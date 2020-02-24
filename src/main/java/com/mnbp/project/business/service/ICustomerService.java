package com.mnbp.project.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.CustomerRepeatBo;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.dto.CustomerDto;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import com.mnbp.project.system.domain.SysDictData;
import org.springframework.web.multipart.MultipartFile;

/**
 * 客户Service接口
 *
 * @author xuyinhui
 * @date 2019-12-26
 */
public interface ICustomerService {
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
     * 删除客户，逻辑删除，修改del_flag的值为2
     *
     * @param ids      id
     * @param operName
     * @return
     */
    int updateByIdsForDel(Integer[] ids, String operName);

    /**
     * 人员导入数据校验： 姓名、证件类型、证件号、性别、出生日期、联系电话、到检日期、方案代码不能为空。 证件号若为身份证，以从证件号中获取出生日期为准 证件号和到检日期组成唯一索引，重复数据为错误数据
     *
     * @param customer          人员信息
     * @param schemeCodeList    方案代码
     * @param dictDataList      证件类型
     * @param wrongDataList     错误数据
     * @param rightCustomerSet  正确数据
     * @param repeatCustomerSet 重复数据
     * @return
     */
    boolean checkExcelData(Customer customer, List<String> schemeCodeList, List<SysDictData> dictDataList,
            List<CustomerDto> wrongDataList, Set<CustomerRepeatBo> rightCustomerSet,
            Set<CustomerRepeatBo> repeatCustomerSet);

    /**
     * 人员导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    AjaxResult importUser(MultipartFile file, String operName) throws IOException;
}
