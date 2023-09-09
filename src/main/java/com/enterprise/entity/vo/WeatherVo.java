package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 天气参数实体类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Data
public class WeatherVo {

    /**
     * 是今日天气还是明日天气
     */
    private int state;

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
