package com.preschool.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trend {
   // data1:{key:'data1',title:'博文增加量趋势',data:[210,200,120,130,140,160,200]},
    String key;
    String title;
    List<Integer> data;
}
