package com.mnbp.project.weixin.service;

import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.domain.vo.WeixinInsuranceInfoVo;

/**
 * 微信相关 Service接口
 *
 * @author: yinhui xu
 * @date: 2019/12/30 16:00
 */
public interface IWeixinService {

    /**
     * 微信查询承保信息
     *
     * @param weixinInsuranceInfoBo
     *            承保信息查询参数
     * @return
     */
    WeixinInsuranceInfoVo getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo);
}
