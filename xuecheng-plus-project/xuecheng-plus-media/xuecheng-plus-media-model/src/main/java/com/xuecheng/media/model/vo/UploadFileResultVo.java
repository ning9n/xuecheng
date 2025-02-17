package com.xuecheng.media.model.vo;

import com.xuecheng.media.model.po.MediaFiles;
import lombok.Data;

/**
 * @author Mr.M
 * @version 1.0
 * @description 上传普通文件成功响应结果
 * @date 2022/9/12 18:49
 */
@Data
public class UploadFileResultVo extends MediaFiles {
    /**
     * 文件名称
     */
    private String filename;


    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;

    /**
     * 上传人
     */
    private String username;

    /**
     * 备注
     */
    private String remark;


}