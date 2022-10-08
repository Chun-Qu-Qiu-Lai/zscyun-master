package com.zscyun.blog.controller;

import com.zscyun.blog.entity.Result;
import com.zscyun.blog.entity.ResultStatus;
import com.zscyun.blog.mapper.MenuMapper;
import com.zscyun.blog.mapper.UserMapper;
import com.zscyun.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/shanzs/blog")
public class MenuController {
  @Autowired
  private MenuMapper menuMapper;

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/get_menus")
  private Result listMenu(@RequestHeader(value = "Authorization", required = false) String authorizationJwt) {
    if (authorizationJwt == null) {
      return Result.success(ResultStatus.SUCCESS, menuMapper.selectMenuList());
    } else {
      String token = authorizationJwt.substring(6);
      Integer id = JwtUtil.parseToken(token);
      Integer userId = null;
      try {
        userId = userMapper.selectUserByUserId(id).getUserId();
      } catch (Exception e) {
        System.out.println("挖矿1");
        return Result.success(ResultStatus.SUCCESS, menuMapper.selectMenuList());
      }
      if (userId == null) {
        return Result.success(ResultStatus.SUCCESS, menuMapper.selectMenuList());
      } else {
        System.out.println("挖矿");
        return Result.success(ResultStatus.SUCCESS, menuMapper.selectMenuListByUserId(userId));
      }
    }
  }
}
