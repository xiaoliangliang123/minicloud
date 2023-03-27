package com.minicloud.files.service;

import com.minicloud.files.dto.Chunk;
import com.minicloud.files.dto.MinioResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author alan.wang
 */
public interface FilesService {
    String save(MultipartFile file) throws Exception;

    void download(HttpServletResponse response, String filename);

    MinioResponseDTO metadata(String filename);

    byte[] bytes(String filename) throws IOException;


    /**
     * @param chunk 分片
     * @param response 相应流，返回status 201-部分成功,200-完全成功
     * @return String 上传成功后minio返回的 id
     * */
    String chunkFileUpload(Chunk chunk, HttpServletResponse response) throws Exception;

}
