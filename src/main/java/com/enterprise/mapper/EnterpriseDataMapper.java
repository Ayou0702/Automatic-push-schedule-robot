package com.enterprise.mapper;

import com.enterprise.entity.EnterpriseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 负责enterpriseData表的数据交互
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface EnterpriseDataMapper {

    /**
     * 根据配置名称查询enterpriseData表中的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    @Select("SELECT * FROM enterprise_data WHERE `data_name` = #{dataName}")
    EnterpriseData queryingEnterpriseData(String dataName);

    /**
     * 获取enterpriseData表中的所有配置数据
     *
     * @author PrefersMin
     *
     * @return 返回所有配置数据
     */
    @Select("SELECT * FROM enterprise_data")
    List<EnterpriseData> queryingAllEnterpriseData();


    /**
     * 修改enterpriseData表中的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 需要修改的配置数据名称
     * @param dataValue 需要修改的配置数据值
     *
     * @return 返回受影响的行数，用以判断是否修改成功
     */
    @Update("UPDATE enterprise_data SET `data_value`=#{dataValue} WHERE `data_name` = #{dataName}")
    boolean updateEnterpriseDataByDataName(String dataName, String dataValue);

    /**
     * 修改enterpriseData表中的配置数据
     *
     * @author PrefersMin
     *
     * @param enterpriseData  需要修改的配置数据
     * @return 返回受影响的行数，用以判断是否修改成功
     */
    @Update("UPDATE enterprise_data SET `data_value`=#{dataValue} ,`data_annotation`=#{dataAnnotation} WHERE `data_name` = #{dataName}")
    boolean updateEnterpriseData(EnterpriseData enterpriseData);

}
