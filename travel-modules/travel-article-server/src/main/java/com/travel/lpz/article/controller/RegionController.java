package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.article.service.RegionService;
import com.travel.lpz.auth.anno.RequireLogin;
import com.travel.lpz.core.untils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lpz
 * @title RegionController
 * @date 2024/10/8 20:11
 * @description TODO
 */
@RestController
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;
    private final DestinationService destinationService;

    public RegionController(RegionService regionService, DestinationService destinationService) {
        this.regionService = regionService;
        this.destinationService = destinationService;
    }
    @GetMapping
    public R<Page<Region>> pageList(Page<Region> page){
        return R.success(regionService.page(page));
    }

    @RequireLogin
    @GetMapping("/{id}")
    public R<Region> test(@PathVariable Long id) {
        return R.success(regionService.getById(id));
    }

    @GetMapping("/detail")
    public R<Region> getById(Long id) {
        return R.success(regionService.getById(id));
    }

    @GetMapping("/hotList")
    public R<List<Region>> hostList() {
        return R.success(regionService.findHotList());
    }
    @PostMapping("/save")
    public R<?> save(Region region) {
        regionService.save(region);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(Region region) {
        regionService.save(region);
        return R.success();
    }
    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        regionService.removeById(id);
        return R.success();
    }
/**
根据区域id查询目的地列表。
*/
    @GetMapping("/{id}/destination")
    public R<List<Destination>> getDestinationById(@PathVariable Long id){
        List<Destination>  result = destinationService.getDestinationByRegionId(id);
        return R.success(result);
    }
}
