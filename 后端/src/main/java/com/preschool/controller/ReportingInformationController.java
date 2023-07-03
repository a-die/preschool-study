package com.preschool.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.Comment;
import com.preschool.pojo.ReportingInformation;
import com.preschool.pojo.ReportingInformationDiscussPost;
import com.preschool.service.ReportingInformationService;
import com.preschool.utils.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/reporting")
@CrossOrigin
public class ReportingInformationController {

    @Autowired
    ReportingInformationService reportService;

    @PostMapping("/addReport")
    public Result addReport(ReportingInformation reportingInformation){
        if(reportService.saveOrUpdate(reportingInformation)) return Result.ok();
        else return Result.error();
    }

    @PostMapping("/getAllReportingInformation")
    public List<Map<String, Object>> getAllReportingInformation(int page, int size){
        Page<ReportingInformation> now = new Page<>(page,size);
        List<Map<String, Object>> allReportingInformation = reportService.getAllReportingInformation(now);
        return allReportingInformation;
    }

    @GetMapping("/getReportingNum")
    public int getReportingNum(){
        int reportingNum = reportService.getReportingNum();
        return reportingNum;
    }

    @PostMapping("/getReportingInformationByReportReason")
    public List<Map<String, Object>>getReportingInformationByReportReason(String reportReason){
        List<Map<String, Object>> reason = reportService.getReportingInformationByReportReason(reportReason);
        return reason;
    }

    @PostMapping("/updateProcessStatus")
    public Result updateProcessStatus(int id,int processStatus){
        int i = reportService.updateProcessStatus(id, processStatus);
        if(i>=1)return Result.ok("处理状态更新成功");
        else return Result.error("处理状态更新失败");
    }

    @PostMapping("/getReportingNumByProcessStatus")
    public int getReportingNumByProcessStatus(int processStatus){
        int reportingNumByProcessStatus = reportService.getReportingNumByProcessStatus(processStatus);
        return reportingNumByProcessStatus;
    }

    @PostMapping("/getAllReportingInformationByProcessStatus")
    public List<Map<String,Object>> getAllReportingInformationByProcessStatus(int page,int size,int processStatus){
        Page<ReportingInformation> now = new Page<>(page,size);
        List<Map<String, Object>> allReportingInformationByProcessStatus = reportService.getAllReportingInformationByProcessStatus(now, processStatus);
        return allReportingInformationByProcessStatus;
    }

    @PostMapping("/getAllReportingInformationByUserId")
    public Result getAllReportingInformationByUserId(int userId){
        return Result.ok(reportService.getAllReportingInformationByUserId(userId));
    }

    @PostMapping("/getAll")
    public Result getAll(int user_id){
        return Result.ok(reportService.getAll(user_id));
    }

    @PostMapping("/clearAll")
    public Result clearAll(int user_id){
        if(reportService.clearAll(user_id)>=1)
            return Result.ok("删除通知成功");
        else
            return Result.error("删除通知失败");
    }

    @PostMapping("/updateReaded")
    public Result updateReaded(int id){
        if(reportService.updateReaded(id))return Result.ok();
        return Result.error();
    }



}

