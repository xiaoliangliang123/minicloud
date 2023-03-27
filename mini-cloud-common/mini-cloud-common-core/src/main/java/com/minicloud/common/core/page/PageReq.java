package com.minicloud.common.core.page;

import lombok.Data;

/**
 * @Author alan.wang
 */
@Data
public class PageReq {

    /** 每页数量 */
    private Integer size = 10;

    /** 页码 */
    private Integer page = 1;

    /** 总数量 */
    private Integer total ;

    private Object data;


    public Integer fromIndex(){
        return (page<=0?1:page -1)*size;
    }

}
