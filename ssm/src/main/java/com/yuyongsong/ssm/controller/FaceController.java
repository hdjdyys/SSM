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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FaceController {
    @Autowired
    private FaceService faceService;

    @RequestMapping("img")
    public String img(@RequestParam("fileName" ) MultipartFile file ){
        ModelAndView mav = new ModelAndView();

        String username =null;

        System.out.println(username);
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
                    face.setUsername(username);
                    int age = faceV3DetectBean.getResult().getFace_list().get(i).getAge();
                    face.setAge(age);

                    Double beautyOne = (Double) faceV3DetectBean.getResult().getFace_list().get(i).getBeauty();
                    face.setBeauty(beautyOne);

                    String gender = faceV3DetectBean.getResult().getFace_list().get(i).getGender().getType();
                    face.setGender(gender);

                    String glasses = faceV3DetectBean.getResult().getFace_list().get(i).getGlasses().getType();
                    face.setGlasses(glasses);

                    String expression = faceV3DetectBean.getResult().getFace_list().get(i).getExpression().getType();
                    face.setExpression(expression);
                    face.setImgPath("D:\\yuyongsong\\zhaopian"+fileName);

                }
                faceService.insertFact(face);
            }else {
                System.out.println("百度人脸检测有误");
            }
        }else {
            System.out.println("请上传一张照片");
        }

        return "redirect:/selectface";
    }

    @RequestMapping("selectface")
    public ModelAndView selectface() throws Exception {
        List<Face> list = faceService.selectFactAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("faces",list);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
