package com.enterprise.entity.vo;

import lombok.Data;

@Data
public class ExceptionVo {

    String errorMessage,errorState;
    int errorCode;
    boolean errorCondition;

}
