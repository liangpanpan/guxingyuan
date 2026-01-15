package com.guxingyuan.cloud.mapper;

import com.guxingyuan.cloud.entity.Article;
import com.guxingyuan.cloud.entity.ArticleWithBLOBs;
import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(String articleId);

    int insert(Article record);

    ArticleWithBLOBs selectByPrimaryKey(String articleId);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);
}