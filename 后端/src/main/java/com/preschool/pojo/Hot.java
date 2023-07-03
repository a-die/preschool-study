package com.preschool.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hot {
//{name:'基础知识',children:[{name:'离散数学',value:50},{name:'高等数学',value:90},{name:'线性代数',value:100},{name:'概率论',value:50}]}
    public String name;
    public List<Children> children;
}