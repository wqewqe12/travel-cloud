package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.article.service.StrategyCatalogService;
import com.travel.lpz.core.untils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/strategies/themes")
public class StrategyCatalogController {
    private final StrategyCatalogService StrategyCatalogService;
    private final DestinationService destinationService;

    public StrategyCatalogController(StrategyCatalogService StrategyCatalogService, DestinationService destinationService) {
        this.StrategyCatalogService = StrategyCatalogService;
        this.destinationService = destinationService;
    }

    @GetMapping("/query")
    public R<Page<StrategyCatalog>> pageList(Page<StrategyCatalog> page) {
        return R.success(StrategyCatalogService.page(page));
    }

    @GetMapping("/detail")
    public R<StrategyCatalog> getById(Long id) {
        return R.success(StrategyCatalogService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(StrategyCatalog dest) {
        StrategyCatalogService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(StrategyCatalog dest) {
        String name = destinationService.getById(dest.getDestId()).getName();
        dest.setDestName(name);
        StrategyCatalogService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        StrategyCatalogService.removeById(id);
        return R.success();
    }


}
