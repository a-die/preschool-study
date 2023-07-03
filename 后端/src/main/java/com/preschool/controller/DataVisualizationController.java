package com.preschool.controller;

import com.preschool.pojo.*;
import com.preschool.service.DataVisualizationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DataVisualizationController {
    @Autowired
    DataVisualizationService dataVisualizationService;

    /**
        * 获得圆环图的数据
        * @param
        * @return java.util.List<com.preschool.pojo.Hot>
    */
    @ApiOperation("获得圆环图的数据")
    @GetMapping("/getHotData")
   public List<Hot> getHotData(){
        return dataVisualizationService.getHotData();
    }

    /**
     * 获得排序统计图的数据
     * @param
     * @return java.util.List<com.preschool.pojo.Hot>
     */
    @ApiOperation("获得排序统计图的数据")
    @GetMapping("/getRankData")
    //[{subject:'',count:},{subject:'',count:}]
   public List<Rank> getRankData(){
        return dataVisualizationService.getRankData();
    }

   /**
       * 获得用户发表博文统计图的数据
       * @param
       * @return java.util.List<com.preschool.pojo.Rank>
   */
    @ApiOperation("获得用户发表博文统计图的数据")
    @GetMapping("/getHorizonData")
    //[{subject:'',count:},{subject:'',count:}]
    public List<Horizon> getHorizonData(){
        return dataVisualizationService.getHorizonData();
    }

   /**
       * 获得历史7个月博文，评论，回复增加量趋势图的数据
       * @param
       * @return com.preschool.pojo.TrendData
   */
    @ApiOperation("获得历史7个月博文，评论，回复增加量趋势图的数据")
    @GetMapping("/getTrendData")
    public TrendData getTrendData(){
        return dataVisualizationService.getTrendData();
    }

    @ApiOperation("获得历史7个月博文，评论，回复增加量趋势图的数据")
    @GetMapping("/getMapData")
    public List<Children> getMapData(){
        return dataVisualizationService.getMapData();
    }
}
