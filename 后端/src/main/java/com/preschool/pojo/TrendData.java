package com.preschool.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendData {
    //    {category:['1月','2月','3月','4月','5月','6月','7月'],
//        data1:{key:'data1',title:'博文增加量趋势',data:[210,200,120,130,140,160,200]},
//        data2:{key:'data2',title:'评论增加量趋势',data:[300,320,360,300,280,390,330]},
//        data3:{key:'data3',title:'回复增加量趋势',data:[400,500,550,590,600,100,90]}}
    List<String> category;
    Trend data1;
    Trend data2;
    Trend data3;
}
