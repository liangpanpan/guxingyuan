package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Cron;
import java.util.List;

public interface CronMapper {
    int deleteByPrimaryKey(Integer cronId);

    int insert(Cron record);

    Cron selectByPrimaryKey(Integer cronId);

    List<Cron> selectAll();

    int updateByPrimaryKey(Cron record);
}