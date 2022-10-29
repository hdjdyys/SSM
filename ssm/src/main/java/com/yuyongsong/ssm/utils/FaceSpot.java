package com.yuyongsong.ssm.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

public class FaceSpot {
    private static final String AppID ="28046162";
    private static final String APIKey = "R5oG7Q3MPvHN02l6BxhZGyyo";
    private static final String SecretKey = "W9QNGfn9G78MOWznyAkii2GTGDnQo8gQ";

    static AipFace client = null;
    static {
        client = new AipFace(AppID, APIKey, SecretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    public static String saveFile(MultipartFile file, String savePath) {
        if (file!=null) {
            //获取原始图片的拓展名
            String originalFilename = file.getOriginalFilename();
            //新的文件名字
            String newFileName = UUID.randomUUID()+"_"+originalFilename;
            //封装上传文件位置的全路径
            File targetFile = new File(savePath,newFileName);
            try {
                //把本地文件上传到封装上传文件位置的全路径
                file.transferTo(targetFile);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return newFileName;
        }

        return null;
    }

    /**
     * 将上传的文件转换成字节流
     * @param file
     * @return
     */
    public static byte[] FileToByte(MultipartFile file) {

        try{
            InputStream content = file.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[100];
            int rc = 0;
            while ((rc = content.read(bytes,0,100))>0){
                byteArrayOutputStream.write(bytes,0,rc);
            }

            return  byteArrayOutputStream.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测人脸
     * @param arg0
     * @param i
     * @return
     */
    public static String detectFace(byte[] arg0, String i) {

        try {


            HashMap<String, Object> options = new HashMap<String, Object>();
            //定义参数变量
            options.put("face_field", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
            //定义最大人脸检测数量
            options.put("max_face_num", i);
            //LIVE表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
            options.put("face_type", "LIVE");
            // 图片数据
            String imgStr = Base64Util.encode(arg0);
            JSONObject jsonObject = client.detect(imgStr, "BASE64", options);
            System.out.println(jsonObject.toString(2));
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
