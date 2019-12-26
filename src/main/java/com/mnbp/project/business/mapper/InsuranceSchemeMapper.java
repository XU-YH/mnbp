package com.mnbp.project.business.mapper;

import com.mnbp.project.business.domain.InsuranceScheme;
import java.util.List;

/**
 * 方案Mapper接口
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
public interface InsuranceSchemeMapper 
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
     * 删除方案
     * 
     * @param id 方案ID
     * @return 结果
     */
    public int deleteInsuranceSchemeById(Integer id);

    /**
     * 批量删除方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInsuranceSchemeByIds(Integer[] ids);
}
