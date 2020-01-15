package com.mnbp.project.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnbp.project.business.mapper.CustomerMapper;
import com.mnbp.project.weixin.domain.bo.WeixinInsuranceInfoBo;
import com.mnbp.project.weixin.domain.vo.WeixinInsuranceInfoVo;
import com.mnbp.project.weixin.service.IWeixinService;

import java.util.List;

/**
 * 微信相关 Service实现
 *
 * @author: yinhui xu
 * @date: 2019/12/30 15:59
 */
@Service
public class WeixinServiceImpl implements IWeixinService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 微信查询承保信息
     *
     * @param weixinInsuranceInfoBo
     *            承保信息查询参数
     * @return
     */
    @Override
    public WeixinInsuranceInfoVo getInsuranceInfo(WeixinInsuranceInfoBo weixinInsuranceInfoBo) {
        List<WeixinInsuranceInfoVo> list = customerMapper.getInsuranceInfo(weixinInsuranceInfoBo);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }
}
