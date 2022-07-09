package com.zhisida.board.cache;

import org.springframework.stereotype.Component;

@Component
public class BoardAnalysisCache {

    //@Cacheable(cacheNames = "Young:Board:Analysis:ResultData:", key = "#analysisId", unless = " #result == null")
    public Object getResultData(Long analysisId) {
        return null;
    }

    //@CachePut(cacheNames = "Young:Board:Analysis:ResultData:", key = "#analysisId", unless = " #result == null")
    public Object putResultData(Long analysisId, Object resultData) {
        return resultData;
    }

    //@CacheEvict(cacheNames = "Young:Board:Analysis:ResultData:", key = "#analysisId")
    public void clearResultData(Long analysisId) {
    }

}
