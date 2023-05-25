package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 天气参数
 *
 * @author PrefersMin
 * @version 1.0
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
    private String dayWeather;
    /**
     * 最低温度
     */
    private String nightTemp;
    /**
     * 最高温度
     */
    private String dayTemp;
    /**
     * 当前天气预报地区
     */
    private String area;
}
