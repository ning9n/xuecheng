package com.tiktok.play.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiktok.play.domain.po.Production;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayMapper extends BaseMapper<Production> {
}
