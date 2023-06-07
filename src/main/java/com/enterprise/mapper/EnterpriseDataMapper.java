package com.enterprise.mapper;

import com.enterprise.entity.EnterpriseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 企业数据接口
 *
 * @author PrefersMin
 * @version 1.2
 */
@Mapper
public interface EnterpriseDataMapper {

    /**
     * 修改指定配置项的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 配置项名称
     * @param dataValue 配置数据
     *
     * @return 返回修改结果
     */
    @Update("UPDATE enterprise_data SET `data_value`=#{dataValue} WHERE `data_name` = #{dataName}")
    boolean updateEnterpriseDataByDataName(String dataName, String dataValue);

    /**
     * 修改指定配置项的配置数据
     *
     * @author PrefersMin
     *
     * @param enterpriseData  需要修改的配置数据
     * @return 返回修改结果
     */
    @Update("UPDATE enterprise_data SET `data_value`=#{dataValue} ,`data_annotation`=#{dataAnnotation} WHERE `data_name` = #{dataName}")
    boolean updateEnterpriseData(EnterpriseData enterpriseData);

    /**
     * 查询指定配置名称的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 配置名称
     * @return 返回查询结果
     */
    @Select("SELECT * FROM enterprise_data WHERE `data_name` = #{dataName}")
    EnterpriseData queryingEnterpriseData(String dataName);

    /**
     * 查询所有配置数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM enterprise_data")
    List<EnterpriseData> queryingAllEnterpriseData();

}
