package com.mnbp.project.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnbp.common.enums.DelFlagEnum;
import com.mnbp.common.utils.ServletUtils;
import com.mnbp.framework.security.LoginUser;
import com.mnbp.framework.security.service.TokenService;
import com.mnbp.project.business.domain.InsuranceScheme;
import com.mnbp.project.business.mapper.InsuranceSchemeMapper;
import com.mnbp.project.business.service.IInsuranceSchemeService;

/**
 * 方案Service业务层处理
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
@Service
public class InsuranceSchemeServiceImpl implements IInsuranceSchemeService {
    @Autowired
    private InsuranceSchemeMapper insuranceSchemeMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询方案
     * 
     * @param id
     *            方案ID
     * @return 方案
     */
    @Override
    public InsuranceScheme selectInsuranceSchemeById(Integer id) {
        return insuranceSchemeMapper.selectInsuranceSchemeById(id);
    }

    /**
     * 查询方案列表
     * 
     * @param insuranceScheme
     *            方案
     * @return 方案
     */
    @Override
    public List<InsuranceScheme> selectInsuranceSchemeList(InsuranceScheme insuranceScheme) {
        return insuranceSchemeMapper.selectInsuranceSchemeList(insuranceScheme);
    }

    /**
     * 新增方案
     * 
     * @param insuranceScheme
     *            方案
     * @return 结果
     */
    @Override
    public int insertInsuranceScheme(InsuranceScheme insuranceScheme) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        insuranceScheme.setCreateBy(loginUser.getUsername());
        insuranceScheme.setCreateTime(new Date());

        return insuranceSchemeMapper.insertInsuranceScheme(insuranceScheme);
    }

    /**
     * 修改方案
     * 
     * @param insuranceScheme
     *            方案
     * @return 结果
     */
    @Override
    public int updateInsuranceScheme(InsuranceScheme insuranceScheme) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        insuranceScheme.setUpdateBy(loginUser.getUsername());
        insuranceScheme.setUpdateTime(new Date());

        return insuranceSchemeMapper.updateInsuranceScheme(insuranceScheme);
    }

    /**
     * 批量删除方案
     * 
     * @param ids
     *            需要删除的方案ID
     * @return 结果
     */
    @Override
    public int deleteInsuranceSchemeByIds(Integer[] ids) {
        return insuranceSchemeMapper.deleteInsuranceSchemeByIds(ids);
    }

    /**
     * 批量删除方案，逻辑删除，修改del_flag的值为2
     *
     * @param ids
     *            ids
     * @return
     */
    @Override
    public String updateByIdsForDel(Integer[] ids) {
        // 获取当前登录人信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Date now = new Date();
        int count = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (Integer id : ids) {

            InsuranceScheme scheme = insuranceSchemeMapper.selectInsuranceSchemeById(id);
            // 校验此方案下是否有人员，若有则不删除
            if (insuranceSchemeMapper.selectCustomerBySchemeId(id) > 0) {
                stringBuffer.append(scheme.getSchemeCode() + "，");
                continue;
            }

            InsuranceScheme insuranceScheme = new InsuranceScheme();
            insuranceScheme.setId(id);
            insuranceScheme.setDelFlag(DelFlagEnum.DELETED.getCode());
            insuranceScheme.setUpdateBy(loginUser.getUsername());
            insuranceScheme.setUpdateTime(now);
            count += insuranceSchemeMapper.updateInsuranceScheme(insuranceScheme);
        }

        if (stringBuffer.length() > 0) {
            return "已删除" + count + "条，方案【" + stringBuffer.toString().substring(0, stringBuffer.length() - 1) + "】下有人员，不能删除";
        }

        return "已删除" + count + "条方案";
    }

    /**
     * 根据方案代码查找方案
     *
     * @param insuranceScheme
     * @return
     */
    @Override
    public InsuranceScheme selectBySchemeCode(InsuranceScheme insuranceScheme) {
        return insuranceSchemeMapper.selectBySchemeCode(insuranceScheme);
    }

    /**
     * 删除方案信息
     * 
     * @param id
     *            方案ID
     * @return 结果
     */
    @Override
    public int deleteInsuranceSchemeById(Integer id) {
        return insuranceSchemeMapper.deleteInsuranceSchemeById(id);
    }

    /**
     * 查询方案代码list
     *
     * @return
     */
    @Override
    public List<String> selectSchemeCodeList() {
        return insuranceSchemeMapper.selectSchemeCodeList();
    }
}
