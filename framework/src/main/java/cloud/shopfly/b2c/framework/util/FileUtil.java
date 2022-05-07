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

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * File utility class
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/28 In the afternoon5:10
 */
public class FileUtil {

    protected final static Log logger = LogFactory.getLog(FileUtil.class);

    private FileUtil() {
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        // boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.TRANSLUCENT;
            /*
             * if (hasAlpha) { transparency = Transparency.BITMASK; }
             */

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            // int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*
             * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
             */
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    public static void createFile(InputStream in, String filePath) {
        if (in == null) {
            throw new RuntimeException("create file error: inputstream is null");
        }
        int potPos = filePath.lastIndexOf('/') + 1;
        String folderPath = filePath.substring(0, potPos);
        createFolder(folderPath);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            byte[] by = new byte[1024];
            int c;
            while ((c = in.read(by)) != -1) {
                outputStream.write(by, 0, c);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Whether files can be uploaded
     *
     * @param logoFileName The file name
     * @return
     */
    public static boolean isAllowUp(String logoFileName) {
        logoFileName = logoFileName.toLowerCase();
        String allowType = "gif,jpg,jpeg,bmp,swf,png,rar,doc,docx,xls,xlsx,pdf,zip,ico,txt,mp4";
        if (!"".equals(logoFileName.trim()) && logoFileName.length() > 0) {
            String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
            //			return allowTYpe.toString().indexOf(ex) >= 0;
            //lzf edit 20110717
            // Solve the case only problem
            // Add the JPEG/PNG extension as well
            return allowType.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
        } else {
            return false;
        }
    }

    /**
     * Whether the image is allowed to upload
     *
     * @param ex The file suffix
     * @return
     */
    public static boolean isAllowUpImg(String ex) {
        String allowType = "gif,jpg,png,jpeg,mp4,quicktime";
        if (!"".equals(ex.trim()) && ex.length() > 0) {
            return allowType.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
        } else {
            return false;
        }
    }


    /**
     * Write content to a file
     *
     * @param filePath
     * @param fileContent
     */
    public static void write(String filePath, String fileContent) {

        try {
            FileOutputStream fo = new FileOutputStream(filePath);
            OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");

            out.write(fileContent);

            out.close();
        } catch (IOException ex) {

            System.err.println("Create File Error!");
            ex.printStackTrace();
        }
    }

    /**
     * willinputStreamWritten to the file
     *
     * @param file
     * @param path
     */
    public static void write(MultipartFile file, String path) {
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * willinputstreamWritten to the file
     *
     * @param stream File stream
     * @param path   Path to the file to write to
     */
    public static void write(InputStream stream, String path) {
        try {
            FileUtils.copyInputStreamToFile(stream, new File(path));
        } catch (IOException e) {
            logger.error("File write exception：", e);
        }

    }

    /**
     * The default value for reading file contents isUTF-8coding
     *
     * @param filePath
     * @return
     */
    public static String read(String filePath, String code) {
        if (code == null || "".equals(code)) {
            code = "UTF-8";
        }
        StringBuffer stringBuffer = new StringBuffer("");
        File file = new File(filePath);
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), code);
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            read.close();
            read = null;
            reader.close();
            read = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            stringBuffer = new StringBuffer("");
        }
        return stringBuffer.toString();
    }

    /**
     * Delete files or folders
     *
     * @param filePath
     */
    public static void delete(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.isDirectory()) {
                    FileUtils.deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        } catch (Exception ex) {
            logger.error("File Deletion exception：", ex);
        }
    }

    public static boolean exist(String filepath) {
        File file = new File(filepath);

        return file.exists();
    }

    /**
     * Creating a folder
     *
     * @param filePath
     */
    public static void createFolder(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception ex) {
            logger.error("Description Failed to create a file directory：", ex);
        }
    }

    /**
     * Rename file、folder
     *
     * @param from
     * @param to
     */
    public static void renameFile(String from, String to) {
        try {
            File file = new File(from);
            if (file.exists()) {
                file.renameTo(new File(to));
            }
        } catch (Exception ex) {
            logger.error("Rename File/Folder Error:", ex);
        }
    }

    /**
     * Gets the extension of the file
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {

        int potPos = fileName.lastIndexOf('.') + 1;
        String type = fileName.substring(potPos, fileName.length());
        return type;
    }

    /**
     * throughFileObject creation file
     *
     * @param file
     * @param filePath
     */
    public static void createFile(File file, String filePath) {
        int potPos = filePath.lastIndexOf('/') + 1;
        String folderPath = filePath.substring(0, potPos);
        createFolder(folderPath);
        FileOutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            fileInputStream = new FileInputStream(file);
            byte[] by = new byte[1024];
            int c;
            while ((c = fileInputStream.read(by)) != -1) {
                outputStream.write(by, 0, c);
            }
            outputStream.close();

            fileInputStream.close();
        } catch (IOException e) {
            logger.error("Description Creating a file is abnormal", e);
        }
    }


    public static String readFile(String resource) {
        InputStream stream = getResourceAsStream(resource);
        String content = readStreamToString(stream);

        return content;

    }

    public static InputStream getResourceAsStream(String resource) {
        String stripped = resource.startsWith("/") ? resource.substring(1)
                : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(stripped);

        }

        return stream;
    }

    public static String readStreamToString(InputStream stream) {
        StringBuffer fileContentsb = new StringBuffer();
        String fileContent = "";

        try {
            InputStreamReader read = new InputStreamReader(stream, "utf-8");
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentsb.append(line + "\n");
            }
            read.close();
            read = null;
            reader.close();
            read = null;
            fileContent = fileContentsb.toString();
        } catch (Exception ex) {
            fileContent = "";
        }
        return fileContent;
    }

    /**
     * delete file folder
     *
     * @param path
     */
    public static void removeFile(File path) {

        if (path.isDirectory()) {
            try {
                FileUtils.deleteDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void copyFile(String srcFile, String destFile) {
        try {
            if (FileUtil.exist(srcFile)) {
                FileUtils.copyFile(new File(srcFile), new File(destFile));
            }
        } catch (IOException e) {
            logger.error("File replication exception", e);
        }
    }

    public static void copyFolder(String sourceFolder, String destinationFolder) {
        ////System.out.println("copy " + sourceFolder + " to " + destinationFolder);
        try {
            File source = new File(sourceFolder);
            if (source.exists()) {
                FileUtils.copyDirectory(new File(sourceFolder), new File(
                        destinationFolder));
            }
        } catch (Exception e) {
            throw new RuntimeException("copy file error");
        }


    }


    /**
     * Copy the source folder to the destination folder
     * Copy only new files
     *
     * @param sourceFolder
     * @param targetFolder
     */
    public static void copyNewFile(String sourceFolder, String targetFolder) {
        try {
            File source = new File(sourceFolder);

            if (!targetFolder.endsWith("/")) {
                targetFolder = targetFolder + "/";
            }

            if (source.exists()) {
                File[] filelist = source.listFiles();
                for (File f : filelist) {
                    File targetFile = new File(targetFolder + f.getName());

                    if (f.isFile()) {
                        // If the destination file is new or the source file is new, copy it, otherwise skip it
                        if (!targetFile.exists() || FileUtils.isFileNewer(f, targetFile)) {
                            FileUtils.copyFileToDirectory(f, new File(targetFolder));
                            ////System.out.println("copy "+ f.getName());
                        } else {
                            //	//System.out.println("skip  "+ f.getName());
                        }
                    }
                }
            }


        } catch (Exception e) {
            throw new RuntimeException("copy file error");
        }
    }


    public static void unZip(String zipPath, String targetFolder, boolean cleanZip) {
        File folderFile = new File(targetFolder);
        File zipFile = new File(zipPath);
        Project prj = new Project();
        Expand expand = new Expand();
        expand.setEncoding("UTF-8");
        expand.setProject(prj);
        expand.setSrc(zipFile);
        expand.setOverwrite(true);
        expand.setDest(folderFile);
        expand.execute();

        if (cleanZip) {
            // Remove the zip package
            Delete delete = new Delete();
            delete.setProject(prj);
            delete.setDir(zipFile);
            delete.execute();
        }
    }


    /**
     * Get the file type
     *
     * @param fileType The file suffix
     * @return
     */
    public static String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }


}
