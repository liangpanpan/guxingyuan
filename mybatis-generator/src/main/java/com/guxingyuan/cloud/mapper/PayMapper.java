package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Pay;
import java.util.List;

public interface PayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pay record);

    Pay selectByPrimaryKey(Integer id);

    List<Pay> selectAll();

    int updateByPrimaryKey(Pay record);
}