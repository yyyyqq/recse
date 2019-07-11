package com.recse4cloud.common.util.excel;

/**
 * Created by YYQ on 2017/8/21.
 */
public class ExcleTitleEntity {
    private String field;
    private String name;

    public ExcleTitleEntity(String field, String name) {
        this.field = field;
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
