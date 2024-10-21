package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.article.service.StrategyCatalogService;
import com.travel.lpz.article.vo.StrategyCatalogGroup;
import com.travel.lpz.core.untils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/strategies/catalogs")
public class StrategyCatalogController {
    private final StrategyCatalogService strategyCatalogService;
    private final DestinationService destinationService;

    public StrategyCatalogController(StrategyCatalogService strategyCatalogService, DestinationService destinationService) {
        this.strategyCatalogService = strategyCatalogService;
        this.destinationService = destinationService;
    }

    @GetMapping("/query")
    public R<Page<StrategyCatalog>> pageList(Page<StrategyCatalog> page) {
        return R.success(strategyCatalogService.page(page));
    }
    @GetMapping("/groups")
    public R<List<StrategyCatalogGroup>> groupList() {
        return R.success(strategyCatalogService.findGroupList());
    }

    @GetMapping("/detail")
    public R<StrategyCatalog> getById(Long id) {
        return R.success(strategyCatalogService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(StrategyCatalog dest) {
        strategyCatalogService.save(dest);
        return R.success();
    }

    @PostMapping("/update")
    public R<?> updateById(StrategyCatalog dest) {
        strategyCatalogService.updateById(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        strategyCatalogService.removeById(id);
        return R.success();
    }


}
