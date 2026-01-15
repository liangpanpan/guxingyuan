package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Score;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoreMapper {
    int deleteByPrimaryKey(@Param("sId") String sId, @Param("cId") String cId);

    int insert(Score record);

    Score selectByPrimaryKey(@Param("sId") String sId, @Param("cId") String cId);

    List<Score> selectAll();

    int updateByPrimaryKey(Score record);
}