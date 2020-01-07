package com.mnbp.project.business.mapper;

import java.util.Collection;
import java.util.List;

import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.domain.vo.WeixinInsuranceInfoVo;

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

    /**
     * 微信查询承保信息
     *
     * @param weixinInsuranceInfoBo 承保信息查询参数
     * @return
     */
    WeixinInsuranceInfoVo getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo);

    /**
     * 批量插入客户Customer数据
     *
     * @param list 客户数据
     * @return
     */
    int batchInsertCustomer(Collection<Customer> list);

    /**
     * 根据证件号查询客户信息
     *
     * @param idNumber 证件号
     * @return
     */
    Customer selectCustomerByIdNumber(String idNumber);
}
