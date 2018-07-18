package com.chub.eurekacommon.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chengtianping
 * @description 文件 工具类
 * @date 2018/1/2
 */
public class ImageUtils {

    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /** 文件裁剪
     * @param srcFile
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static InputStream cutPic(String srcFile, int x, int y, int width, int height) {
        InputStream inputStream = null;
        ImageInputStream imageInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            // 读取图片文件
            inputStream = new ByteArrayInputStream(getImageFromNetByUrl(srcFile));
            // 获取文件格式
            String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);
            // ImageReader声称能够解码指定格式
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();

            // 获取图片流
            imageInputStream = ImageIO.createImageInputStream(inputStream);
            // 输入源中的图像将只按顺序读取
            reader.setInput(imageInputStream, true);
            // 描述如何对流进行解码
            ImageReadParam param = reader.getDefaultReadParam();

            // 图片裁剪区域
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标
            param.setSourceRegion(rect);
            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bi, ext, outputStream);
            outputStream.flush();
            InputStream picInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return picInputStream;
        } catch (Exception e) {
            logger.error("cutPic exception ===> ",e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("inputStream close IOException ===> ",e);
                }
            }
            if (imageInputStream != null) {
                try {
                    imageInputStream.close();
                } catch (IOException e) {
                    logger.error("imageInputStream close IOException ===> ",e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("outputStream close IOException ===> ",e);
                }
            }
        }
    }

    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);

            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
            logger.error("getImageFromNetByUrl Exception ====> ",e);
            return null;
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
    }

    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
