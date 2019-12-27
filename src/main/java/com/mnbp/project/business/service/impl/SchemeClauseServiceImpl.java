package com.mnbp.project.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnbp.common.utils.DateUtils;
import com.mnbp.project.business.domain.SchemeClause;
import com.mnbp.project.business.domain.dto.SchemeClauseBO;
import com.mnbp.project.business.domain.vo.SchemeClauseVO;
import com.mnbp.project.business.mapper.SchemeClauseMapper;
import com.mnbp.project.business.service.ISchemeClauseService;

/**
 * 方案条款Service业务层处理
 * 
 * @author yinhui xu
 * @date 2019-12-27
 */
@Service
public class SchemeClauseServiceImpl implements ISchemeClauseService {
    @Autowired
    private SchemeClauseMapper schemeClauseMapper;

    /**
     * 查询方案条款
     * 
     * @param id
     *            方案条款ID
     * @return 方案条款
     */
    @Override
    public SchemeClause selectSchemeClauseById(Integer id) {
        return schemeClauseMapper.selectSchemeClauseById(id);
    }

    /**
     * 查询方案条款列表
     * 
     * @param schemeClauseBO
     *            方案条款
     * @return 方案条款
     */
    @Override
    public List<SchemeClauseVO> selectSchemeClauseList(SchemeClauseBO schemeClauseBO) {
        return schemeClauseMapper.selectSchemeClauseList(schemeClauseBO);
    }

    /**
     * 新增方案条款
     * 
     * @param schemeClause
     *            方案条款
     * @return 结果
     */
    @Override
    public int insertSchemeClause(SchemeClause schemeClause) {
        schemeClause.setCreateTime(DateUtils.getNowDate());
        return schemeClauseMapper.insertSchemeClause(schemeClause);
    }

    /**
     * 修改方案条款
     * 
     * @param schemeClause
     *            方案条款
     * @return 结果
     */
    @Override
    public int updateSchemeClause(SchemeClause schemeClause) {
        schemeClause.setUpdateTime(DateUtils.getNowDate());
        return schemeClauseMapper.updateSchemeClause(schemeClause);
    }

    /**
     * 批量删除方案条款
     * 
     * @param ids
     *            需要删除的方案条款ID
     * @return 结果
     */
    @Override
    public int deleteSchemeClauseByIds(Integer[] ids) {
        return schemeClauseMapper.deleteSchemeClauseByIds(ids);
    }

    /**
     * 删除方案条款信息
     * 
     * @param id
     *            方案条款ID
     * @return 结果
     */
    @Override
    public int deleteSchemeClauseById(Integer id) {
        return schemeClauseMapper.deleteSchemeClauseById(id);
    }
}
