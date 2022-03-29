/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Base64相关
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class Base64 {
	private static final char[] LEGAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();

	public static String encode(byte[] data) {
		int start = 0;
		int len = data.length;
		StringBuffer buf = new StringBuffer(data.length * 3 / 2);

		int end = len - 3;
		int i = start;
		int n = 0;

		while (i <= end) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 0x0ff) << 8)
					| (((int) data[i + 2]) & 0x0ff);

			buf.append(LEGAL_CHARS[(d >> 18) & 63]);
			buf.append(LEGAL_CHARS[(d >> 12) & 63]);
			buf.append(LEGAL_CHARS[(d >> 6) & 63]);
			buf.append(LEGAL_CHARS[d & 63]);

			i += 3;

			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		}

		if (i == start + len - 2) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 255) << 8);

			buf.append(LEGAL_CHARS[(d >> 18) & 63]);
			buf.append(LEGAL_CHARS[(d >> 12) & 63]);
			buf.append(LEGAL_CHARS[(d >> 6) & 63]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (((int) data[i]) & 0x0ff) << 16;

			buf.append(LEGAL_CHARS[(d >> 18) & 63]);
			buf.append(LEGAL_CHARS[(d >> 12) & 63]);
			buf.append("==");
		}

		return buf.toString();
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z') {
			return ((int) c) - 65;
		} else if (c >= 'a' && c <= 'z') {
			return ((int) c) - 97 + 26;
		} else if (c >= '0' && c <= '9') {
			return ((int) c) - 48 + 26 + 26;
		} else {
			switch (c) {
				case '+':
					return 62;
				case '/':
					return 63;
				case '=':
					return 0;
				default:
					throw new RuntimeException("unexpected code: " + c);
			}
		}
	}

	/**
	 * Decodes the given Base64 encoded String to a new byte array. The byte
	 * array holding the decoded data is returned.
	 */

	public static byte[] decode(String s) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException ex) {
			System.err.println("Error while decoding BASE64: " + ex.toString());
		}
		return decodedBytes;
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();

		while (true) {
			while (i < len && s.charAt(i) <= ' ') {
				i++;
			}

			if (i == len) {
				break;
			}

			int tri = (decode(s.charAt(i)) << 18)
					+ (decode(s.charAt(i + 1)) << 12)
					+ (decode(s.charAt(i + 2)) << 6)
					+ (decode(s.charAt(i + 3)));

			os.write((tri >> 16) & 255);
			if (s.charAt(i + 2) == '=') {
				break;
			}
			os.write((tri >> 8) & 255);
			if (s.charAt(i + 3) == '=') {
				break;
			}
			os.write(tri & 255);

			i += 4;
		}
	}
	
    /**
     * Decodes a BASE-64 encoded stream to recover the original
     * data. White space before and after will be trimmed away,
     * but no other manipulation of the input will be performed.
     *
     * As of version 1.2 this method will properly handle input
     * containing junk characters (newlines and the like) rather
     * than throwing an error. It does this by pre-parsing the
     * input and generating from that a count of VALID input
     * characters.
     **/
    public static byte[] decode(char[] data) {
        // as our input could contain non-BASE64 data (newlines,
        // whitespace of any sort, whatever) we must first adjust
        // our count of USABLE data so that...
        // (a) we don't misallocate the output array, and
        // (b) think that we miscalculated our data length
        //     just because of extraneous throw-away junk

        int tempLen = data.length;
        for (int ix = 0; ix < data.length; ix++) {
            if ((data[ix] > 255) || codes[data[ix]] < 0) {
				--tempLen; // ignore non-valid chars and padding
			}
        }
        // calculate required length:
        //  -- 3 bytes for every 4 valid base64 chars
        //  -- plus 2 bytes if there are 3 extra base64 chars,
        //     or plus 1 byte if there are 2 extra.

        int len = (tempLen / 4) * 3;
        if ((tempLen % 4) == 3) {
			len += 2;
		}
        if ((tempLen % 4) == 2) {
			len += 1;
		}

        byte[] out = new byte[len];

		// # of excess bits stored in accum
        int shift = 0;
		// excess bits
        int accum = 0;
        int index = 0;

        // we now go through the entire array (NOT using the 'tempLen' value)
        for (int ix = 0; ix < data.length; ix++) {
            int value = (data[ix] > 255) ? -1 : codes[data[ix]];
			// skip over non-code
            if (value >= 0)
            {
				// bits shift up by 6 each time thru
                accum <<= 6;
				// loop, with new bits being put in
                shift += 6;
				// at the bottom.
                accum |= value;
				// whenever there are 8 or more shifted in,
                if (shift >= 8)
                {
					// write them out (from the top, leaving any
                    shift -= 8;
					// excess at the bottom for next iteration.
                    out[index++] =
                        (byte) ((accum >> shift) & 0xff);
                }
            }
            // we will also have skipped processing a padding null byte ('=') here;
            // these are used ONLY for padding to an even length and do not legally
            // occur as encoded data. for this reason we can ignore the fact that
            // no index++ operation occurs in that special case: the out[] array is
            // initialized to all-zero bytes to start with and that works to our
            // advantage in this combination.
        }

        // if there is STILL something wrong we just have to throw up now!
        if (index != out.length) {
            throw new Error("Miscalculated data length (wrote " +
                index + " instead of " + out.length + ")");
        }

        return out;
    }
    
    private static byte[] codes = new byte[256];
}
