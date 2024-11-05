package com.travel.lpz.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.Travel;
import com.travel.lpz.article.qo.StrategyQuery;
import com.travel.lpz.article.qo.TravelQuery;
import com.travel.lpz.article.service.TravelService;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travels")
public class TravelController {
    private final TravelService travelService;


    public TravelController( TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/query")
    public R<Page<Travel>> pageList(TravelQuery query) {

        return R.success(travelService.pageList(query));
    }



    @GetMapping("/detail")
    public R<Travel> getById(Long id) {
        return R.success(travelService.getById(id));
    }


    @PostMapping("/save")
    public R<?> save(Travel dest) {
        travelService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(Travel dest) {
        travelService.updateById(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        travelService.removeById(id);
        return R.success();
    }


}
