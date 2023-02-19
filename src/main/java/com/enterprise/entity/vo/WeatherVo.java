package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 天气参数
 */
@Data
public class WeatherVo {
    /**
     * 日期
     */
    private String date;
    /**
     * 当前天气
     */
    private String weather;
    /**
     * 最低温度
     */
    private String lowest;
    /**
     * 最高温度
     */
    private String highest;
    /**
     * 当前天气预报地区
     */
    private String area;
}
