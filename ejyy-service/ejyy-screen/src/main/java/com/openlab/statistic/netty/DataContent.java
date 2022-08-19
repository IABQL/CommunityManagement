package com.openlab.statistic.netty;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataContent implements Serializable {
    private Integer action;//动作类型
    private String userId;
    private Object extend;// 扩展字段
}
