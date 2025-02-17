package com.tiktok.play.domain.dto;

import lombok.Data;

@Data
public class UploadProductionDTO {
    private Long userName;
    // title: 标题，作品的名称
    private String title;
    // duration：时长，以秒为单位
    //TODO 时长改为由程序识别
    private Integer duration;

    // introduce: 简介，作品的简介信息
    private String introduce;
}
