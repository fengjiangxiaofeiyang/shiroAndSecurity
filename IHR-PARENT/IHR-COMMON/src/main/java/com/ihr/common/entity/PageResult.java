package com.ihr.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T>{
    private long total;
    private List<T> rows;
}
