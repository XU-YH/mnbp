package com.mnbp.project.business.service.impl;

import java.util.List;
import com.mnbp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mnbp.project.business.mapper.InsuranceSchemeMapper;
import com.mnbp.project.business.domain.InsuranceScheme;
import com.mnbp.project.business.service.IInsuranceSchemeService;

/**
 * 方案Service业务层处理
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
@Service
public class InsuranceSchemeServiceImpl implements IInsuranceSchemeService 
{
    @Autowired
    private InsuranceSchemeMapper insuranceSchemeMapper;

    /**
     * 查询方案
     * 
     * @param id 方案ID
     * @return 方案
     */
    @Override
    public InsuranceScheme selectInsuranceSchemeById(Integer id)
    {
        return insuranceSchemeMapper.selectInsuranceSchemeById(id);
    }

    /**
     * 查询方案列表
     * 
     * @param insuranceScheme 方案
     * @return 方案
     */
    @Override
    public List<InsuranceScheme> selectInsuranceSchemeList(InsuranceScheme insuranceScheme)
    {
        return insuranceSchemeMapper.selectInsuranceSchemeList(insuranceScheme);
    }

    /**
     * 新增方案
     * 
     * @param insuranceScheme 方案
     * @return 结果
     */
    @Override
    public int insertInsuranceScheme(InsuranceScheme insuranceScheme)
    {
        insuranceScheme.setCreateTime(DateUtils.getNowDate());
        return insuranceSchemeMapper.insertInsuranceScheme(insuranceScheme);
    }

    /**
     * 修改方案
     * 
     * @param insuranceScheme 方案
     * @return 结果
     */
    @Override
    public int updateInsuranceScheme(InsuranceScheme insuranceScheme)
    {
        insuranceScheme.setUpdateTime(DateUtils.getNowDate());
        return insuranceSchemeMapper.updateInsuranceScheme(insuranceScheme);
    }

    /**
     * 批量删除方案
     * 
     * @param ids 需要删除的方案ID
     * @return 结果
     */
    @Override
    public int deleteInsuranceSchemeByIds(Integer[] ids)
    {
        return insuranceSchemeMapper.deleteInsuranceSchemeByIds(ids);
    }

    /**
     * 删除方案信息
     * 
     * @param id 方案ID
     * @return 结果
     */
    @Override
    public int deleteInsuranceSchemeById(Integer id)
    {
        return insuranceSchemeMapper.deleteInsuranceSchemeById(id);
    }

    /**
     * 查询方案代码list
     *
     * @return
     */
    @Override
    public List<String> selectSchemeCodeList() {
        return insuranceSchemeMapper.selectSchemeCodeList();
    }
}
