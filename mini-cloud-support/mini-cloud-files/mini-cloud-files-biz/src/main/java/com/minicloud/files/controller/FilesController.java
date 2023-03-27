package com.minicloud.files.controller;

import com.minicloud.files.dto.Chunk;
import com.minicloud.files.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author alan.wang
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    /**
     * 上传文件
     * @param file 上传的文件
     * @return filename 文件唯一id
     * */
    @PostMapping
    public ResponseEntity save(@RequestParam MultipartFile file) throws Exception {

        return ResponseEntity.ok(filesService.save(file));
    }

    /**
     * 下载文件
     * @param filename 上传时返回的唯一id
     * @return stream 文件流
     * */
    @GetMapping
    public ResponseEntity download(HttpServletResponse response, @RequestParam String filename) throws Exception {

        filesService.download(response,filename);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * */
    @GetMapping("/metadata")
    public ResponseEntity metadata(@RequestParam String filename) throws Exception {

        return ResponseEntity.ok(filesService.metadata(filename));
    }

    /**
     * 获取文件字节
     * */
    @GetMapping("/bytes")
    public ResponseEntity<byte[]> bytes(@RequestParam("filename") String filename) throws Exception {

        return ResponseEntity.ok(filesService.bytes(filename));
    }

    /**
     * 分片/断点续传文件
     * */
    @PostMapping("/chunk/fileUpload")
    public String uploadPost(@ModelAttribute Chunk chunk, HttpServletResponse response) throws Exception {

        return filesService.chunkFileUpload(chunk,response);
    }
}
