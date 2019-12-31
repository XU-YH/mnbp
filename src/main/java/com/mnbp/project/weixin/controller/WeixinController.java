package com.mnbp.project.weixin.controller;

import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信 Controller
 *
 * @author: yinhui xu
 * @date: 2019/12/30 15:55
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    @Autowired
    private IWeixinService weixinService;

    /**
     * 微信查询承保信息
     *
     * @param weixinInsuranceInfoBo
     *            承保信息查询参数
     * @return
     */
    @GetMapping("/getInsuranceInfo")
    public AjaxResult getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo) {
        return AjaxResult.success(weixinService.getInsuranceInfo(weixinInsuranceInfoBo));
    }
}
