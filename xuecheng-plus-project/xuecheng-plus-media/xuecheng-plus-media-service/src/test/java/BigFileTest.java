import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BigFileTest {
    //分块测试
    @Test
    public void testChunk()  {
        //源文件
        File sourceFile=new File("C:\\Users\\张利宁\\Videos\\屏幕录制\\test.mp4");
        //分块文件存放路径
        String chunkFilePath="C:\\Users\\张利宁\\Videos\\屏幕录制\\";
        //分块文件大小
        int chunkSize=7*1024*1024;
        //分块文件数
        int chunkNum= (int) Math.ceil((double) sourceFile.length() /chunkSize);

        //使用流从文件读数据，向分块文件写数据
        try (RandomAccessFile raf_r = new RandomAccessFile(sourceFile, "r")) {
            //缓冲区
            byte[] bytes = new byte[1024*1024];
            for (int i = 0; i < chunkNum; i++) {
                File chunkFile = new File(chunkFilePath + i);
                //分块文件写入流
                try (RandomAccessFile raf_rw = new RandomAccessFile(chunkFile, "rw")) {
                    int len = -1;
                    while ((len = raf_r.read(bytes)) != -1) {
                        raf_rw.write(bytes, 0, len);
                        if (chunkFile.length() >= chunkSize) {
                            break;
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //合并文件
    @Test
    public void testMerge() throws FileNotFoundException {
        //源文件
        File chunkFolder=new File("C:/Users/张利宁/Videos/屏幕录制/chunk/");
        //合并后的文件
        File mergeFile=new File("C:\\Users\\张利宁\\Videos\\屏幕录制\\test1.mp4");
        File[] files = chunkFolder.listFiles();
        if(files==null){
            throw new FileNotFoundException();
        }
        List<File> fileList = Arrays.asList(files);
        fileList.sort(Comparator.comparingInt(c -> Integer.parseInt(c.getName())));
        try (RandomAccessFile raf_rw = new RandomAccessFile(mergeFile, "rw")) {
            byte[] bytes = new byte[1024*1024];

            fileList.forEach((f) -> {
                try (RandomAccessFile raf_r = new RandomAccessFile(f, "r")) {
                    int len = -1;
                    while ((len = raf_r.read(bytes)) != -1) {
                        raf_rw.write(bytes, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
