package com.zscyun.blog.mapper;

import com.zscyun.blog.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 蛋炒饭不加蛋
 * <p>
 * 文件
 */
@Mapper
public interface FileMapper {
  int insertFile(FileUpload fileUpload);
}
