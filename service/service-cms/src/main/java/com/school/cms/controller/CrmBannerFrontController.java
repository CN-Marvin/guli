package com.school.cms.controller;

import com.school.cms.entity.CrmBanner;
import com.school.cms.service.CrmBannerService;
import com.school.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.cms.controller
 * @author: Marvin-zl
 * @date: 2022/5/31 19:22
 */

@Api(tags = "前台banner管理")
@RestController
@RequestMapping("/cmsservice/bannerfront")
public class CrmBannerFrontController {
    @Resource
    private CrmBannerService crmBannerService;

    @ApiOperation("查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list",list);
    }
}
