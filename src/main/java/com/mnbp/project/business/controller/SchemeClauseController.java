package com.mnbp.project.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mnbp.common.utils.poi.ExcelUtil;
import com.mnbp.framework.aspectj.lang.annotation.Log;
import com.mnbp.framework.aspectj.lang.enums.BusinessType;
import com.mnbp.framework.web.controller.BaseController;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.framework.web.page.TableDataInfo;
import com.mnbp.project.business.domain.SchemeClause;
import com.mnbp.project.business.domain.dto.SchemeClauseBO;
import com.mnbp.project.business.domain.vo.SchemeClauseVO;
import com.mnbp.project.business.service.ISchemeClauseService;

/**
 * 方案条款Controller
 * 
 * @author yinhui xu
 * @date 2019-12-27
 */
@RestController
@RequestMapping("/business/scheme/clause")
public class SchemeClauseController extends BaseController {
    @Autowired
    private ISchemeClauseService schemeClauseService;

    /**
     * 查询方案条款列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchemeClauseBO schemeClauseBO) {
        startPage();
        List<SchemeClauseVO> list = schemeClauseService.selectSchemeClauseList(schemeClauseBO);
        return getDataTable(list);
    }

    /**
     * 导出方案条款列表
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:export')")
    @Log(title = "方案条款", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SchemeClauseBO schemeClauseBO) {
        List<SchemeClauseVO> list = schemeClauseService.selectSchemeClauseList(schemeClauseBO);
        ExcelUtil<SchemeClauseVO> util = new ExcelUtil<SchemeClauseVO>(SchemeClauseVO.class);
        return util.exportExcel(list, "clause");
    }

    /**
     * 获取方案条款详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(schemeClauseService.selectSchemeClauseById(id));
    }

    /**
     * 新增方案条款
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:add')")
    @Log(title = "方案条款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchemeClause schemeClause) {
        return toAjax(schemeClauseService.insertSchemeClause(schemeClause));
    }

    /**
     * 修改方案条款
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:edit')")
    @Log(title = "方案条款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchemeClause schemeClause) {
        return toAjax(schemeClauseService.updateSchemeClause(schemeClause));
    }

    /**
     * 删除方案条款
     */
    @PreAuthorize("@ss.hasPermi('business:insuranceScheme:remove')")
    @Log(title = "方案条款", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(schemeClauseService.deleteSchemeClauseByIds(ids));
    }
}
