import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinioTest {
    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.128:9000")
                    .credentials("root", "password")
                    .build();

    @Test
    public void uploadTest() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("test")
                        .object("test.zip")
                        .filename("D:\\学成在线\\学成在线项目—资料\\day05 媒资管理 Nacos Gateway MinIO\\资料\\nacos配置\\nacos_config_export.zip")
                        .contentType(mimeType)
                        .build());
    }

    @Test
    public void deleteTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("test")
                .object("test.zip")
                .build());
    }

    @Test
    public void downloadTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.downloadObject(DownloadObjectArgs.builder()
                .bucket("test")
                .filename("C:/Users/张利宁/Videos/屏幕录制/chunk1")
                .object("merge1.mp4")
                .build());
    }

    //查询文件
    @Test
    public void getFile() throws IOException {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("test").object("test.zip").build();
        try (
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream(new File("D:\\学成在线\\学成在线项目—资料\\day05 媒资管理 Nacos Gateway MinIO\\资料\\test.zzip"));
        ) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //校验文件的完整性对文件的内容进行md5
        FileInputStream fileInputStream1 = new FileInputStream(new File("D:\\学成在线\\学成在线项目—资料\\day05 媒资管理 Nacos Gateway MinIO\\资料\\test.zzip"));
        String source_md5 = DigestUtils.md5Hex(fileInputStream1);
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\学成在线\\学成在线项目—资料\\day05 媒资管理 Nacos Gateway MinIO\\资料\\test.zzip"));
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if (source_md5.equals(local_md5)) {
            System.out.println("下载成功");
        }
    }

    //分块文件上传到minio
    @Test
    public void uploadChunk() throws IOException {
        File chunkFolder = new File("C:/Users/张利宁/Videos/屏幕录制/chunk/");
        File[] files = chunkFolder.listFiles();
        if (files == null) {
            throw new IOException("文件不存在或无法访问");
        }
        int num = files.length;
        List<File> fileList = Arrays.asList(files);
        fileList.sort(Comparator.comparingInt(f -> Integer.parseInt(f.getName())));
        fileList.forEach((f) -> {
            UploadObjectArgs args;
            try {
                args= UploadObjectArgs.builder()
                        .bucket("test")
                        .object("chunk/"+f.getName())
                        .filename(f.getAbsolutePath())
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                minioClient.uploadObject(args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    //合并分块文件
    @Test
    public void testMerge() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<ComposeSource> list=new ArrayList<>();
        for(int i=0;i<7;i++) {
            list.add(ComposeSource.builder().bucket("test").object("chunk/" + i).build());
        }

        List<ComposeSource> list1 = Stream.iterate(0, i -> ++i)
                .limit(7)
                .map(i -> ComposeSource
                        .builder()
                        .bucket("test")
                        .object("chunk/" + i)
                        .build())
                .collect(Collectors.toList());

        ComposeObjectArgs args=ComposeObjectArgs
                .builder()
                .bucket("test")
                .object("merge1.mp4")
                .sources(list)
                .build();
        minioClient.composeObject(args);
    }
    //批量删除文件
    @Test

    public void deleteBatch() {

    }

}
