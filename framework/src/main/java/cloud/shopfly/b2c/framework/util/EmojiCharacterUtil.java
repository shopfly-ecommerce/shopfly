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
 * Emoji表情处理工具类
 *
 * @author fk
 * @version 2.0
 * @since 7.2.0
 * 2020年5月7日 上午12:45:33
 */
public class EmojiCharacterUtil {

    /**
     * 转义时标识
     */
    private static final char UNICODE_SEPARATOR = '&';
    private static final char UNICODE_PREFIX = 'u';
    private static final char SEPARATOR = ':';

    private static boolean isEmojiCharacter(int codePoint) {
        // 杂项符号与符号字体
        return (codePoint >= 0x2600 && codePoint <= 0x27BF)
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)
                || (codePoint >= 0x2028 && codePoint <= 0x202F)
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)
                /* 标点符号占用区域 */
                // 字母符号
                || (codePoint >= 0x2100 && codePoint <= 0x214F)
                // 各种技术符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)
                // 箭头A
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)
                // 箭头B
                || (codePoint >= 0x2900 && codePoint <= 0x297F)
                // 中文符号
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)
                // 高低位替代符保留区域
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)
                // 私有保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)
                // 变异选择器
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)
                // Plane在第二平面以上的，char都不可以存，全部都转
                || codePoint >= 0x10000;
    }

    /**
     * 将带有emoji字符的字符串转换成可见字符标识
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
                // hash 长度，4位1个字节
                index += (hash.length() - 1) / 4;
            } else {
                sb.append((char) codepoint);
            }
        }
        return sb.toString();
    }

    /**
     * 解析可见字符标识字符串
     */
    public static String decode(String src) {
        // 查找对应编码的标识位
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
                // 自已的格式，与通用unicode格式不能互转
                if (sourceChar[index + 1] >= '4' && sourceChar[index + 1] <= '6'
                        && sourceChar[index + 2] == UNICODE_PREFIX
                        && sourceChar[index + 3] == SEPARATOR) {
                    int length = Integer.parseInt(String.valueOf(sourceChar[index + 1]));
                    // 创建一个4至六位的数组，来存储uncode码的HEX值
                    char[] hexchars = new char[length];
                    for (int j = 0; j < length; j++) {
                        // 4位识别码
                        char ch = sourceChar[index + 4 + j];
                        if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')) {
                            hexchars[j] = ch;

                        } else { // 字符范围不对
                            sb.append(sourceChar[index]);
                            index++;
                            break;
                        }
                    }
                    sb.append(Character.toChars(Integer.parseInt(new String(hexchars), 16)));
                    // 4位前缀+4-6位字符码
                    index += (4 + length);
                    // 通用字符的反转
                } else if (sourceChar[index + 1] == UNICODE_PREFIX) {
                    // 因为第二平面之上的，已经采用了我们自己转码格式，所以这里是固定的长度4
                    char[] hexchars = new char[4];
                    for (int j = 0; j < 4; j++) {
                        // 两位识别码要去掉
                        char ch = sourceChar[index + 2 + j];
                        if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')) {
                            // 4位识别码
                            hexchars[j] = ch;
                        } else { // 字符范围不对
                            sb.append(sourceChar[index]);
                            index++;
                            break;
                        }
                        sb.append(Character.toChars(Integer.parseInt(String.valueOf(hexchars), 16)));
                        // 2位前缀+4位字符码
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
