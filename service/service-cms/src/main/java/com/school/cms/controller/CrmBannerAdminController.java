package com.school.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.cms.entity.CrmBanner;
import com.school.cms.service.CrmBannerService;
import com.school.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-31
 */
@Api(tags = "后台banner管理")
@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {

    @Resource
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> crmBannerPage = new Page<>(page,limit);
        crmBannerService.page(crmBannerPage, null);
        return R.ok().data("items",crmBannerPage.getRecords()).data("total",crmBannerPage.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation("删除Banner")
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation("修改Banner")
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @ApiOperation("根据id获取banner")
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item",banner);

    }
}

