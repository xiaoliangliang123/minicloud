package com.minicloud.files.service.impl;

import com.minicloud.files.config.MinioUtil;

import java.io.File;

public class BaseUploadService {

    private MinioUtil minioUtil;

    /**
     * 临时文件目录
     */
    private String tempUploadDirPath = System.getProperty("user.dir") + File.separator + "temp" + File.separator;

    private File tempFile = new File(tempUploadDirPath);

    public BaseUploadService(MinioUtil minioUtil){
        this.minioUtil = minioUtil;
    }

    public void BaseUploadService() {
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
    }

    public String getTempUploadDirPath(){
        return tempUploadDirPath;
    }

    public MinioUtil getMinioUtil() {
        return minioUtil;
    }

    public File getTempFile() {
        return tempFile;
    }
}
