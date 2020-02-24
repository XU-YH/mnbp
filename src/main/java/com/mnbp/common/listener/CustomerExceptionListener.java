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
    private Date time;
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
    // 重复数据
    private Set<CustomerRepeatBo> repeatCustomerSet = new HashSet<>(10);
    // 正确数据
    private Set<CustomerRepeatBo> rightCustomerSet = new HashSet<>(10);

    // 新增数据
    private List<Customer> insertDataList = new ArrayList<>();
    private Map<String, Object> dataMap;

    public CustomerExceptionListener() {
    }

    public CustomerExceptionListener(String operName, Date time, ICustomerService customerService, CustomerMapper customerMapper,
            List<String> schemeCodeList, List<SysDictData> dictDataList, List<CustomerDto> wrongDataList,
            Map<String, Object> dataMap) {
        this.operName = operName;
        this.time = time;
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
                    .get(excelDataConvertException.getColumnIndex()) + "列解析异常，数据为:" + excelDataConvertException
                    .getCellData());
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
                .checkExcelData(customer, schemeCodeList, dictDataList, wrongDataList, rightCustomerSet,
                        repeatCustomerSet);
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
        if (insertDataList.size() > 0) {
            insertCount += customerMapper.batchInsertCustomer(insertDataList);
        }

        // 重复代表着两条以上的相同（证件号和到检日期）数据，由于采用的是分批插入数据库，所以会造成：假定一定存在两条重复数据，第一条数据已经插入到数据库中，第二条数据还没有解析到。
        // 这里的做法是：重复数据的第一条数据向插入到数据库中，全部解析完成后，根据第二条数据的信息查出第一条数据，将其添加到错误数据list并删除数据
        for (CustomerRepeatBo repeatCustomer : repeatCustomerSet) {
            // 找出重复数据
            CustomerDto customerDto = customerMapper
                    .selectRepeatCustomer(repeatCustomer.getIdNumber(), repeatCustomer.getExaminatidonDate(), operName,
                            time);
            customerDto.setFailureCause("重复数据；");
            wrongDataList.add(customerDto);
            insertCount -= customerMapper.deleteCustomerById(customerDto.getId());
        }

        dataMap.put("insertCount", insertCount);
    }
}