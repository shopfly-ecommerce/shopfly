/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.CaptchaManager;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 图片服务器实现
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018年3月19日 上午9:56:03
 */
@Service
public class CaptchaManagerImpl implements CaptchaManager {

    @Autowired
    private Cache cache;
    @Autowired
    private ShopflyConfig shopflyConfig;
    @Autowired
    private SettingManager settingManager;


    @Override
    public boolean valid(String uuid, String code, String scene) {
        //从传入参数组织key
        String valCode = CachePrefix.CAPTCHA.getPrefix() + uuid + "_" + scene;
        //redis中获取验证码
        Object obj = cache.get(valCode);

        if (obj != null && obj.toString().toLowerCase().equals(code.toLowerCase())) {
            cache.remove(valCode);
            return true;
        }
        return false;
    }


    private static char[] captchars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8'};

    private Random generator = new Random();

    @Override
    public void writeCode(String uuid, String scene) {
        int width = 160, height = 80;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设定字体
        g.setFont(this.getFont());

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        int car = captchars.length - 1;
        //获取站点设置
        String siteSettingJson = settingManager.get(SettingGroup.SITE);
        //解析站点设置
        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
        for (int i = 0; i < 4; i++) {
            String rand = "" + captchars[generator.nextInt(car) + 1];
            if (siteSetting.getTestMode().equals(1)) {
                rand = "1";
            }
            sRand += rand;
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(30 + random.nextInt(80), 30 + random.nextInt(80), 30 + random.nextInt(80)));
            g.drawString(rand, 30 * i + 20, 58);
        }
        cache.put(CachePrefix.CAPTCHA.getPrefix() + uuid + "_" + scene, sRand.toLowerCase(), shopflyConfig.getCaptchaTimout());
        // 图象生效
        g.dispose();
        // 输出图象到页面
        HttpServletResponse resp = ThreadContextHolder.getHttpResponse();
        //设置头信息为图片类型
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        try {
            ImageIO.write(image, "JPEG", resp.getOutputStream());
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     *
     * @return
     */
    private Font getFont() {
        int size = 55;
        Random random = new Random();
        Font[] font = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Forte", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return font[random.nextInt(5)];
    }

    @Override
    public void deleteCode(String uuid, String code, String scene) {
        cache.remove(CachePrefix.CAPTCHA.getPrefix() + uuid + "_" + scene);
    }
}
