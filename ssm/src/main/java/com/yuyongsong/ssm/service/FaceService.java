package com.yuyongsong.ssm.service;

import com.yuyongsong.ssm.pojo.Face;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FaceService {
    public int insertFact(Face face);

    List<Face> selectFactAll();
}
