package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.StrategyTheme;
import com.travel.lpz.article.service.StrategyThemeService;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lpz
 * @title StrategyCatalogController
 * @date 2024/10/16 15:59
 * @description TODO
 */
@RestController
@RequestMapping("/strategies/themes")
public class StrategyThemeController {
    private final StrategyThemeService strategyThemeService;

    public StrategyThemeController(StrategyThemeService strategyThemeService) {
        this.strategyThemeService = strategyThemeService;
    }

    @GetMapping("/query")
    public R<Page<StrategyTheme>> pageList(Page<StrategyTheme> page) {
        return R.success(strategyThemeService.page(page));
    }

    @GetMapping("/detail")
    public R<StrategyTheme> getById(Long id) {
        return R.success(strategyThemeService.getById(id));
    }

    @GetMapping("/list")
    public R<List<StrategyTheme>> listAll() {
        return R.success(strategyThemeService.list());
    }
    @PostMapping("/save")
    public R<?> save(StrategyTheme dest) {
        strategyThemeService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(StrategyTheme dest) {
        strategyThemeService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        strategyThemeService.removeById(id);
        return R.success();
    }


}
