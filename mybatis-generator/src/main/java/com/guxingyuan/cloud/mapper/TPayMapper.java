package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.TPay;
import java.util.List;

public interface TPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TPay record);

    TPay selectByPrimaryKey(Integer id);

    List<TPay> selectAll();

    int updateByPrimaryKey(TPay record);
}