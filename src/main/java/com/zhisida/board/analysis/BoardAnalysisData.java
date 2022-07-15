package com.zhisida.board.analysis;

import lombok.Data;

import java.util.*;

@Data
public class BoardAnalysisData {
    private Date analysisTime;
    private Date dataTime;
    private Map resultData = new HashMap<>();
    private List originData = new ArrayList<>();

    public void addOriginData(List originData){
        this.originData.add(originData);
    }
}
