package com.yuyongsong.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.yuyongsong.ssm.pojo.Face;
import com.yuyongsong.ssm.utils.FaceSpot;


import com.yuyongsong.ssm.utils.FaceV3DetectBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FaceController {

    @RequestMapping()
    public void img(@RequestParam("fileName" ) MultipartFile file){

        String fileName = FaceSpot.saveFile(file,"D:\\yuyongsong\\zhaopian");

        if(fileName!=null){
            byte[] byface = FaceSpot.FileToByte(file);

            //检测人脸返回的数据
            String str = FaceSpot.detectFace(byface,"1");
            JSON json = JSON.parseObject(str);

            FaceV3DetectBean faceV3DetectBean = JSON.toJavaObject(json,FaceV3DetectBean.class);

            Face face = new Face();
            if(faceV3DetectBean!=null){
                for(int i = 0;i<faceV3DetectBean.getResult().getFace_list().size();i++){
                    //获取年龄
                    int age = faceV3DetectBean.getResult().getFace_list().get(i).getAge();
                    face.setAge(age);

                }
            }
        }

    }
}
