package dev.shopflix.framework.util;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
public class UploadImg {
    String fromFileStr;
    String saveToFileStr;
    String sysimgfile;
    int width;
    int height;
    String suffix;
    /**
     * @param fromFileStr
     *            原始图片完整路径
     * @param saveToFileStr
     *            缩略图片保存路径
     *
     */
    public UploadImg(String fromFileStr, String saveToFileStr, String sysimgfile,String suffix,int width,int height) {
        this.fromFileStr = fromFileStr;
        this.saveToFileStr = saveToFileStr;
        this.sysimgfile = sysimgfile;
        this.width=width;
        this.height=height;
        this.suffix=suffix;
    }
    public boolean createThumbnail() throws Exception {
        // fileExtNmae是图片的格式 gif JPG 或png
        // String fileExtNmae="";
        File F = new File(fromFileStr);
        if (!F.isFile())
            throw new Exception(F
                    + " is not image file error in CreateThumbnail!");
        File ThF = new File(saveToFileStr, sysimgfile +"."+suffix);
        BufferedImage buffer = ImageIO.read(F);
        /*
         * 核心算法，计算图片的压缩比
         */
        int w= buffer.getWidth();
        int h=buffer.getHeight();
        double ratiox = 1.0d;
        double ratioy = 1.0d;

        ratiox= w * ratiox / width;
        ratioy= h * ratioy / height;

        if( ratiox >= 1){
            if(ratioy < 1){
                ratiox = height * 1.0 / h;
            }else{
                if(ratiox > ratioy){
                    ratiox = height * 1.0 / h;
                }else{
                    ratiox = width * 1.0 / w;
                }
            }
        }else{
            if(ratioy < 1){
                if(ratiox > ratioy){
                    ratiox = height * 1.0 / h;
                }else{
                    ratiox = width * 1.0 / w;
                }
            }else{
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
        buffer = buffer.getSubimage((buffer.getWidth()-width)/2, (buffer.getHeight() - height) / 2, width, height);
        try {
            ImageIO.write(buffer, suffix, ThF);
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: "
                    + ex.getMessage());
        }
        return (true);
    }
    public static void main(String[] args) {
        UploadImg UI;
        boolean ss = false;
        try {
            UI = new UploadImg("/Users/kingapex/ideaworkspace/shopflix-en/shopflix/framework/target/images/goods/41187474.jpeg", "/Users/kingapex/ideaworkspace/shopflix-en/shopflix/framework/target/images/goods/", "ps_low2","jpeg",300,500);
            ss = UI.createThumbnail();
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