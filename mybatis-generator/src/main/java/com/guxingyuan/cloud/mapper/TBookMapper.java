package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.TBook;
import java.util.List;

public interface TBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TBook record);

    TBook selectByPrimaryKey(Integer id);

    List<TBook> selectAll();

    int updateByPrimaryKey(TBook record);
}