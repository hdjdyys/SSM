package com.yuyongsong.ssm.mapper;

import com.yuyongsong.ssm.pojo.Face;

import java.util.List;

public interface FaceMapper {
    public int insertFace(Face face);

    public List<Face> selectFaceAll();
}
