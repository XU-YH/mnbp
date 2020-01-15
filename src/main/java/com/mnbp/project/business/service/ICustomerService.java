package com.mnbp.project.business.service;

import java.util.List;

import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;

/**
 * 客户Service接口
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
public interface ICustomerService 
{
    /**
     * 查询客户
     * 
     * @param id 客户ID
     * @return 客户
     */
    public Customer selectCustomerById(Integer id);

    /**
     * 查询客户列表
     * 
     * @param customer 客户
     * @return 客户集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户
     * 
     * @param customer 客户
     * @param operName 创建人
     * @return 结果
     */
    AjaxResult insertCustomer(Customer customer, String operName);

    /**
     * 修改客户
     * 
     * @param customer 客户
     * @param operName
     * @return 结果
     */
    AjaxResult updateCustomer(Customer customer, String operName);

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的客户ID
     * @return 结果
     */
    public int deleteCustomerByIds(Integer[] ids);

    /**
     * 删除客户信息
     * 
     * @param id 客户ID
     * @return 结果
     */
    public int deleteCustomerById(Integer id);

    /**
     * 权益查询，查询客户承保信息
     *
     * @param insuranceInfoBo 客户承保信息BO
     * @return
     */
    List<InsuranceInfoVo> selectCustomerInsuranceInfoList(InsuranceInfoBo insuranceInfoBo);

    /**
     *
     * @param customerList excel表
     * @param operName 登陆人名称
     * @return
     */
    AjaxResult importUser(List<Customer> customerList, String operName);

    /**
     * 删除客户，逻辑删除，修改del_flag的值为2
     *
     * @param ids id
     * @param operName
     * @return
     */
    int updateByIdsForDel(Integer[] ids, String operName);
}
