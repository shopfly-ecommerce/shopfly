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
 * Picture server implementation
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018years3month19The morning of9:56:03
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
        // Organize keys from incoming parameters
        String valCode = CachePrefix.CAPTCHA.getPrefix() + uuid + "_" + scene;
        // Obtain the verification code in Redis
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

        // Getting the graphics Context
        Graphics g = image.getGraphics();

        // Generate random class
        Random random = new Random();

        // Set the background color
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // Set the font
        g.setFont(this.getFont());

        // 155 interference lines are randomly generated to make the authentication code in the image difficult to be detected by other programs
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
        // Get site Settings
        String siteSettingJson = settingManager.get(SettingGroup.SITE);
        // Resolving site Settings
        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
        for (int i = 0; i < 4; i++) {
            String rand = "" + captchars[generator.nextInt(car) + 1];
            if (siteSetting.getTestMode().equals(1)) {
                rand = "1";
            }
            sRand += rand;
            // Calling the function yields the same color, probably because the seeds are so close that they can only be generated directly
            g.setColor(new Color(30 + random.nextInt(80), 30 + random.nextInt(80), 30 + random.nextInt(80)));
            g.drawString(rand, 30 * i + 20, 58);
        }
        cache.put(CachePrefix.CAPTCHA.getPrefix() + uuid + "_" + scene, sRand.toLowerCase(), shopflyConfig.getCaptchaTimout());
        // The image effect
        g.dispose();
        // Output images to pages
        HttpServletResponse resp = ThreadContextHolder.getHttpResponse();
        // Set header information to image type
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

    protected Color getRandColor(int fc, int bc) {// Get a random color for a given range
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
     * Generate random fonts
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
