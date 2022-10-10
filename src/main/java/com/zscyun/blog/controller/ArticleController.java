package com.zscyun.blog.controller;


import com.zscyun.blog.entity.*;
import com.zscyun.blog.mapper.ArticleMapper;
import com.zscyun.blog.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

  @Value("${dataFormatter.dataFormatterStyleThree}")
  private String dataFormatterStyleThree;

  @Autowired
  private FileMapper fileMapper;

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
      List<Object> resultMap = new ArrayList<>();
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
        resultMap.add(ar);
      }
      return new Result<>(200, "成功", resultMap);
    } else {
      articles = articleMapper.selectArticlesByCategory(category);
      List<Object> resultMap = new ArrayList<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      for (Article article : articles) {
        Map<String, Object> r = new HashMap<>();
        String convert = new String(article.getContent(), StandardCharsets.UTF_8);
        r.put("id", article.getId());
        r.put("author", article.getAuthor());
        r.put("title", article.getTitle());
        r.put("content", convert);
        //代码分行
        List<String> contentList = new ArrayList<>();
        String[] strs = convert.split("\n");
        for (String str : strs) {
          contentList.add(str.toString());
        }
        r.put("contentList", contentList);
        r.put("category", article.getCategory());
        r.put("create_time", sdf.format(article.getCreate_time()));
        r.put("update_time", article.getUpdate_time());
        resultMap.add(r);
      }
      return Result.success(ResultStatus.SUCCESS, resultMap);
    }
  }

  /**
   * 文章详情
   *
   * @param articleId
   * @return
   */
  @GetMapping("/get_article")
  private Result getArticleByArticleId(@RequestParam("articleId") Integer articleId) {
    Article article = new Article();
    article = articleMapper.selectArticleById(articleId);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Map<String, Object> resultMap = new LinkedHashMap<>();
    List<Map> contentPart = new LinkedList<>();
    String convert = new String(article.getContent(), StandardCharsets.UTF_8);
    resultMap.put("id", article.getId());
    resultMap.put("author", article.getAuthor());
    resultMap.put("title", article.getTitle());
    resultMap.put("content", convert);
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
    resultMap.put("contentList", contentList);
    resultMap.put("contentPart", contentPart);
    resultMap.put("category", article.getCategory());
    resultMap.put("create_time", sdf.format(article.getCreate_time()));
    resultMap.put("update_time", article.getUpdate_time());
    return Result.success(ResultStatus.SUCCESS, resultMap);
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
    articleMapper.deleteArticle(articleId);
    return Result.success(ResultStatus.SUCCESS);
  }

  @GetMapping("/article_page")
  private Result getArticleByArticleId() {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("count", articleMapper.selectAcountArticle());
    return Result.success(ResultStatus.SUCCESS, resultMap);
  }

  /**
   * 1.文件保存在服务器，url地址保存在数据库
   * 上传成功之后返回成功保存的url地址
   */
  @PostMapping("/upload")
  public Result upload(@RequestParam MultipartFile file) {
    if (!file.isEmpty()) {
      String uploadPath = "/www/springbootxm/blogImg/";
      // 如果目录不存在则创建
      File uploadDir = new File(uploadPath);
      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }
      //获取原文件名
      String OriginalFilename = file.getOriginalFilename();
      //获取文件后缀名
      String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
      //重新随机生成名字
      Date date = new Date();
      LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      String result = localDateTime.format(DateTimeFormatter.ofPattern(dataFormatterStyleThree));
      String filename = UUID.randomUUID().toString() + "-" + result + suffixName;
      File localFile = new File(uploadPath + filename);
      try {
        //把上传的文件保存至本地
        file.transferTo(localFile);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(filename);
        fileUpload.setFileUrl("https://www.shanzs.top/blogImg/" + filename);
        fileMapper.insertFile(fileUpload);
        return Result.success(ResultStatus.SUCCESS, "https://www.shanzs.top/blogImg/" + filename);
      } catch (IOException e) {
        return Result.fail(ResultStatus.HTTP_STATUS_412);
      }
    } else {
      return Result.fail(ResultStatus.HTTP_STATUS_413);
    }
  }

  @GetMapping("/delete_file")
  public Result deleteFile(@RequestParam("0") String fileNameMd) {
    String fileName = fileNameMd.substring(31);
    String filePath = "/www/springbootxm/blogImg/" + fileName;
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
      return Result.success(ResultStatus.SUCCESS);
    } else {
      return Result.fail(ResultStatus.HTTP_STATUS_414);
    }
  }
}

