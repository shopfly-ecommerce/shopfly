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
         * Core algorithm, calculate the compression ratio of the picture
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
         * After the calculation of the magnification or reduction of the picture is completed,ratioxIs greater than1, indicates magnification, otherwise indicates narrowing
         */
        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(ratiox, ratiox), null);
        buffer = op.filter(buffer, null);
        // Screenshot from enlarged image center
        buffer = buffer.getSubimage((buffer.getWidth() - width) / 2, (buffer.getHeight() - height) / 2, width, height);

        String format = FileUtil.getFileExt(sourceFilePath);
        ImageIO.write(buffer, format, ThF);

        return (true);
    }

    public static void main(String[] args) {
        ImageUtil UI;
        boolean ss = false;
        try {
            String from = "/Users/kingapex/ideaworkspace/shopfly-en/shopfly/framework/target/images/goods/41187474.jpeg";
            String to = "/Users/kingapex/ideaworkspace/shopfly-en/shopfly/framework/target/images/goods/41187474_500_200.jpeg";
            ss = createThumbnail(from, to, 500, 200);
//            UI = new UploadImg(, "/Users/kingapex/ideaworkspace/shopfly-en/shopfly/framework/target/images/goods/", "ps_low2","jpeg");
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
