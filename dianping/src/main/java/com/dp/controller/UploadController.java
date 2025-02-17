package com.dp.controller;

import com.dp.dto.Result;
import com.dp.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final UploadService service;

    /**
     * 上传文件，目前只上传图片
     * @param file 文件
     * @return 文件名
     */
    @PostMapping("/blog")
    public Result<String> upload(@RequestPart("file")MultipartFile file){
       String fileName=service.upload(file);
       return Result.success(fileName);

    }

    /**
     * 删除文件
     * @param filename 文件名
     * @return 是否成功删除
     */
    @GetMapping("/blog/delete")
    public Result<String> delete(@RequestParam("name") String filename) {
        service.delete(filename);
        return Result.success();
    }

}
