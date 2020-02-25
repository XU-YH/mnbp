package com.mnbp.project.weixin.controller;

import com.mnbp.common.constant.HttpStatus;
import com.mnbp.common.utils.ServletUtils;
import com.mnbp.common.utils.ip.IpUtils;
import com.mnbp.framework.web.domain.AjaxResult;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.service.IWeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * 日志打印
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);

    /**
     * 域名映射地址：30.4.32.76
     */
    private static final String IP = "30.4.32.76";

    @Autowired
    private IWeixinService weixinService;

    /**
     * 微信查询承保信息
     *
     * @param weixinInsuranceInfoBo 承保信息查询参数
     * @return
     */
    @GetMapping("/getInsuranceInfo")
    public AjaxResult getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo) {
        LOGGER.info("微信承保信息查询，姓名：{}，证件号：{}", weixinInsuranceInfoBo.getCustomerName(),
                weixinInsuranceInfoBo.getIdNumber());
        // 请求的地址，访问者IP既不是本地IP又不是30.4.32.76，不让其访问微信接口
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        if (!IpUtils.internalIp(ip) && !IP.equals(ip)) {
            LOGGER.info("{}该IP企图访问微信承保信息查询接口");
            return AjaxResult.error(HttpStatus.NOT_FOUND, "无法访问");
        }
        return AjaxResult.success(weixinService.getInsuranceInfo(weixinInsuranceInfoBo));
    }
}
