package com.minicloud.files.service.impl;



import com.minicloud.files.config.MinioUtil;
import com.minicloud.files.dto.Chunk;
import com.minicloud.files.dto.MinioResponseDTO;
import com.minicloud.files.service.FilesService;
import com.minicloud.files.service.UploadService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

/**
 * @Author alan.wang
 */
@Service
public class FilesServiceImpl implements FilesService {

    @Resource
    private UploadService uploadService;

    @Autowired
    private MinioUtil minioUtil;

    @Value("${minio.bucketName}")
    private String bucketName;



    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String save(MultipartFile file) throws Exception {

        LocalDate localDate = LocalDate.now();
        String dir = DateTimeFormatter.ofPattern("YYYYMMdd").format(localDate);
        MinioResponseDTO minioResponseDTO = minioUtil.upload(file, bucketName, dir);
        return minioResponseDTO.getFilename();
    }

    @Override
    public void download(HttpServletResponse response, String filename) {
        minioUtil.download(response, bucketName, filename);
    }

    @SneakyThrows
    @Override
    public MinioResponseDTO metadata(String filename) {

        Map metadataMap = minioUtil.metadata(bucketName, filename);
        String originFileName = (String) ((ArrayList) metadataMap.get("x-amz-meta-originfilename")).get(0);
        String username = (String) ((ArrayList) metadataMap.get("x-amz-meta-username")).get(0);
        originFileName = new String(Base64.getDecoder().decode(originFileName));
        MinioResponseDTO minioResponseDTO = new MinioResponseDTO(filename, originFileName, username);
        return minioResponseDTO;
    }


    @Override
    public byte[] bytes(String filename) throws IOException {

        byte[] bytes = minioUtil.bytes(bucketName, filename);
        return bytes;
    }

    @Override
    public String chunkFileUpload(Chunk chunk, HttpServletResponse response) throws Exception {

        return uploadService.chunkFileUpload(chunk,bucketName,response);

    }

}
