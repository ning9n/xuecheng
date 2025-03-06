package com.bilibili.media.controller;

import com.xuecheng.base.model.RestResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频管理
 */
@RestController
@RequestMapping("/video")
@Api("视频管理")
public class VideoController {
    /**
     * 上传视频
     * 在minio存放路径：年/月/日/视频id
     * 分块文件存放路径：年/月/日/视频id/chunk/num
     * @param video 视频
     * @param num 视频的第几块
     * @param id 文件id，作为存放路径
     * @return 是否成功
     */
    @PostMapping("upload")
    public RestResponse<Boolean> uploadChunk(@RequestPart("data")MultipartFile video,Integer num,Integer id){
        return RestResponse.validfail("功能未完成");
    }

    /**
     * 判断文件的哪些块上传成功
     * @param id 文件id
     * @return 块的序号集合
     */
    @PostMapping("check")
    public RestResponse<List<Integer>> check(Integer id){
        return RestResponse.validfail("功能未完成");
    }

    /**
     * 合并分块文件
     * @param id 文件id
     * @return 是否成功
     */
    @PostMapping("merge")
    public RestResponse<Boolean> merge(Integer id){
        return RestResponse.validfail("功能未完成");
    }
}
