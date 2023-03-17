package com.enterprise.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 负责enterpriseData表的数据交互
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:49
 */
@Mapper
public interface EnterpriseDataMapper {

    /**
     * 查询enterpriseData表中的配置数据
     *
     * @param dataName 要获取的参数名称
     *
     * @return 返回参数值
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:49
     */
    @Select("SELECT data_value FROM enterprise_data WHERE `data_name` = #{dataName}")
    String queryingEnterpriseData(String dataName);

    /**
     * 修改enterpriseData表中的配置数据
     *
     * @param dataName  要修改的参数名称
     * @param dataValue 要修改的参数值
     * @return 返回受影响的行数，用以判断是否修改成功
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:49
     */
    @Update("UPDATE enterprise_data SET `data_value`=#{dataValue} WHERE `data_name` = #{dataName}")
    int updateEnterpriseData(String dataName, String dataValue);
}
