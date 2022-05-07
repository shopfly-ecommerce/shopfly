/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;

/**
 * EmojiFacial expression processing tool class
 *
 * @author fk
 * @version 2.0
 * @since 7.2.0
 * 2020years5month7The morning of12:45:33
 */
public class EmojiCharacterUtil {

    /**
     * Escape time identification
     */
    private static final char UNICODE_SEPARATOR = '&';
    private static final char UNICODE_PREFIX = 'u';
    private static final char SEPARATOR = ':';

    private static boolean isEmojiCharacter(int codePoint) {
        // Miscellaneous symbols and symbol fonts
        return (codePoint >= 0x2600 && codePoint <= 0x27BF)
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)
                || (codePoint >= 0x2028 && codePoint <= 0x202F)
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)
                /* Punctuation takes up space*/
                // Alphabetic character
                || (codePoint >= 0x2100 && codePoint <= 0x214F)
                // Technical symbols
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)
                // Arrow A
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)
                // Arrow B
                || (codePoint >= 0x2900 && codePoint <= 0x297F)
                // Chinese symbols
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)
                // The high-low substitution preserves the area
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)
                // Private reserved area
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)
                // Variation selector
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)
                // Plane above the second Plane, char can not be stored, all transfer
                || codePoint >= 0x10000;
    }

    /**
     * withemojiThe string of characters is converted to a visible character identifier
     */
    public static String encode(String src) {
        if (StringUtil.isEmpty(src)) {
            return src;
        }
        int cpCount = src.codePointCount(0, src.length());
        int firCodeIndex = src.offsetByCodePoints(0, 0);
        int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);
        StringBuilder sb = new StringBuilder(src.length());
        for (int index = firCodeIndex; index <= lstCodeIndex; index++) {
            int codepoint = src.codePointAt(index);
            if (isEmojiCharacter(codepoint)) {
                String hash = Integer.toHexString(codepoint);
                sb.append(UNICODE_SEPARATOR).append(hash.length())
                        .append(UNICODE_PREFIX).append(SEPARATOR).append(hash);
                // Hash length: 4 bits and 1 byte
                index += (hash.length() - 1) / 4;
            } else {
                sb.append((char) codepoint);
            }
        }
        return sb.toString();
    }

    /**
     * Parse the visible character identification string
     */
    public static String decode(String src) {
        // Find the corresponding encoding identifier bit
        if (StringUtil.isEmpty(src)) {
            return src;
        }
        StringBuilder sb = new StringBuilder(src.length());
        char[] sourceChar = src.toCharArray();
        int index = 0;
        while (index < sourceChar.length) {
            if (sourceChar[index] == UNICODE_SEPARATOR) {
                if (index + 6 >= sourceChar.length) {
                    sb.append(sourceChar[index]);
                    index++;
                    continue;
                }
                // Your own format is not interchangeable with the universal Unicode format
                if (sourceChar[index + 1] >= '4' && sourceChar[index + 1] <= '6'
                        && sourceChar[index + 2] == UNICODE_PREFIX
                        && sourceChar[index + 3] == SEPARATOR) {
                    int length = Integer.parseInt(String.valueOf(sourceChar[index + 1]));
                    // Create an array of 4 to 6 bits to store the HEX value of the uncode code
                    char[] hexchars = new char[length];
                    for (int j = 0; j < length; j++) {
                        // 4 digit identification code
                        char ch = sourceChar[index + 4 + j];
                        if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')) {
                            hexchars[j] = ch;

                        } else { // Incorrect character range
                            sb.append(sourceChar[index]);
                            index++;
                            break;
                        }
                    }
                    sb.append(Character.toChars(Integer.parseInt(new String(hexchars), 16)));
                    // 4 bit prefix +4-6 bit character code
                    index += (4 + length);
                    // Reversal of a common character
                } else if (sourceChar[index + 1] == UNICODE_PREFIX) {
                    // And since the one above the second plane is already in our own transcoding format, we have a fixed length of 4 here
                    char[] hexchars = new char[4];
                    for (int j = 0; j < 4; j++) {
                        // The two-digit identifier is to be removed
                        char ch = sourceChar[index + 2 + j];
                        if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')) {
                            // 4 digit identification code
                            hexchars[j] = ch;
                        } else { // Incorrect character range
                            sb.append(sourceChar[index]);
                            index++;
                            break;
                        }
                        sb.append(Character.toChars(Integer.parseInt(String.valueOf(hexchars), 16)));
                        // 2 bit prefix +4 bit character code
                        index += (2 + 4);
                    }
                } else {
                    sb.append(sourceChar[index]);
                    index++;
                    continue;
                }
            } else {
                sb.append(sourceChar[index]);
                index++;
                continue;
            }
        }

        return sb.toString();
    }

    public static String filter(String src) {
        if (src == null) {
            return null;
        }
        int cpCount = src.codePointCount(0, src.length());
        int firCodeIndex = src.offsetByCodePoints(0, 0);
        int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);
        StringBuilder sb = new StringBuilder(src.length());
        for (int index = firCodeIndex; index <= lstCodeIndex; ) {
            int codepoint = src.codePointAt(index);
            if (!isEmojiCharacter(codepoint)) {
                System.err.println("codepoint:" + Integer.toHexString(codepoint));
                sb.append((char) codepoint);
            }
            index += ((Character.isSupplementaryCodePoint(codepoint)) ? 2 : 1);

        }
        return sb.toString();
    }

}
