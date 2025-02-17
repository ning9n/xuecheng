package com.tiktok.play.client;

import com.tiktok.base.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("media")
public interface MediaClient {
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> uploadVideo(@RequestPart("file")MultipartFile multipartFile, @RequestParam Long productionId);
}
