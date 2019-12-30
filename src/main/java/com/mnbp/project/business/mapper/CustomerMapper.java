package com.mnbp.project.business.mapper;

import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;

import java.util.List;

/**
 * 客户Mapper接口
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
public interface CustomerMapper 
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
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改客户
     * 
     * @param customer 客户
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 删除客户
     * 
     * @param id 客户ID
     * @return 结果
     */
    public int deleteCustomerById(Integer id);

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerByIds(Integer[] ids);

    /**
     * 权益查询，查询客户承保信息
     *
     * @param insuranceInfoBo 客户承保信息BO
     * @return
     */
    List<InsuranceInfoVo> selectCustomerInsuranceInfoList(InsuranceInfoBo insuranceInfoBo);
}
