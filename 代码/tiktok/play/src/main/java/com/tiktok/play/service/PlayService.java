package com.tiktok.play.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.play.domain.dto.UploadProductionDTO;
import com.tiktok.play.domain.po.Production;
import com.tiktok.play.domain.vo.MediaDetailVO;
import org.springframework.web.multipart.MultipartFile;

public interface PlayService extends IService<Production> {
    MediaDetailVO getProduction(Long productionId);

    void uploadProduction(MultipartFile file, UploadProductionDTO dto);
}
