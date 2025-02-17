package com.dp.service.impl;

import cn.hutool.core.lang.UUID;
import com.dp.service.UploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;
    private final Long SIZE= (long) (6*1024*1024);

    /**
     * 上传文件
     * @param file 文件
     * @return 文件名
     */
    //TODO 检查代码
    @Override
    public String upload(MultipartFile file) {
        String filename=createNewFileName();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .object(filename)
                    .bucket(bucket)
                    .stream(file.getInputStream(),file.getSize(),SIZE)
                    .build());
        } catch (Exception e) {
            log.error("上传文件失败");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return filename;
    }

    /**
     * 删除文件
     * @param filename 文件名
     */
    @Override
    public void delete(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .object(filename)
                    .bucket(bucket)
                    .build());
        } catch (Exception e) {
            log.error("文件删除失败，文件名：{}",filename);
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成文件名
     * @return 文件名
     */
    //TODO 考虑通过数据库校验文件名或使用redis生成分布式ID
    private String createNewFileName() {
        return UUID.randomUUID().toString(true);
    }
}
