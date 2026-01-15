package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Emp;
import java.util.List;

public interface EmpMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Emp record);

    Emp selectByPrimaryKey(Long id);

    List<Emp> selectAll();

    int updateByPrimaryKey(Emp record);
}