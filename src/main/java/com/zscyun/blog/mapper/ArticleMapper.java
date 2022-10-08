package com.zscyun.blog.mapper;

import com.zscyun.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
  //获取文章总数
 int selectAcountArticle();
  //插入文章
  int insertArticle(Article article);

  //获取所有文章
  List<Article> selectListArticle();

  //获取文章通过分类
  List<Article> selectArticlesByCategory(String category);

  //获取文章通过文章id
  Article selectArticleById(int id);

  //修改文章
  int updateArticle(Article article);

  int deleteArticle(int articleId);


}
