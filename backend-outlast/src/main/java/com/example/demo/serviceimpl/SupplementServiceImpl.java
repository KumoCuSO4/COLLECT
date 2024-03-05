package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.SupplementMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.SupplementPO;
import com.example.demo.po.UserPO;
import com.example.demo.service.SupplementService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Service
public class SupplementServiceImpl implements SupplementService {
    @Resource
    UserMapper userMapper;
    @Resource
    SupplementMapper supplementMapper;
    @Value("${web.file-upload-path}")
    private String path;

    @Override
    public ResultVO<SupplementVO> createSupplement(SupplementVO supplement) {
        if(supplement.getDescription() == null || supplement.getDescription().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告情况描述不能为空");
        }
        if(supplement.getStep() == null || supplement.getStep().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告缺陷复先步骤不能为空");
        }
        if(supplement.getEquipment() == null || supplement.getEquipment().equals("")){
            return new ResultVO<>(Constant.REQUEST_FAIL,"报告测试设备信息不能为空");
        }
        supplement.setPost_time(new Date());//获取当前时间
        SupplementPO supplementPO = new SupplementPO(supplement);
        if(supplementMapper.insert(supplementPO) > 0){
            //增加用户活跃度
            userMapper.increaseActivity(supplement.getUser_id(),Constant.SUPPLEMENT_INCREASE);
            if(supplement.getPics()!=null && supplement.getPics().length!=0) {
                System.out.println(supplement.getPics().length);
                for(int i=0;i<supplement.getPics().length;i++) {
                    MultipartFile file = supplement.getPics()[i];
                    String targetPath = path + "pics/supplement/" + supplementPO.getId() + "/";
                    FileHelper.save(targetPath,FileHelper.PicType,file);
                }
            }
            SupplementVO supplementVO = new SupplementVO(supplementPO);
            String[] fileList = FileHelper.getFileList(path + "pics/report/" + supplementVO.getId() + "/");
            supplementVO.setPicsList(fileList);
            return new ResultVO<SupplementVO>(Constant.REQUEST_SUCCESS, "补充说明报告提交成功", supplementVO);
        }else {
            return new ResultVO<>(Constant.REQUEST_FAIL, "发生未知错误");
        }
    }

    @Override
    public PageInfo<SupplementVO> getList(Integer report_id, Integer pageSize, Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<SupplementPO> pageInfo = new PageInfo<>(supplementMapper.selectByReportId(report_id));
        PageInfo<SupplementVO> result = PageInfoUtil.convert(pageInfo, SupplementVO.class);
        for(int i=0;i<result.getSize();i++){
            SupplementVO supplementVO = result.getList().get(i);
            UserPO userPO = userMapper.selectByUid(supplementVO.getUser_id());
            supplementVO.setUser_name(userPO.getUname());
        }
        return result;
    }

    @Override
    public void getSupplementPic(Integer supplementId, String name, HttpServletResponse response) {
        FileHelper.downloadFile(path + "pics/supplement/" + supplementId + "/",name,name,response);
    }

    @Override
    public SupplementVO getSupplement(Integer supplementId) {
        SupplementVO supplementVO = new SupplementVO(supplementMapper.selectByPrimaryKey(supplementId));
        String[] fileList = FileHelper.getFileList(path + "pics/supplement/" + supplementId + "/");
        supplementVO.setPicsList(fileList);
        UserPO userPO = userMapper.selectByUid(supplementVO.getUser_id());
        supplementVO.setUser_name(userPO.getUname());
        return supplementVO;
    }
}
