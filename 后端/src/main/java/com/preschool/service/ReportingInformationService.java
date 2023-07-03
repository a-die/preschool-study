package com.preschool.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.Comment;
import com.preschool.pojo.ReportingInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.ReportingInformationDiscussPost;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-11-27
 */
public interface ReportingInformationService extends IService<ReportingInformation> {
    /*
          获得所有的举报信息，多表查询，和用户表和博文表连表查询
        * @param null
        * @return
    */

    List<Map<String,Object>> getAllReportingInformation(Page<ReportingInformation> page);

    int getReportingNum();

    List<Map<String, Object>>getReportingInformationByReportReason(String reportReason);

    int updateProcessStatus(int id,int processStatus);

    int getReportingNumByProcessStatus(int processStatus);

    List<Map<String,Object>>getAllReportingInformationByProcessStatus(Page<ReportingInformation> page,int processStatus);

    List<ReportingInformationDiscussPost>getAllReportingInformationByUserId(int userId);

    List<ReportingInformation> getAll(int user_id);

    int clearAll(int user_id);

    boolean updateReaded(int id);
}
