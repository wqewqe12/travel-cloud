package com.travel.lpz.article.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.domain.StrategyContent;
import com.travel.lpz.article.service.StrategyService;
import com.travel.lpz.article.untils.QiNiuUntils;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/strategies")
public class StrategyController {
    private final StrategyService strategyService;
    private final QiNiuUntils qiNiuUntils;

    public StrategyController(StrategyService strategyService, QiNiuUntils qiNiuUntils) {
        this.strategyService = strategyService;
        this.qiNiuUntils = qiNiuUntils;
    }

    @GetMapping("/query")
    public R<Page<Strategy>> pageList(Page<Strategy> page) {
        return R.success(strategyService.page(page));
    }

    @GetMapping("/groups")
    public R<List<StrategyCatalog>> groupByCatalog(Long destId){
        return R.success(strategyService.findGroupsByDestId(destId));
    }

    @GetMapping("/content")
    public R<StrategyContent> getContentById(Long id){
        return R.success(strategyService.getContentById(id));
    }
    @GetMapping("/viewnumTop3")
    public R<List<Strategy>> viewnumTop3(Long destId){
        return R.success(strategyService.findViewnumByDestId(destId));
    }

    @GetMapping("/detail")
    public R<Strategy> getById(Long id) {
        return R.success(strategyService.getById(id));
    }

    @PostMapping("/uploadImg")
    public JSONObject  uploadImg(MultipartFile file) throws IOException {
        JSONObject result = new JSONObject();
        if (file == null) {
            result.put("uploaded", 0);
            JSONObject error = new JSONObject();
            error.put("message", "请选择要上传的文件!");
            result.put("error", error);
            return result;
        }
        String url = qiNiuUntils.upload(file);
        // 返回七牛云可访问的 url 地址
        result.put("uploaded", 1);
        result.put("fileName", url);
        result.put("url", url);
        return result;
    }


    @PostMapping("/save")
    public R<?> save(Strategy dest) {
        strategyService.save(dest);
        return R.success();
    }
    @PostMapping("/update")
    public R<?> updateById(Strategy dest) {
        strategyService.save(dest);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> deleteById(@PathVariable Long id) {
        strategyService.removeById(id);
        return R.success();
    }


}
