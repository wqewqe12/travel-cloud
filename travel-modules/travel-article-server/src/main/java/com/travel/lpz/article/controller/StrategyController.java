package com.travel.lpz.article.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.domain.StrategyContent;
import com.travel.lpz.article.domain.StrategyRank;
import com.travel.lpz.article.qo.StrategyQuery;
import com.travel.lpz.article.service.StrategyRankService;
import com.travel.lpz.article.service.StrategyService;
import com.travel.lpz.article.untils.QiNiuUntils;
import com.travel.lpz.article.vo.StrategyCondition;
import com.travel.lpz.core.untils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/strategies")
public class StrategyController {
    private final StrategyService strategyService;
    private final QiNiuUntils qiNiuUntils;
    private final StrategyRankService strategyRankService;

    public StrategyController(StrategyService strategyService, QiNiuUntils qiNiuUntils, StrategyRankService strategyRankService) {
        this.strategyService = strategyService;
        this.qiNiuUntils = qiNiuUntils;
        this.strategyRankService = strategyRankService;
    }

    @GetMapping("/query")
    public R<Page<Strategy>> pageList(StrategyQuery qo) {
        return R.success(strategyService.pageList(qo));
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

    @GetMapping("/conditions")
    public R<Map<String,List<StrategyCondition>>> getConditions(){
        Map<String, List<StrategyCondition>> map = new HashMap<>();
        List<StrategyCondition> chinaCondition = strategyService.findDestCondition(Strategy.ABROAD_NO);
        map.put("chinaCondition",chinaCondition);
        List<StrategyCondition> abroadCondition = strategyService.findDestCondition(Strategy.ABROAD_YES);
        map.put("abroadCondition",abroadCondition);
        List<StrategyCondition> themeCondition = strategyService.findThemeCondition();
        map.put("themeCondition",themeCondition);
        return R.success(map);
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

    @GetMapping("/ranks")
    public R<JSONObject> findRanks() {
        List<StrategyRank> abroadRank = strategyRankService.selectRankByType(StrategyRank.TYPE_ABROAD);
        List<StrategyRank> chinaRank = strategyRankService.selectRankByType(StrategyRank.TYPE_CHINA);
        List<StrategyRank> hotRank = strategyRankService.selectRankByType(StrategyRank.TYPE_HOT);
        JSONObject result = new JSONObject();
        result.put("abroadRank",abroadRank);
        result.put("chinaRank",chinaRank);
        result.put("hotRank",hotRank);
        return R.success(result);
    }

}
