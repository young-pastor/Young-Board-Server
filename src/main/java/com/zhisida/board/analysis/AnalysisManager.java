package com.zhisida.board.analysis;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;


public class AnalysisManager {
    public static Analysis getAnalysis(String type) {
        Assert.hasLength(type, "实时分析类型不能为空");
        Map<String, Analysis> beans = SpringUtil.getBeansOfType(Analysis.class);
        Assert.notEmpty(beans, "未找到实时分析类型");
        String curKey = type + "Analysis";
        Optional<String> d = beans.keySet().stream().filter(k -> k.equalsIgnoreCase(curKey)).findFirst();
        if (d.isPresent()) {
            Analysis analysis = beans.get(d.get());
            Assert.notNull(analysis, "未找到实时分析类型");
            return analysis;
        }
        throw new RuntimeException("未找到实时分析类型");
    }
}
