package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.service.StrategyService;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/strategies")
public class StrategyController {
    private final StrategyService StrategyService;

    public StrategyController(StrategyService StrategyService) {
        this.StrategyService = StrategyService;
    }

    @GetMapping("/query")
    public R<Page<Strategy>> pageList(Page<Strategy> page) {
        return R.success(StrategyService.page(page));
    }

    @GetMapping("/detail")
    public R<Strategy> getById(Long id) {
        return R.success(StrategyService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(Strategy dest) {
        StrategyService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(Strategy dest) {
        StrategyService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        StrategyService.removeById(id);
        return R.success();
    }


}
