package com.school.cms.service;

import com.school.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-31
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查找所有Banner
     * @return
     */
    List<CrmBanner> getAllBanner();

}
