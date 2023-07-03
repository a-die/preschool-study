package com.preschool.service;

import com.preschool.pojo.*;

import java.util.List;

public interface DataVisualizationService {
    List<Hot> getHotData();
    List<Rank> getRankData();
    List<Horizon> getHorizonData();
    TrendData getTrendData();
    List<Children> getMapData();
}
