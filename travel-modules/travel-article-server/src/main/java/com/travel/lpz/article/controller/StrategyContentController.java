package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.StrategyContent;
import com.travel.lpz.article.service.StrategyContentService;
import com.travel.lpz.article.service.StrategyContentService;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/strategies/themes")
public class StrategyContentController {
    private final StrategyContentService StrategyContentService;
    public StrategyContentController(StrategyContentService StrategyContentService) {
        this.StrategyContentService = StrategyContentService;
    }

    @GetMapping("/query")
    public R<Page<StrategyContent>> pageList(Page<StrategyContent> page) {
        return R.success(StrategyContentService.page(page));
    }

    @GetMapping("/detail")
    public R<StrategyContent> getById(Long id) {
        return R.success(StrategyContentService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(StrategyContent dest) {
        StrategyContentService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(StrategyContent dest) {
        StrategyContentService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        StrategyContentService.removeById(id);
        return R.success();
    }


}
