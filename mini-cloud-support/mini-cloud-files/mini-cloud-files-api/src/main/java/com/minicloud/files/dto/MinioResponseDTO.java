package com.minicloud.files.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author alan.wang
 */


public class MinioResponseDTO implements Serializable {

    private String filename;

    private String originFilename;

    private String username ;

    private String url ;

    public MinioResponseDTO(String fileName,String originFilename,String username) {
        this.filename = fileName;
        this.originFilename = originFilename;
        this.username = username;
    }

    public MinioResponseDTO(String fileName,String originFilename,String username, String url) {
        this.filename = fileName;
        this.originFilename = originFilename;
        this.username = username;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public String getUsername() {
        return username;
    }
}
