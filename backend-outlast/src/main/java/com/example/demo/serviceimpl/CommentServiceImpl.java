package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.CommentMapper;
import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.CommentPO;
import com.example.demo.po.ReportPO;
import com.example.demo.po.UserPO;
import com.example.demo.service.CommentService;
import com.example.demo.util.Constant;
import com.example.demo.util.PageInfoUtil;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    UserMapper userMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    ReportMapper reportMapper;
    @Override
    public ResultVO<CommentVO> createComment(CommentVO comment) {
        CommentPO temp = commentMapper.selectByUserId(comment.getUser_id(),comment.getReport_id());
        if(temp!=null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "重复发布评论！");
        }
        comment.setPost_time(new Date());//获取当前时间
        CommentPO commentPO = new CommentPO(comment);
        if(commentMapper.insert(commentPO) > 0){
            //更新report的总评分
            List<CommentPO> commentPOList = commentMapper.selectByReportId(commentPO.getReport_id());
            if(commentPOList.size()==1){
                reportMapper.updateScore(commentPO.getReport_id(),commentPO.getScore());
            }else{
                double score = 0.0;
                for(CommentPO commentPO1:commentPOList){
                    score+=commentPO1.getScore();
                }
                score = score/commentPOList.size();
                reportMapper.updateScore(commentPO.getReport_id(),score);
            }
            //增加用户活跃度
            userMapper.increaseActivity(comment.getUser_id(),Constant.COMMENT_INCREASE);
            return new ResultVO<CommentVO>(Constant.REQUEST_SUCCESS, "评论发布成功", new CommentVO(commentPO));
        }else {
            return new ResultVO<>(Constant.REQUEST_FAIL, "发生未知错误");
        }
    }

    @Override
    public PageInfo<CommentVO> getComments(Integer report_id, Integer pageSize, Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<CommentPO> pageInfo = new PageInfo<>(commentMapper.selectByReportId(report_id));
        PageInfo<CommentVO> result = PageInfoUtil.convert(pageInfo, CommentVO.class);
        for(int i=0;i<result.getSize();i++){
            CommentVO commentVO = result.getList().get(i);
            UserPO userPO = userMapper.selectByUid(commentVO.getUser_id());
            commentVO.setUser_name(userPO.getUname());
        }
        return result;
    }
}
