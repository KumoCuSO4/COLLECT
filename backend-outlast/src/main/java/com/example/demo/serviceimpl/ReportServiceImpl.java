package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.UserPO;
import com.example.demo.service.NLPService;
import com.example.demo.service.ReportService;
import com.example.demo.util.Constant;
import com.example.demo.util.FileHelper;
import com.example.demo.util.PageInfoUtil;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.SupplementVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    UserMapper userMapper;
    @Resource
    TaskMapper taskMapper;
    @Resource
    ReportMapper reportMapper;
    @Resource
    TaskOrderMapper taskOrderMapper;

    @Value("${web.file-upload-path}")
    private String path;

    @Override
    public ResultVO<ReportVO> submitReport(ReportVO report){
        if(report.getDescription() == null || report.getDescription().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告情况描述不能为空");
        }
        if(report.getStep() == null || report.getStep().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告缺陷复先步骤不能为空");
        }
        if(report.getEquipment() == null || report.getEquipment().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告测试设备信息不能为空");
        }
        report.setId(0);
        report.setPost_time(new Date());//获取当前时间
        report.setScore(0.0);
        ReportPO reportPO = new ReportPO(report);
        if(reportMapper.insert(reportPO) > 0){
            //更新任务完成状态
            taskOrderMapper.updateStatus(reportPO.getTask_id(),Constant.TASK_FINISHED);
            //增加用户活跃度
            userMapper.increaseActivity(report.getUser_id(),Constant.REPORT_INCREASE);
            if(report.getPics()!=null && report.getPics().length!=0) {
                System.out.println(report.getPics().length);
                for(int i=0;i<report.getPics().length;i++) {
                    MultipartFile file = report.getPics()[i];
                    String targetPath = path + "pics/report/" + reportPO.getId() + "/";
                    FileHelper.save(targetPath,FileHelper.PicType,file);
                }
            }
            ReportVO reportVO = new ReportVO(reportPO);
            String[] fileList = FileHelper.getFileList(path + "pics/report/" + reportPO.getId() + "/");
            reportVO.setPicsList(fileList);
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "报告提交成功", reportVO);
        }else {
            return new ResultVO<>(Constant.REQUEST_FAIL, "发生未知错误");
        }
    }

    @Override
    public void getReportPic(Integer reportId, String name, HttpServletResponse response) {
        FileHelper.downloadFile(path + "pics/report/" + reportId + "/",name,name,response);
    }

    @Override
    public PageInfo<ReportVO> getReportByReportTime(Integer taskId, Integer pageSize, Integer currPage){
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<ReportPO> pageInfo = new PageInfo<>(reportMapper.selectByTaskId(taskId));
        PageInfo<ReportVO> result = PageInfoUtil.convert(pageInfo, ReportVO.class);
        for(int i=0;i<result.getSize();i++){
            ReportVO reportVO = result.getList().get(i);
            UserPO userPO = userMapper.selectByUid(reportVO.getUser_id());
            reportVO.setUser_name(userPO.getUname());
        }
        return result;
    }

    @Override
    public ReportVO getReportByReportId(Integer reportId){
        ReportVO reportVO = new ReportVO(reportMapper.selectByPrimaryKey(reportId));
        String[] fileList = FileHelper.getFileList(path + "pics/report/" + reportId + "/");
        reportVO.setPicsList(fileList);
        UserPO userPO = userMapper.selectByUid(reportVO.getUser_id());
        reportVO.setUser_name(userPO.getUname());
        return reportVO;
    }

    @Resource
    private NLPService nlpService;

    @Override
    public ReportVO getSimilarByReportId(Integer reportId) {
        ReportPO report = reportMapper.selectByPrimaryKey(reportId);
        int taskId = report.getTask_id();
        String des = report.getDescription();
        List<ReportPO> taskReports = reportMapper.selectByTaskId(taskId);
        double maxSimilarity = 0;
        ReportPO similarReport = null;
        for(ReportPO taskReport : taskReports) {
            if(taskReport.getId()==reportId) continue;
            String tDes = taskReport.getDescription();
            double similarity = nlpService.calculateSimilarity(des,tDes);
            if(similarity>maxSimilarity) {
                maxSimilarity=similarity;
                similarReport=taskReport;
            }
        }
        if(similarReport==null) return null;
        ReportVO reportVO = new ReportVO(similarReport);
        String[] fileList = FileHelper.getFileList(path + "pics/" + similarReport.getId() + "/");
        reportVO.setPicsList(fileList);
        return reportVO;
    }

    @Override
    public Double getSimilarity(Integer reportId) {
        ReportPO report = reportMapper.selectByPrimaryKey(reportId);
        int taskId = report.getTask_id();
        String des = report.getDescription();
        List<ReportPO> taskReports = reportMapper.selectByTaskId(taskId);
        double maxSimilarity = 0;
        for(ReportPO taskReport : taskReports) {
            if(taskReport.getId()==reportId) continue;
            String tDes = taskReport.getDescription();
            double similarity = nlpService.calculateSimilarity(des,tDes);
            if(similarity>maxSimilarity) {
                maxSimilarity=similarity;
            }
        }
        if(maxSimilarity==0) return 0.0;
        return maxSimilarity;
    }
}