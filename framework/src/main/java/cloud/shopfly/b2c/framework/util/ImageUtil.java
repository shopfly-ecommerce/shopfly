package cloud.shopfly.b2c.framework.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

    public static boolean createThumbnail(String sourceFilePath, String targetFilePath, int width, int height) throws IOException {

        File F = new File(sourceFilePath);

        File ThF = new File(targetFilePath);
        BufferedImage buffer = ImageIO.read(F);
        /*
         * 核心算法，计算图片的压缩比
         */
        int w = buffer.getWidth();
        int h = buffer.getHeight();
        double ratiox = 1.0d;
        double ratioy = 1.0d;

        ratiox = w * ratiox / width;
        ratioy = h * ratioy / height;

        if (ratiox >= 1) {
            if (ratioy < 1) {
                ratiox = height * 1.0 / h;
            } else {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            }
        } else {
            if (ratioy < 1) {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            } else {
                ratiox = width * 1.0 / w;
            }
        }
        /*
         * 对于图片的放大或缩小倍数计算完成，ratiox大于1，则表示放大，否则表示缩小
         */
        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(ratiox, ratiox), null);
        buffer = op.filter(buffer, null);
        //从放大的图像中心截图
        buffer = buffer.getSubimage((buffer.getWidth() - width) / 2, (buffer.getHeight() - height) / 2, width, height);

        String format = FileUtil.getFileExt(sourceFilePath);
        ImageIO.write(buffer, format, ThF);

        return (true);
    }

    public static void main(String[] args) {
        ImageUtil UI;
        boolean ss = false;
        try {
            String from = "/Users/kingapex/ideaworkspace/shopflix-en/shopflix/framework/target/images/goods/41187474.jpeg";
            String to = "/Users/kingapex/ideaworkspace/shopflix-en/shopflix/framework/target/images/goods/41187474_500_200.jpeg";
            ss = createThumbnail(from, to, 500, 200);
//            UI = new UploadImg(, "/Users/kingapex/ideaworkspace/shopflix-en/shopflix/framework/target/images/goods/", "ps_low2","jpeg");
//            ss = UI.createThumbnail();
            if (ss) {
                System.out.println("Success");
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}