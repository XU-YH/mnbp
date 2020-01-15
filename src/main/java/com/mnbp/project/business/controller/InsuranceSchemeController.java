package com.mnbp.project.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mnbp.common.enums.DelFlagEnum;
import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.aspectj.lang.annotation.Log;
import com.mnbp.framework.aspectj.lang.enums.BusinessType;
import com.mnbp.framework.web.controller.BaseController;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.framework.web.page.TableDataInfo;
import com.mnbp.project.business.domain.InsuranceScheme;
import com.mnbp.project.business.service.IInsuranceSchemeService;

/**
 * 方案Controller
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/business/insuranceScheme")
public class InsuranceSchemeController extends BaseController {
    @Autowired
    private IInsuranceSchemeService insuranceSchemeService;

    /**
     * 查询方案列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:list')")
    @GetMapping("/list")
    public TableDataInfo list(InsuranceScheme insuranceScheme) {
        startPage();
        insuranceScheme.setDelFlag(DelFlagEnum.OK.getCode());
        List<InsuranceScheme> list = insuranceSchemeService.selectInsuranceSchemeList(insuranceScheme);
        return getDataTable(list);
    }

    /**
     * 导出方案列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:export')")
    @Log(title = "方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(InsuranceScheme insuranceScheme) {
        insuranceScheme.setDelFlag(DelFlagEnum.OK.getCode());
        List<InsuranceScheme> list = insuranceSchemeService.selectInsuranceSchemeList(insuranceScheme);
        ExcelUtil<InsuranceScheme> util = new ExcelUtil<InsuranceScheme>(InsuranceScheme.class);
        return util.exportExcel(list, "insuranceScheme");
    }

    /**
     * 获取方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(insuranceSchemeService.selectInsuranceSchemeById(id));
    }

    /**
     * 新增方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:add')")
    @Log(title = "方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody InsuranceScheme insuranceScheme) {
        if (insuranceSchemeService.selectBySchemeCode(insuranceScheme) != null) {
            return AjaxResult.error("已有该方案代码【" + insuranceScheme.getSchemeCode() + "】");
        }

        return toAjax(insuranceSchemeService.insertInsuranceScheme(insuranceScheme));
    }

    /**
     * 修改方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:edit')")
    @Log(title = "方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody InsuranceScheme insuranceScheme) {
        if (insuranceSchemeService.selectBySchemeCode(insuranceScheme) != null) {
            return AjaxResult.error("已有该方案代码【" + insuranceScheme.getSchemeCode() + "】");
        }

        return toAjax(insuranceSchemeService.updateInsuranceScheme(insuranceScheme));
    }

    /**
     * 删除方案
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:remove')")
    @Log(title = "方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(insuranceSchemeService.updateByIdsForDel(ids));
    }

    /**
     * 校验方案代码唯一性，根据方案代码查询方案
     *
     * @param insuranceScheme
     * @return
     */
    @GetMapping("/selectBySchemeCode")
    public AjaxResult selectBySchemeCode(InsuranceScheme insuranceScheme) {
        InsuranceScheme scheme = insuranceSchemeService.selectBySchemeCode(insuranceScheme);

        return AjaxResult.success(scheme);
    }
}
