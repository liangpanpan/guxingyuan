package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Evaluate;
import java.util.List;

public interface EvaluateMapper {
    int insert(Evaluate record);

    List<Evaluate> selectAll();
}