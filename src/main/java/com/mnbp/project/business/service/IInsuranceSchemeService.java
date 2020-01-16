package com.mnbp.project.business.service;

import com.mnbp.project.business.domain.InsuranceScheme;
import java.util.List;

/**
 * 方案Service接口
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
public interface IInsuranceSchemeService
{
    /**
     * 查询方案
     *
     * @param id 方案ID
     * @return 方案
     */
    public InsuranceScheme selectInsuranceSchemeById(Integer id);

    /**
     * 查询方案列表
     *
     * @param insuranceScheme 方案
     * @return 方案集合
     */
    public List<InsuranceScheme> selectInsuranceSchemeList(InsuranceScheme insuranceScheme);

    /**
     * 新增方案
     *
     * @param insuranceScheme 方案
     * @return 结果
     */
    public int insertInsuranceScheme(InsuranceScheme insuranceScheme);

    /**
     * 修改方案
     *
     * @param insuranceScheme 方案
     * @return 结果
     */
    public int updateInsuranceScheme(InsuranceScheme insuranceScheme);

    /**
     * 批量删除方案
     *
     * @param ids 需要删除的方案ID
     * @return 结果
     */
    public int deleteInsuranceSchemeByIds(Integer[] ids);

    /**
     * 删除方案信息
     *
     * @param id 方案ID
     * @return 结果
     */
    public int deleteInsuranceSchemeById(Integer id);

    /**
     * 查询方案代码list
     *
     * @return
     */
    List<String> selectSchemeCodeList();

    /**
     * 删除客户，逻辑删除，修改del_flag的值为2
     *
     * @param ids ids
     * @return
     */
    String updateByIdsForDel(Integer[] ids);

    /**
     * 根据方案代码查找方案
     *
     * @param insuranceScheme
     * @return
     */
    InsuranceScheme selectBySchemeCode(InsuranceScheme insuranceScheme);
}
