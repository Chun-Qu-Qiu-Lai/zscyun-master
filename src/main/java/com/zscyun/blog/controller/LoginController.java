package com.zscyun.blog.controller;

import cn.hutool.core.util.StrUtil;
import com.zscyun.blog.entity.Result;
import com.zscyun.blog.entity.ResultStatus;
import com.zscyun.blog.entity.User;
import com.zscyun.blog.mapper.UserMapper;
import com.zscyun.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/shanzs/blog")
public class LoginController {
  @Autowired
  private UserMapper userMapper;


  @PostMapping("/login")
  private Result login(@RequestBody User userForm) {
    if (!StrUtil.isNotBlank(userForm.getUsername())) {
      return Result.success(ResultStatus.HTTP_STATUS_401, "用户名为空");
    }
    if (!StrUtil.isNotBlank(userForm.getPassword())) {

      return Result.success(ResultStatus.HTTP_STATUS_401, "密码为空");
    }
    User user = userMapper.selectUser(userForm);
    if (user == null) {
      return Result.success(ResultStatus.HTTP_STATUS_401, "认证失败");
    }
    String token = JwtUtil.createToken(user.getUserId());
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("token", token);
    return Result.success(ResultStatus.SUCCESS, resultMap);
  }
}


