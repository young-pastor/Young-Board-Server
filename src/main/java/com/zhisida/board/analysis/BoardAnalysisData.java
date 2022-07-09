package com.zhisida.board.analysis;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Data
public class BoardAnalysisData {
    private Date analysisTime;
    private Date dataTime;
    private Map resultData = new HashMap<>();
}
