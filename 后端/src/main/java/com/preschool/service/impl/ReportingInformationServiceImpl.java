package com.preschool.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.dao.DiscussPostMapper;
import com.preschool.pojo.Comment;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.ReportingInformation;
import com.preschool.dao.ReportingInformationMapper;
import com.preschool.pojo.ReportingInformationDiscussPost;
import com.preschool.service.ReportingInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-11-27
 */
@Service
public class ReportingInformationServiceImpl extends ServiceImpl<ReportingInformationMapper, ReportingInformation> implements ReportingInformationService {

    @Autowired
    ReportingInformationMapper reportingMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;

    @Override
    public List<Map<String, Object>> getAllReportingInformation(Page<ReportingInformation> page) {
        List<Map<String, Object>> allReportingInformation = reportingMapper.getAllReportingInformation(page);
        return allReportingInformation;
    }

    @Override
    public int getReportingNum() {
        return reportingMapper.getReportingNum();
    }

    @Override
    public List<Map<String, Object>> getReportingInformationByReportReason(String reportReason) {
        List<Map<String, Object>> reportingInformationByReportReasonReason = reportingMapper.getReportingInformationByReportReason(reportReason);
        return reportingInformationByReportReasonReason;
    }

    @Override
    public int updateProcessStatus(int id,int processStatus) {
        ReportingInformation reportingInformation = new ReportingInformation();
        reportingInformation.setId(id);
        reportingInformation.setProcessStatus(processStatus);
        int i = reportingMapper.updateById(reportingInformation);
        return i;
    }

    @Override
    public int getReportingNumByProcessStatus(int processStatus) {
        int reportingNumByProcessStatus = reportingMapper.getReportingNumByProcessStatus(processStatus);
        return reportingNumByProcessStatus;
    }

    @Override
    public List<Map<String, Object>> getAllReportingInformationByProcessStatus(Page<ReportingInformation> page, int processStatus) {
        List<Map<String, Object>> allReportingInformationByProcessStatus = reportingMapper.getAllReportingInformationByProcessStatus(page, processStatus);
        return allReportingInformationByProcessStatus;
    }

    @Override
    public List<ReportingInformationDiscussPost> getAllReportingInformationByUserId(int userId) {
        return reportingMapper.getAllReportingInformationByUserId(userId);
    }

    @Override
    public List<ReportingInformation> getAll(int user_id) {
        return reportingMapper.getAll(user_id);
    }

    @Override
    public int clearAll(int user_id) {
        List<DiscussPost> discussPosts = discussPostMapper.queryByUserId(user_id, 0);
        for(DiscussPost discussPost:discussPosts){
            reportingMapper.clearSomePosts(discussPost.getId());
        }
        return 1;
    }

    @Override
    public boolean updateReaded(int id) {
        ReportingInformation reportingInformation = reportingMapper.selectById(id);
        reportingInformation.setReaded(1);
        return reportingMapper.updateById(reportingInformation)>=1;
    }


}
