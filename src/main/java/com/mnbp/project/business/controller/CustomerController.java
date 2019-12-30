package com.mnbp.project.business.controller;

import java.util.List;

import com.mnbp.project.business.domain.bo.InsuranceInfoBo;
import com.mnbp.project.business.domain.vo.InsuranceInfoVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mnbp.framework.aspectj.lang.annotation.Log;
import com.mnbp.framework.aspectj.lang.enums.BusinessType;
import com.mnbp.project.business.domain.Customer;
import com.mnbp.project.business.service.ICustomerService;
import com.mnbp.framework.web.controller.BaseController;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.web.page.TableDataInfo;

/**
 * 客户Controller
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/business/customer")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('business:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer) {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 权益查询，查询客户承保信息
     *
     * @param insuranceInfoBo 客户承保信息BO
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
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "customer");
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
    public AjaxResult add(@RequestBody Customer customer) {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('business:customer:edit')")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Customer customer) {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('business:customer:remove')")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
