package com.mnbp.project.business.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 导入客户数据
     *
     * @param file
     *            excel表
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:customer:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) {
        ExcelUtil<Customer> util = new ExcelUtil<>(Customer.class);
        List<Customer> customerList = null;
        try {
            customerList = util.importExcel(file.getInputStream());
        } catch (Exception e) {
            LOGGER.error("人员导入失败，【CustomerController.importData()】，msg：", e);
            return AjaxResult.error("数据导入失败，请检查EXCEL是否规范或是找系统后台解决。");
        }

        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();

        return customerService.importUser(customerList, operName);
    }
}
