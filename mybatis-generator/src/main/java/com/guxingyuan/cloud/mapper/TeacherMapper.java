package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Teacher;
import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(String tId);

    int insert(Teacher record);

    Teacher selectByPrimaryKey(String tId);

    List<Teacher> selectAll();

    int updateByPrimaryKey(Teacher record);
}