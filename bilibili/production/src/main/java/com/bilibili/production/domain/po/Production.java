package com.bilibili.production.domain.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class Production {
    // id: 主键，唯一标识每个作品
    private Long id;

    // title: 标题，作品的名称
    private String title;

    // duration：时长，以秒为单位
    private Integer duration;

    // introduce: 简介，作品的简介信息
    private String introduce;

    // play_count: 播放量，作品被播放的次数
    private Integer playCount;

    // like_count: 点赞数，作品收到的点赞数量
    private Integer likeCount;

    // comment_count: 评论数，作品收到的评论数量
    private Integer commentCount;

    // favorite_count: 收藏数，作品被用户收藏的数量
    private Integer favoriteCount;

    // createUser:创建者
    private Long createUser;

    // create_time: 发布时间，作品的上传或发布日期和时间
    private LocalDateTime createTime;

    // update_time: 更新时间，作品的最后更新日期和时间
    private LocalDateTime updateTime;
}
