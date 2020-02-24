package com.mnbp.project.weixin.controller;

import com.mnbp.common.utils.ServletUtils;
import com.mnbp.common.utils.ip.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.service.IWeixinService;

/**
 * 微信 Controller
 *
 * @author: yinhui xu
 * @date: 2019/12/30 15:55
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    /**
     * 日志打印
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);

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
        LOGGER.info("微信承保信息查询，姓名：{}，证件号：{}", weixinInsuranceInfoBo.getCustomerName(), weixinInsuranceInfoBo.getIdNumber());
        // 请求的地址
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        LOGGER.info(ip);
        return AjaxResult.success(weixinService.getInsuranceInfo(weixinInsuranceInfoBo));
    }
}
