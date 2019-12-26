package com.mnbp.project.business.controller;

import java.util.List;
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
import com.mnbp.project.business.domain.InsuranceScheme;
import com.mnbp.project.business.service.IInsuranceSchemeService;
import com.mnbp.framework.web.controller.BaseController;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.web.page.TableDataInfo;

/**
 * 方案Controller
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/business/insuranceScheme")
public class InsuranceSchemeController extends BaseController
{
    @Autowired
    private IInsuranceSchemeService insuranceSchemeService;

    /**
     * 查询方案列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:list')")
    @GetMapping("/list")
    public TableDataInfo list(InsuranceScheme insuranceScheme)
    {
        startPage();
        List<InsuranceScheme> list = insuranceSchemeService.selectInsuranceSchemeList(insuranceScheme);
        return getDataTable(list);
    }

    /**
     * 导出方案列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:export')")
    @Log(title = "方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(InsuranceScheme insuranceScheme)
    {
        List<InsuranceScheme> list = insuranceSchemeService.selectInsuranceSchemeList(insuranceScheme);
        ExcelUtil<InsuranceScheme> util = new ExcelUtil<InsuranceScheme>(InsuranceScheme.class);
        return util.exportExcel(list, "insuranceScheme");
    }

    /**
     * 获取方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(insuranceSchemeService.selectInsuranceSchemeById(id));
    }

    /**
     * 新增方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:add')")
    @Log(title = "方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InsuranceScheme insuranceScheme)
    {
        return toAjax(insuranceSchemeService.insertInsuranceScheme(insuranceScheme));
    }

    /**
     * 修改方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:edit')")
    @Log(title = "方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InsuranceScheme insuranceScheme)
    {
        return toAjax(insuranceSchemeService.updateInsuranceScheme(insuranceScheme));
    }

    /**
     * 删除方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:remove')")
    @Log(title = "方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(insuranceSchemeService.deleteInsuranceSchemeByIds(ids));
    }
}
