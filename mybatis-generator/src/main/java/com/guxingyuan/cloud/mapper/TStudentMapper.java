package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.TStudent;
import java.util.List;

public interface TStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TStudent record);

    TStudent selectByPrimaryKey(Integer id);

    List<TStudent> selectAll();

    int updateByPrimaryKey(TStudent record);
}