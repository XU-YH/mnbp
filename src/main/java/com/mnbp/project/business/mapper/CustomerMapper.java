package com.mnbp.project.business.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.dto.CustomerDto;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.domain.vo.WeixinInsuranceInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 客户Mapper接口
 *
 * @author xuyinhui
 * @date 2019-12-26
 */
public interface CustomerMapper {
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
    List<WeixinInsuranceInfoVo> getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo);

    /**
     * 批量插入客户Customer数据
     *
     * @param list 客户数据
     * @return
     */
    int batchInsertCustomer(Collection<Customer> list);

    /**
     * 根据证件号和到检日期查询客户信息
     *
     * @param idNumber         证件号
     * @param examinatidonDate 到检日期
     * @return
     */
    Integer selectCustomerByIdNumber(@Param("idNumber") String idNumber,
            @Param("examinatidonDate") Date examinatidonDate);

    /**
     * 人员导入数据库已有的数据，先将所有数据插入，后修改数据库中存在两条重复记录（证件号和到检日期都相同）的旧数据
     *
     * @param operName 操作人名
     * @param time     修改时间
     * @return
     */
    int updateRepeatCustomer(@Param("operName") String operName, @Param("time") Date time);

    /**
     * 找出重复数据
     *
     * @param idNumber         证件号
     * @param examinatidonDate 到检日期
     * @param createBy         插入人
     * @param createTime       插入时间
     * @return
     */
    CustomerDto selectRepeatCustomer(@Param("idNumber") String idNumber,
            @Param("examinatidonDate") Date examinatidonDate, @Param("createBy") String createBy,
            @Param("createTime") Date createTime);
}
