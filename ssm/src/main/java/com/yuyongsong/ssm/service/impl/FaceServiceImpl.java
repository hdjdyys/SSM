package com.yuyongsong.ssm.service.impl;

import com.yuyongsong.ssm.mapper.FaceMapper;
import com.yuyongsong.ssm.pojo.Face;
import com.yuyongsong.ssm.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FaceServiceImpl implements FaceService {
    @Autowired
    private FaceMapper faceMapper;

    @Override
    public int insertFact(Face face) {
        return faceMapper.insertFace(face);
    }

    @Override
    public List<Face> selectFactAll() {
        return faceMapper.selectFaceAll();
    }
}
