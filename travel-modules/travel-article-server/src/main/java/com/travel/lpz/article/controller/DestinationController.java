package com.travel.lpz.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.qo.DestinationQuery;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.auth.anno.RequireLogin;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @title DestinationController
 * @date 2024/10/8 20:11
 * @description TODO
 */

@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public R<Page<Destination>> pageList(DestinationQuery query){
        return R.success(destinationService.pageList(query));
    }

    @GetMapping("/list")
    public R<List<Destination>> listAll() {
        return R.success(destinationService.list());
    }

    @GetMapping("/detail")
    public R<Destination > getById(Long id) {
        return R.success(destinationService.getById(id));
    }
    @PostMapping("/save")
    public R<?> save(Destination dest) {
        destinationService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(Destination dest) {
        destinationService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        destinationService.removeById(id);
        return R.success();
    }
    @GetMapping("/toasts")
    public R<List<Destination>> toasts(Long destId){
        return R.success(destinationService.findToasts(destId));
    }

    @GetMapping("/hotList")
    public R<List<Destination>> hostList(Long rid) {
        return R.success(destinationService.findDestsByRid(rid));
    }
}
