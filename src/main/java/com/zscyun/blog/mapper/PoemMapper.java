package com.zscyun.blog.mapper;

import com.zscyun.blog.entity.Poem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoemMapper {
  Poem getPoemByRandom();
}
