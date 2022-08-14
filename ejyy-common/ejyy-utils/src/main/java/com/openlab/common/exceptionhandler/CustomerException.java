package com.openlab.common.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerException extends RuntimeException {
    private Integer code; // 状态码
    private String msg; // 异常信息
}
