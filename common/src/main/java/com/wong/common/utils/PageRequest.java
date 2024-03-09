package com.wong.common.utils;

import lombok.Data;

/**
 * @author Wongbuer
 * @Description  用于接收分页请求参数
 */
@Data
public class PageRequest {
    private Integer page;
    private Integer size;
    private String sortField;
    private Order order;

    enum Order
    {
        /**
         * 升序排列
         */
        ASC,
        /**
         * 降序排列
         */
        DESC
    }
}
