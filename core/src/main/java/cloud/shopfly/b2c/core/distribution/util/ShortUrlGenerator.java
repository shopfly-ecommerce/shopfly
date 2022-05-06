/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.util;


/**
 * 短链接生成器
 *
 * @author Sylow
 * @version v1.0, 2015-11-18
 * @since v1.0
 */
public class ShortUrlGenerator {

    /**
     * 生成短链接
     *
     * @param url 长链接
     * @return 四个短地址数组，取任意一个即可
     */
    public static String[] getShortUrl(String url) {

        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "shopfly";

        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = ShopflyEncrypt.md5(key + url);

        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];

        //生成4个短地址，8位一组
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

            String outChars = "";

            //生成6次-6位短地址
            for (int j = 0; j < 6; j++) {

                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;

                // 把取得的字符相加
                outChars += chars[(int) index];

                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;

            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;

        }

        return resUrl;

    }

    /**
     * 根据短链接获得key值
     *
     * @param shortUrl 短链接
     * @return key值 String
     */
    public static String getShortKey(String shortUrl) {
        String key = shortUrl.substring(shortUrl.lastIndexOf("/") + 1, shortUrl.length());
        return key;
    }

    /**
     * 测试函数
     *
     * @param args
     */
    public static void main(String[] args) {

        // 长连接： http://www.javamall.com.cn
        // 解析后的短链接为： http://**/Afeyae

        String sLongUrl = "http://www.javamall.com.cn";
        String[] aResult = getShortUrl(sLongUrl);

        // 打印出结果
        for (int i = 0; i < aResult.length; i++) {
            System.out.println(aResult[i]);
        }

        //String key = getShortKey("http://www:8080/su/asfasf");
        //System.out.println(key);

    }
}
