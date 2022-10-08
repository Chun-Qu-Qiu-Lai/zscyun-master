package com.zscyun.blog.controller;


import com.zscyun.blog.entity.Article;
import com.zscyun.blog.entity.RArticle;
import com.zscyun.blog.entity.Result;
import com.zscyun.blog.entity.ResultStatus;
import com.zscyun.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 蛋炒饭不加蛋
 * <p>
 * 文章管理
 */
@CrossOrigin
@RestController
@RequestMapping("/blog")
public class ArticleController {
  @Autowired
  private ArticleMapper articleMapper;

  @PostMapping("/create_article")
  private Result createArticle(@RequestParam("articleTitle") String articleTitle,
                               @RequestParam("articleCategory") String articleCategory,
                               @RequestParam("articleContent") String articleContent) {
    Article article = new Article();
    article.setTitle(articleTitle);
    article.setCategory(articleCategory);
    article.setContent(articleContent.getBytes());
    articleMapper.insertArticle(article);
    return Result.success(ResultStatus.SUCCESS);
  }

  @GetMapping("/get_articles")
  private Result getArticles(@RequestParam(value = "category", required = false) String category) {
    List<Article> articles = new ArrayList<>();
    if (category == null) {
      articles = articleMapper.selectListArticle();
      List<Object> r = new ArrayList<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      for (Article article : articles) {
        Map<String, Object> ar = new LinkedHashMap<>();
        String convert = new String(article.getContent(), StandardCharsets.UTF_8);
        ar.put("id", article.getId());
        ar.put("author", article.getAuthor());
        ar.put("title", article.getTitle());
        ar.put("content", convert);
        //代码分行
        List<String> contentList = new ArrayList<>();
        String[] strs = convert.split("\n");
        for (String str : strs) {
          contentList.add(str.toString());
        }
        ar.put("contentList", contentList);
        ar.put("category", article.getCategory());
        ar.put("create_time", sdf.format(article.getCreate_time()));
        ar.put("update_time", article.getUpdate_time());
        r.add(ar);
      }
      return new Result<>(200, "成功", r);
    } else {
      articles = articleMapper.selectArticlesByCategory(category);
      List<Object> r = new ArrayList<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      for (Article article : articles) {
        Map<String, Object> ar = new LinkedHashMap<>();
        String convert = new String(article.getContent(), StandardCharsets.UTF_8);
        ar.put("id", article.getId());
        ar.put("author", article.getAuthor());
        ar.put("title", article.getTitle());
        ar.put("content", convert);
        //代码分行
        List<String> contentList = new ArrayList<>();
        String[] strs = convert.split("\n");
        for (String str : strs) {
          contentList.add(str.toString());
        }
        ar.put("contentList", contentList);
        ar.put("category", article.getCategory());
        ar.put("create_time", sdf.format(article.getCreate_time()));
        ar.put("update_time", article.getUpdate_time());
        r.add(ar);
      }
      return Result.success(ResultStatus.SUCCESS, r);
    }
  }

  @GetMapping("/get_article")
  private Result getArticleByArticleId(@RequestParam("articleId") Integer articleId) {
    Article article = new Article();
    article = articleMapper.selectArticleById(articleId);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Map<String, Object> r = new LinkedHashMap<>();
    List<Map> contentPart = new LinkedList<>();
    String convert = new String(article.getContent(), StandardCharsets.UTF_8);
    r.put("id", article.getId());
    r.put("author", article.getAuthor());
    r.put("title", article.getTitle());
    r.put("content", convert);
    List<Object> contentList = new ArrayList<>();
    String[] strOne = convert.split("\n");
    for (String item : strOne) {
      contentList.add(item.toString());
    }
    String[] strTwo = convert.split("```");
    for (String value : strTwo) {
      int unicode = 0;
      int string = 0;
      Map<String, Object> part = new LinkedHashMap<>();
      for (int j = 0; j < value.toString().length(); j++) {
        char c = value.toString().charAt(j);
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
          string++;
        } else {
          unicode++;
        }
      }
      if (unicode > string) {
        part.put("type", "describe");
        part.put("piece", value.toString());
      } else {
        part.put("type", "code");
        List<Object> piece = new ArrayList<>();
        String[] strThree = value.toString().split("\n");
        for (String s : strThree) {
          piece.add(s.toString());
        }
        part.put("piece", piece);
      }
      contentPart.add(part);
    }
    r.put("contentList", contentList);
    r.put("contentPart", contentPart);
    r.put("category", article.getCategory());
    r.put("create_time", sdf.format(article.getCreate_time()));
    r.put("update_time", article.getUpdate_time());
    return Result.success(ResultStatus.SUCCESS, r);
  }

  @PostMapping("/update_article")
  private Result updateArticle(@RequestBody RArticle rArticle) {
    Article article = new Article();
    article.setId(rArticle.getId());
    article.setCategory(rArticle.getCategory());
    article.setContent(rArticle.getContent().getBytes());
    article.setTitle(rArticle.getTitle());
    if (articleMapper.updateArticle(article) <= 0) {
      return Result.fail();
    }
    return Result.success(ResultStatus.SUCCESS);
  }

  @GetMapping("/delete_article")
  private Result deleteArticle(@RequestParam("articleId") Integer articleId) {
    if (articleId == null) {
      return Result.fail(ResultStatus.HTTP_STATUS_401);
    }
    return Result.success(ResultStatus.SUCCESS);
  }

  @GetMapping("/article_page")
  private Result getArticleByArticleId() {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("count", articleMapper.selectAcountArticle());
    return Result.success(ResultStatus.SUCCESS, resultMap);
  }
}

