package com.yuyongsong.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.yuyongsong.ssm.pojo.Face;
import com.yuyongsong.ssm.service.FaceService;
import com.yuyongsong.ssm.utils.FaceSpot;


import com.yuyongsong.ssm.utils.FaceV3DetectBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FaceController {
    @Autowired
    private FaceService faceService;


    @RequestMapping("/img.do")
    public ModelAndView img(@RequestParam("fileName" ) MultipartFile file,HttpSession session){


        String username =(String) session.getAttribute("user");

        System.out.println(username);
        String fileName = FaceSpot.saveFile(file,"D:\\apply\\apache-tomcat-9.0.68\\webapps\\img");
        ModelAndView modelAndView = new ModelAndView();
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
                    face.setUsername(username);
                    int age = faceV3DetectBean.getResult().getFace_list().get(i).getAge();
                    face.setAge(age);

                    Double beautyOne = (Double) faceV3DetectBean.getResult().getFace_list().get(i).getBeauty();
                    face.setBeauty(beautyOne);

                    String gender = faceV3DetectBean.getResult().getFace_list().get(i).getGender().getType();
                    if(gender.equals("male")){
                        face.setGender("男");
                    }else if(gender.equals("female")){
                        face.setGender("女");
                    }


                    String glasses = faceV3DetectBean.getResult().getFace_list().get(i).getGlasses().getType();
                    if(glasses.equals("none")){
                        face.setGlasses("没有戴眼镜");
                    }else if(glasses.equals("sun")){
                        face.setGlasses("带了墨镜");
                    }else if(glasses.equals("common")){
                        face.setGlasses("带了普通眼镜");
                    }


                    String expression = faceV3DetectBean.getResult().getFace_list().get(i).getExpression().getType();
                    if(expression.equals("none")){
                        face.setExpression("不笑");
                    }else if(expression.equals("smile")){
                        face.setExpression("微笑");
                    }else if(expression.equals("laugh")){
                        face.setExpression("大笑");
                    }

                    face.setImgPath("http://localhost:8080/img/"+fileName);

                }
                faceService.insertFact(face);
                List<Face> list = faceService.selectFactAll();
                modelAndView.addObject("faces",list);
                modelAndView.addObject("user",username);
                modelAndView.setViewName("index");

            }else {
                System.out.println("百度人脸检测有误");
            }
        }else {
            System.out.println("请上传一张照片");
        }

        return modelAndView;
    }



}
