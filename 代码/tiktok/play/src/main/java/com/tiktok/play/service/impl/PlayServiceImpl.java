package com.tiktok.play.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiktok.play.domain.dto.UploadProductionDTO;
import com.tiktok.play.domain.po.Production;
import com.tiktok.play.domain.vo.MediaDetailVO;
import com.tiktok.play.client.MediaClient;
import com.tiktok.play.mapper.PlayMapper;
import com.tiktok.play.service.PlayService;
import com.tiktok.play.util.RedisUniqueIdGenerator;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl extends ServiceImpl<PlayMapper,Production> implements PlayService {
    private final PlayMapper playMapper;
    private final MediaClient mediaClient;
    private final RedisUniqueIdGenerator generator;
    private final String ID_KEY_PREFIX="production";
    @Override
    public MediaDetailVO getProduction(Long productionId) {
        //查询数据库信息
        Production production = playMapper.selectById(productionId);
        return new MediaDetailVO(production);
    }
    @GlobalTransactional
    @Override
    public void uploadProduction(MultipartFile file, UploadProductionDTO dto) {
        //获取id
        Long id = generator.nextId(ID_KEY_PREFIX);
        //保存到数据库
        Production production = Production.builder()
                .title(dto.getTitle())
                .duration(dto.getDuration())
                .introduce(dto.getIntroduce())
                .createUser(dto.getUserName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        playMapper.insert(production);

        //上传到minio
        mediaClient.uploadVideo(file,id);
    }
}
