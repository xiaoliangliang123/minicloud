package com.minicloud.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class IOLogRecordDTO implements Serializable {

    private Long timestamp ;

    private String method;

    private String url ;

    private String contentType;

    private String args ;

    private Object response ;

    private String dateTime;

    private String keyword ;

    private String description;


}
