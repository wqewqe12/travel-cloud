package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.service.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bxc
 * @title RegionController
 * @date 2024/10/8 20:11
 * @description TODO
 */
@RestController
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }
    @GetMapping
    public R<Page<Region>> pageList(Page<Region> page){
        Page<Region> resultPage = regionService.page(page);
        return R.ok(resultPage);
    }

    @RequestMapping("/list")
    public String list() {
        return regionService.getAll();
    }
}
