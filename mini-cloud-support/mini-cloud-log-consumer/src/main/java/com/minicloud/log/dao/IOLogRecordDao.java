package com.minicloud.log.dao;

import cn.hutool.json.JSONUtil;
import com.minicloud.common.dto.IOLogRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IOLogRecordDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void insert(IOLogRecordDTO ioLogRecordDTO) {

        String sql = "insert into iolog(timestamp,method,url,content_type,args,response,data_time,keyword,description) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,ioLogRecordDTO.getTimestamp(),ioLogRecordDTO.getMethod(),ioLogRecordDTO.getUrl(),ioLogRecordDTO.getContentType(),
                JSONUtil.toJsonStr(ioLogRecordDTO.getArgs()),JSONUtil.toJsonStr(ioLogRecordDTO.getResponse()),
                ioLogRecordDTO.getDateTime(),ioLogRecordDTO.getKeyword(),ioLogRecordDTO.getDescription());
    }
}
