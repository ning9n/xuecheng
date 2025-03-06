package com.bilibili.search.domain.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程索引信息
 * </p>
 *
 * @author itcast
 */
@Data
public class CourseIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private Long id;

    //标题
    private String title;

    // duration：时长，以秒为单位
    private Integer duration;

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

    //图片
    private String pic;

    //发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
