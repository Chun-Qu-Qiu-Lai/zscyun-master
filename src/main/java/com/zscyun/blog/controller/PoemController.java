package com.zscyun.blog.controller;


import com.zscyun.blog.entity.Result;
import com.zscyun.blog.entity.ResultStatus;
import com.zscyun.blog.mapper.PoemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蛋炒饭不加蛋
 *
 * 诗句标签获取
 */
@CrossOrigin
@RestController
@RequestMapping("/blog")
public class PoemController {
  @Autowired
  private PoemMapper poemMapper;

  @GetMapping("/getPoem")
  private Result getPoem() {
    return Result.success(ResultStatus.SUCCESS, poemMapper.getPoemByRandom());
  }
}
