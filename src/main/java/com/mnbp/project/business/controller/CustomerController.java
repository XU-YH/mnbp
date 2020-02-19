package com.mnbp.project.business.controller;

import com.mnbp.common.enums.DelFlagEnum;
import com.mnbp.common.utils.ServletUtils;
import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.aspectj.lang.annotation.Log;
import com.mnbp.framework.aspectj.lang.enums.BusinessType;
import com.mnbp.framework.security.LoginUser;
import com.mnbp.framework.security.service.TokenService;
import com.mnbp.framework.web.controller.BaseController;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.framework.web.page.TableDataInfo;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import com.mnbp.project.business.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 客户Controller
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/business/customer")
public class CustomerController extends BaseController {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('business:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer) {
        startPage();
        customer.setDelFlag(DelFlagEnum.OK.getCode());
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 权益查询，查询客户承保信息
     *
     * @param insuranceInfoBo
     *            客户承保信息BO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('business:customer:insuranceInfo:list')")
    @GetMapping("/insuranceInfo/list")
    public TableDataInfo insuranceInfoList(InsuranceInfoBo insuranceInfoBo) {
        startPage();
        List<InsuranceInfoVo> list = customerService.selectCustomerInsuranceInfoList(insuranceInfoBo);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @PreAuthorize("@ss.hasPermi('business:customer:export')")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Customer customer) {
        customer.setDelFlag(DelFlagEnum.OK.getCode());
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "人员数据");
    }

    /**
     * 获取客户详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(customerService.selectCustomerById(id));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('business:customer:add')")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Customer customer) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        return customerService.insertCustomer(customer, loginUser.getUsername());
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('business:customer:edit')")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Customer customer) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        return customerService.updateCustomer(customer, loginUser.getUsername());
    }

    /**
     * 删除客户，逻辑删除，修改del_flag的值为2
     */
    @PreAuthorize("@ss.hasPermi('business:customer:remove')")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        return toAjax(customerService.updateByIdsForDel(ids, loginUser.getUsername()));
    }

    /**
     * 下载客户导入模板
     *
     * @return
     */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        String fileName = "人员导入模板.xlsx";
        return AjaxResult.success(fileName);
    }

    /**
     * 导入客户数据功能重写
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:customer:import')")
    @PostMapping("/importData")
    public AjaxResult importData1(MultipartFile file, boolean updateSupport) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();

        AjaxResult ajaxResult;

        try {
            ajaxResult = customerService.importUser(file, operName);
        } catch (IOException e) {
            LOGGER.error("人员导入失败，【CustomerController.importData()】，msg：", e);
            return AjaxResult.error("数据导入失败");
        }
        return ajaxResult;
    }
}
