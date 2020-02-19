/**
 * @author: xu_yh
 * @date: 2020/2/13 15:07
 * @Copyright ©2020 xu_yh. All rights reserved.
 */
package com.mnbp.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.mnbp.common.constant.Constants;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.CustomerRepeatBo;
import com.mnbp.project.business.domain.dto.CustomerDto;
import com.mnbp.project.business.mapper.CustomerMapper;
import com.mnbp.project.business.service.ICustomerService;
import com.mnbp.project.system.domain.SysDictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 人员excel导入 （异常处理）转换器
 *
 * @author: xu_yh
 * @date: 2020/2/13 15:07
 * @version: V1.0
 */
public class CustomerExceptionListener extends AnalysisEventListener<Customer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerExceptionListener.class);

    // excel头数据
    private Map<Integer, String> headMap = null;

    // 操作时间
    private Date time = new Date();
    // 新增数量
    private int insertCount = 0;

    private ICustomerService customerService;
    private CustomerMapper customerMapper;
    // 操作人
    private String operName = "";
    // 方案代码list
    private List<String> schemeCodeList;
    // 证件类型
    private List<SysDictData> dictDataList;
    // 错误数据
    private List<CustomerDto> wrongDataList;
    // 正确数据
    private Set<CustomerRepeatBo> rightCustomerSet = new HashSet<>(10);

    // 新增数据
    private List<Customer> insertDataList = new ArrayList<>();
    private Map<String, Object> dataMap;

    public CustomerExceptionListener() {
    }

    public CustomerExceptionListener(String operName, ICustomerService customerService, CustomerMapper customerMapper,
            List<String> schemeCodeList, List<SysDictData> dictDataList, List<CustomerDto> wrongDataList,
            Map<String, Object> dataMap) {
        this.operName = operName;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.schemeCodeList = schemeCodeList;
        this.dictDataList = dictDataList;
        this.wrongDataList = wrongDataList;
        this.dataMap = dataMap;
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 解析异常同样归类为错误数据，如日期解析错误
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            LOGGER.error("第{}行，{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex() + 1,
                    headMap.get(excelDataConvertException.getColumnIndex()), excelDataConvertException.getCellData());
            CustomerDto customerDto = new CustomerDto();
            customerDto.setFailureCause("第" + (excelDataConvertException.getRowIndex() + 1) + "行，" + headMap
                    .get(excelDataConvertException.getColumnIndex()) + "列解析异常，数据为:" + excelDataConvertException.getCellData());
            wrongDataList.add(customerDto);
        }
    }

    /**
     * excel表头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void invoke(Customer customer, AnalysisContext context) {
        // 数据校验
        boolean isRight = customerService
                .checkExcelData(customer, schemeCodeList, dictDataList, wrongDataList, rightCustomerSet);
        if (!isRight) {
            return;
        }

        rightCustomerSet.add(new CustomerRepeatBo(customer.getIdNumber(), customer.getExaminatidonDate()));

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

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        insertCount += customerMapper.batchInsertCustomer(insertDataList);
        dataMap.put("insertCount", insertCount);
        LOGGER.info("----------------- 人员导入excel表完成! -----------------");
    }
}