package com.mnbp.project.business.service.impl;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnbp.common.constant.Constants;
import com.mnbp.common.exception.CustomException;
import com.mnbp.common.utils.DateUtils;
import com.mnbp.common.utils.StringUtils;
import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.dto.CustomerDto;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import com.mnbp.project.business.mapper.CustomerMapper;
import com.mnbp.project.business.service.ICustomerService;
import com.mnbp.project.business.service.IInsuranceSchemeService;

/**
 * 客户Service业务层处理
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private IInsuranceSchemeService insuranceSchemeService;

    /**
     * 查询客户
     * 
     * @param id
     *            客户ID
     * @return 客户
     */
    @Override
    public Customer selectCustomerById(Integer id) {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户列表
     * 
     * @param customer
     *            客户
     * @return 客户
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户
     * 
     * @param customer
     *            客户
     * @return 结果
     */
    @Override
    public int insertCustomer(Customer customer) {
        customer.setCreateTime(DateUtils.getNowDate());
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改客户
     * 
     * @param customer
     *            客户
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer) {
        customer.setUpdateTime(DateUtils.getNowDate());
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除客户
     * 
     * @param ids
     *            需要删除的客户ID
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(Integer[] ids) {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除客户信息
     * 
     * @param id
     *            客户ID
     * @return 结果
     */
    @Override
    public int deleteCustomerById(Integer id) {
        return customerMapper.deleteCustomerById(id);
    }

    /**
     * 权益查询，查询客户承保信息
     *
     * @param insuranceInfoBo
     *            客户承保信息BO
     * @return
     */
    @Override
    public List<InsuranceInfoVo> selectCustomerInsuranceInfoList(InsuranceInfoBo insuranceInfoBo) {
        return customerMapper.selectCustomerInsuranceInfoList(insuranceInfoBo);
    }

    /**
     * 导入人员（客户）数据，excel表重复数据以最后一条为准，数据库中已有数据则更新该条数据
     *
     * @param customerList
     *            excel数据
     * @param operName
     *            登陆人名称
     * @return
     */
    @Override
    public AjaxResult importUser(List<Customer> customerList, String operName) {
        if (StringUtils.isNull(customerList) || customerList.size() == 0) {
            throw new CustomException("导入人员数据不能为空！！！");
        }
        // 当前时间
        Date now = new Date();
        // 新增数量
        int insertCount = 0;
        // 错误数量
        int wrongCount = 0;
        // 修改数量
        int updateCount = 0;
        // 方案代码list
        List<String> schemeCodeList = insuranceSchemeService.selectSchemeCodeList();
        // 错误数据
        List<CustomerDto> wrongDataList = new ArrayList<>();
        // 合格数据
        Map<String, Customer> customerMap = new HashMap<>();

        for (Customer customer : customerList) {
            StringBuffer sb = new StringBuffer();
            String idNumber = customer.getIdNumber();
            // 数据校验
            if (StringUtils.isEmpty(customer.getCustomerName())) {
                sb.append("姓名不能为空；");
            }
            if (StringUtils.isEmpty(customer.getIdType())) {
                sb.append("证件类型不能为空；");
            }
            if (StringUtils.isEmpty(idNumber)) {
                sb.append("证件号不能为空；");
            }
            if (customer.getExaminatidonDate() == null) {
                sb.append("到检日期不能为空；");
            }
            if (StringUtils.isEmpty(customer.getBranchName())) {
                sb.append("分公司名称不能为空；");
            }
            if (StringUtils.isEmpty(customer.getSchemeCode())) {
                sb.append("方案代码不能为空；");
            } else if (!schemeCodeList.contains(customer.getSchemeCode())) {
                sb.append("无此方案代码；");
            }
            if (sb.length() > 0) {
                // 错误数据
                CustomerDto customerDto = new CustomerDto();
                BeanUtils.copyProperties(customer, customerDto);
                customerDto.setFailureCause(sb.toString());
                wrongDataList.add(customerDto);
                continue;
            }

            // 更新数据
            Customer oldCustomer = customerMapper.selectCustomerByIdNumber(idNumber);
            if (oldCustomer != null) {
                customer.setId(oldCustomer.getId());
                customer.setUpdateBy(operName);
                customer.setUpdateTime(now);
                customerMapper.updateCustomer(customer);
                updateCount++;
                continue;
            }

            // 正确（新增）数据
            customer.setCreateBy(operName);
            customer.setCreateTime(now);
            // 去重，以最后一条数据为准
            customerMap.put(idNumber, customer);
            // 超过限定数量插入一次
            if (customerMap.size() >= Constants.BATCH_COUNT) {
                // 新增客户人员数据
                insertCount += customerMapper.batchInsertCustomer(customerMap.values());
                customerMap.clear();
            }
        }

        // 新增客户数据
        if (customerMap.size() > 0) {
            insertCount += customerMapper.batchInsertCustomer(customerMap.values());
        }
        // 错误数据以excel表形式返给前端，提供用户下载
        String fileName = "";
        if (wrongDataList.size() > 0) {
            wrongCount = wrongDataList.size();
            ExcelUtil<CustomerDto> excelUtil = new ExcelUtil<>(CustomerDto.class);
            AjaxResult ajaxResult = excelUtil.exportExcel(wrongDataList, "人员导入错误数据");
            // 获取到文件名
            fileName = (String)ajaxResult.get(AjaxResult.MSG_TAG);
        }

        // 返回结果封装
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fileName", fileName);
        dataMap.put("insertCount", insertCount);
        dataMap.put("updateCount", updateCount);
        dataMap.put("wrongCount", wrongCount);

        return AjaxResult.success(dataMap);
    }
}
