package com.zscyun.blog.mapper;


import com.zscyun.blog.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
  List<Menu> selectMenuList();

  List<Menu> selectMenuListByUserId(Integer id);
}
