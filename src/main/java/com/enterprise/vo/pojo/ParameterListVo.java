package com.enterprise.vo.pojo;

import lombok.Data;

/**
 * 推送参数实体类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Data
public class ParameterListVo {

    /**
     * 天气参数
     */
    private WeatherVo weatherVo;

    /**
     * 彩虹屁
     */
    private String caiHongPi;

    /**
     * 计算距离放假天数
     */
    private Integer dateEnding;

    /**
     * 计算开学天数
     */
    private Integer dateStarting;

}
