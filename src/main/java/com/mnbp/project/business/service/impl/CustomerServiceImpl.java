package com.mnbp.project.business.service.impl;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnbp.common.constant.Constants;
import com.mnbp.common.enums.DelFlagEnum;
import com.mnbp.common.enums.DictTypeEnum;
import com.mnbp.common.enums.IdTypeEnum;
import com.mnbp.common.exception.CustomException;
import com.mnbp.common.utils.DateUtils;
import com.mnbp.common.utils.IdCardVerifyUtil;
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
import com.mnbp.project.system.domain.SysDictData;
import com.mnbp.project.system.service.impl.SysDictDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysDictDataServiceImpl sysDictDataService;

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
     * 修改客户
     * 
     * @param customer
     *            客户
     * @param operName 修改人
     * @return 结果
     */
    @Override
    public AjaxResult updateCustomer(Customer customer, String operName) {

        // 校验方案代码
        // 方案代码list
        List<String> schemeCodeList = insuranceSchemeService.selectSchemeCodeList();
        if (!schemeCodeList.contains(customer.getSchemeCode())) {
            return AjaxResult.error("修改人员失败【" + customer.getSchemeCode() + "】此方案代码不存在");
        }

        // 校验身份证号
        String idNumber = customer.getIdNumber();
        Date examinatidonDate = customer.getExaminatidonDate();
        if (IdTypeEnum.IDENTITY_CARD.getCode().equals(customer.getIdType())) {
            if (!IdCardVerifyUtil.isValidIdNo(idNumber)) {
                return AjaxResult.error("修改人员失败【" + customer.getIdNumber() + "】身份证不符合规范");
            }
            customer.setBirthdate(DateUtils.parseDate(idNumber.substring(6, 14)));
        }
        // 校验是否存在重复的记录（证件号和到检日期）
        Integer customerId = customerMapper.selectCustomerByIdNumber(idNumber, examinatidonDate);
        if (customerId != null && !customer.getId().equals(customerId)) {
            return AjaxResult.error("修改人员失败，证件号和到检日期存在重复记录");
        }

        customer.setUpdateTime(new Date());
        customer.setUpdateBy(operName);

        return customerMapper.updateCustomer(customer) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 批量删除客户，逻辑删除，修改del_flag的值
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
     * 导入人员（客户）数据，是否为重复数据以证件号和到检日期是否相同为准，采用第一条数据。数据库中已有数据则更新该条数据
     *
     * @param customerList
     *            excel数据
     * @param operName
     *            登陆人名称
     * @return
     */
    @Override
    @Transactional
    public AjaxResult importUser(List<Customer> customerList, String operName) {
        if (StringUtils.isNull(customerList) || customerList.size() == 0) {
            throw new CustomException("导入人员数据不能为空！！！");
        }
        // 当前时间
        Date time = new Date();
        // 新增数量
        int insertCount = 0;
        // 错误数量
        int wrongCount = 0;
        // 修改数量
        int updateCount = 0;
        // 方案代码list
        List<String> schemeCodeList = insuranceSchemeService.selectSchemeCodeList();
        // 证件类型
        List<SysDictData> dictDataList = sysDictDataService.selectDictDataByType(DictTypeEnum.ID_TYPE.getCode());
        // 错误数据
        List<CustomerDto> wrongDataList = new ArrayList<>();
        // 新增数据
        List<Customer> insertDataList = new ArrayList<>();
        // 正确数据
        Set<Customer> rightCustomerSet = new TreeSet<>((o1, o2) -> {
            int num = o1.getIdNumber().compareToIgnoreCase(o2.getIdNumber());
            return num == 0 ? o1.getExaminatidonDate().compareTo(o2.getExaminatidonDate()) : num;
        });

        for (Customer customer : customerList) {
            String idNumber = customer.getIdNumber();
            Date examinatidonDate = customer.getExaminatidonDate();
            // 数据校验
            boolean isRight = checkExcelData(customer, schemeCodeList, dictDataList, wrongDataList, rightCustomerSet);
            if (!isRight) {
                continue;
            }

            rightCustomerSet.add(customer);

            // 更新数据
            // Integer oldCustomerId = customerMapper.selectCustomerByIdNumber(idNumber, examinatidonDate);
            // if (oldCustomerId != null) {
            //     customer.setId(oldCustomerId);
            //     customer.setUpdateBy(operName);
            //     customer.setUpdateTime(now);
            //     customerMapper.updateCustomer(customer);
            //     updateCount++;
            //     continue;
            // }

            // 新增数据
            customer.setCreateBy(operName);
            customer.setCreateTime(time);
            insertDataList.add(customer);
            // 超过限定数量插入一次
            if (insertDataList.size() >= Constants.BATCH_COUNT) {
                // 新增客户人员数据
                insertCount += customerMapper.batchInsertCustomer(insertDataList);
                insertDataList.clear();
            }
        }

        // 新增客户数据
        if (insertDataList.size() > 0) {
            insertCount += customerMapper.batchInsertCustomer(insertDataList);
        }

        // 人员导入数据库已有的数据，先将所有数据插入，后修改数据库中存在两条重复记录（证件号和到检日期都相同）的旧数据
        updateCount = customerMapper.updateRepeatCustomer(operName, time);

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
        dataMap.put("insertCount", insertCount - updateCount);
        dataMap.put("updateCount", updateCount);
        dataMap.put("wrongCount", wrongCount);

        return AjaxResult.success(dataMap);
    }

    /**
     * 删除客户，逻辑删除，修改del_flag的值为2
     *
     * @param ids
     *            id
     * @param operName
     * @return
     */
    @Override
    public int updateByIdsForDel(Integer[] ids, String operName) {
        Date time = new Date();
        int count = 0;
        for (Integer id : ids) {
            Customer customer = new Customer();
            customer.setId(id);
            customer.setDelFlag(DelFlagEnum.DELETED.getCode());
            customer.setUpdateBy(operName);
            customer.setUpdateTime(time);
            count += customerMapper.updateCustomer(customer);
        }

        return count;
    }

    /**
     * 新增人员（客户）
     *
     * @param customer
     *            输入信息
     * @param operName
     *            创建人
     * @return
     */
    @Override
    public AjaxResult insertCustomer(Customer customer, String operName) {
        // 校验方案代码
        // 方案代码list
        List<String> schemeCodeList = insuranceSchemeService.selectSchemeCodeList();
        if (!schemeCodeList.contains(customer.getSchemeCode())) {
            return AjaxResult.error("修改人员失败【" + customer.getSchemeCode() + "】此方案代码不存在");
        }
        // 校验身份证号
        String idNumber = customer.getIdNumber();
        Date examinatidonDate = customer.getExaminatidonDate();
        if (IdTypeEnum.IDENTITY_CARD.getCode().equals(customer.getIdType())) {
            if (!IdCardVerifyUtil.isValidIdNo(idNumber)) {
                return AjaxResult.error("新增人员失败【" + customer.getIdNumber() + "】身份证不符合规范");
            }
            customer.setBirthdate(DateUtils.parseDate(idNumber.substring(6, 14)));
        }
        // 校验是否存在重复的记录（证件号和到检日期）
        if (customerMapper.selectCustomerByIdNumber(idNumber, examinatidonDate) != null) {
            return AjaxResult.error("新增人员失败，证件号和到检日期存在重复记录");
        }

        customer.setCreateTime(new Date());
        customer.setCreateBy(operName);

        return customerMapper.insertCustomer(customer) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 人员导入数据校验： 姓名、证件类型、证件号、性别、出生日期、联系电话、到检日期、方案代码不能为空。 证件号若为身份证，以从证件号中获取出生日期为准 证件号和到检日期组成唯一索引，重复数据为错误数据
     * 
     * @param customer
     *            人员信息
     * @param schemeCodeList
     *            方案代码
     * @param dictDataList
     *            证件类型
     * @param wrongDataList
     *            错误数据
     * @param rightCustomerSet
     *            正确数据
     * @return
     */
    private boolean checkExcelData(Customer customer, List<String> schemeCodeList, List<SysDictData> dictDataList,
        List<CustomerDto> wrongDataList, Set<Customer> rightCustomerSet) {
        boolean isRight = true;

        // 错误信息
        StringBuffer sb = new StringBuffer();
        String idNumber = customer.getIdNumber();
        Date examinatidonDate = customer.getExaminatidonDate();

        if (StringUtils.isEmpty(customer.getCustomerName())) {
            sb.append("姓名不能为空；");
        }
        if (examinatidonDate == null) {
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
        if (StringUtils.isEmpty(customer.getProvince())) {
            sb.append("省份不能为空；");
        }
        if (StringUtils.isEmpty(customer.getCity())) {
            sb.append("城市不能为空；");
        }
        if (StringUtils.isEmpty(customer.getBranchName())) {
            sb.append("分公司名不能为空；");
        }
        if (StringUtils.isNotEmpty(customer.getPhonenumber()) && customer.getPhonenumber().length() > 20) {
            sb.append("联系电话太长了；");
        }
        String idType = customer.getIdType();
        if (StringUtils.isEmpty(idType)) {
            sb.append("证件类型不能为空；");
        } else {
            boolean dictFlag = true;
            for (SysDictData dictData : dictDataList) {
                if (dictData.getDictValue().equals(idType)) {
                    dictFlag = false;
                    break;
                }
            }
            if (dictFlag) {
                sb.append("无此证件类型；");
            } else {
                if (StringUtils.isEmpty(idNumber)) {
                    sb.append("证件号不能为空；");
                } else if (idNumber.length() > 20) {
                    sb.append("证件号太长了；");
                } else if (IdTypeEnum.IDENTITY_CARD.getCode().equals(idType)) {
                    // 校验身份证是否符合规范
                    if (IdCardVerifyUtil.isValidIdNo(idNumber)) {
                        customer.setBirthdate(DateUtils.parseDate(idNumber.substring(6, 14)));
                    } else {
                        sb.append("证件号不符合身份证规范；");
                    }
                } else if (customer.getBirthdate() == null) {
                    sb.append("出生日期不能为空；");
                }
            }
        }
        // 将证件号和到检日期相同的数据归为错误数据，原因为重复数据
        if (sb.length() <= 0 && rightCustomerSet.contains(customer)) {
            sb.append("该条记录为重复数据（证件号和到检日期相同即重复）；");
        }
        if (sb.length() > 0) {
            // 错误数据
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerDto);
            customerDto.setFailureCause(sb.toString());
            wrongDataList.add(customerDto);
            isRight = false;
        }

        return isRight;
    }
}
