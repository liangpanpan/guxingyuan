package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Student;
import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(String sId);

    int insert(Student record);

    Student selectByPrimaryKey(String sId);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);
}