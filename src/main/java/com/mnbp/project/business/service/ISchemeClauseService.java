package com.mnbp.project.business.service;

import java.util.List;

import com.mnbp.project.business.domain.SchemeClause;
import com.mnbp.project.business.domain.bo.SchemeClauseBO;
import com.mnbp.project.business.domain.vo.SchemeClauseVo;

/**
 * 方案条款Service接口
 * 
 * @author yinhui xu
 * @date 2019-12-27
 */
public interface ISchemeClauseService {
    /**
     * 查询方案条款
     * 
     * @param id
     *            方案条款ID
     * @return 方案条款
     */
    public SchemeClause selectSchemeClauseById(Integer id);

    /**
     * 查询方案条款列表
     * 
     * @param schemeClauseBO
     *            方案条款
     * @return 方案条款集合
     */
    public List<SchemeClauseVo> selectSchemeClauseList(SchemeClauseBO schemeClauseBO);

    /**
     * 新增方案条款
     * 
     * @param schemeClause
     *            方案条款
     * @return 结果
     */
    public int insertSchemeClause(SchemeClause schemeClause);

    /**
     * 修改方案条款
     * 
     * @param schemeClause
     *            方案条款
     * @return 结果
     */
    public int updateSchemeClause(SchemeClause schemeClause);

    /**
     * 批量删除方案条款
     * 
     * @param ids
     *            需要删除的方案条款ID
     * @return 结果
     */
    public int deleteSchemeClauseByIds(Integer[] ids);

    /**
     * 删除方案条款信息
     * 
     * @param id
     *            方案条款ID
     * @return 结果
     */
    public int deleteSchemeClauseById(Integer id);
}
