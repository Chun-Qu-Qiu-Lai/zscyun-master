package com.zscyun.blog.mapper;


import com.zscyun.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 72431
 */
@Mapper
public interface UserMapper {
  User selectUser(User user);

  User selectUserByUserId(Integer id);
}
