package com.recse4cloud.common.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestfullData<T> {
    private String code = "00000";
    private String msg = "操作成功";
    private T data;

    public RestfullData() {
    }

    public RestfullData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
