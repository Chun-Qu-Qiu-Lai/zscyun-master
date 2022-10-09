package com.zscyun.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蛋炒饭不加蛋
 * <p>
 * 文件
 */
public class FileUpload implements Serializable {
  private Integer fileId;
  private String fileUrl;
  private String fileName;
  private Date create_time;
  private Date update_time;

  public FileUpload() {
  }

  public FileUpload(Integer fileId, String fileUrl, String fileName, Date create_time, Date update_time) {
    this.fileId = fileId;
    this.fileUrl = fileUrl;
    this.fileName = fileName;
    this.create_time = create_time;
    this.update_time = update_time;
  }

  public Integer getFileId() {
    return fileId;
  }

  public void setFileId(Integer fileId) {
    this.fileId = fileId;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Date getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Date create_time) {
    this.create_time = create_time;
  }

  public Date getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(Date update_time) {
    this.update_time = update_time;
  }
}
