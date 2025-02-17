package com.tiktok.play.controller;

import com.tiktok.base.response.Result;
import com.tiktok.play.domain.dto.UploadProductionDTO;
import com.tiktok.play.domain.vo.MediaDetailVO;
import com.tiktok.play.service.PlayService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "播放内容接口",tags = "播放内容接口")
@Controller
@RequiredArgsConstructor
public class PlayController {
    private final PlayService playService;
    /**
     * 查询作品详情
     * 用户发送两次http请求，一次获取视频，一次获取其他信息
     * 本请求获取其他信息
     */
    @GetMapping("/details/{productionId}")
    public Result<MediaDetailVO> getProduction(@PathVariable Long productionId){
        MediaDetailVO vo =playService.getProduction(productionId);
        return Result.success(vo);
    }

    /**
     * 新增作品
     *
     * @return
     */
    @PostMapping("/upload")
    public Result<String> uploadProduction(@RequestPart("file")MultipartFile file, UploadProductionDTO dto){
        playService.uploadProduction(file,dto);
        return Result.success();
    }
}
