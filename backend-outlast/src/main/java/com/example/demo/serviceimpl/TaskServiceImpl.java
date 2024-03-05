package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.TaskPO;
import com.example.demo.po.UserPO;
import com.example.demo.service.TaskService;
import com.example.demo.util.Constant;
import com.example.demo.util.FileHelper;
import com.example.demo.util.PageInfoUtil;
import com.example.demo.vo.FileInfoVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    TaskMapper taskMapper;
    @Resource
    TaskOrderMapper taskOrderMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public ResultVO<TaskVO> createTask(TaskVO task) {
        MultipartFile executableFile = task.getExecutable_file();
        MultipartFile docFile = task.getDoc_file();
        if(task.getName()==null) return new ResultVO<>(Constant.REQUEST_FAIL, "任务名称不得为空");
        if(task.getDescription()==null) return new ResultVO<>(Constant.REQUEST_FAIL, "任务描述不得为空");
        if(task.getType()==null) return new ResultVO<>(Constant.REQUEST_FAIL, "任务类型不得为空");
        if(task.getEquipment()==null) return new ResultVO<>(Constant.REQUEST_FAIL, "测试设备不得为空");
        if(task.getEnd_time()==null) return new ResultVO<>(Constant.REQUEST_FAIL, "截止时间不得为空");
        if(task.getWorker_num()==0) return new ResultVO<>(Constant.REQUEST_FAIL, "所需工人数量不得为空");
        if(task.getDifficulty()==0) return new ResultVO<>(Constant.REQUEST_FAIL, "任务难度不得为空");
        if(executableFile==null) return new ResultVO<>(Constant.REQUEST_FAIL, "需要可执行文件");
        if(docFile==null) return new ResultVO<>(Constant.REQUEST_FAIL, "需要描述文档");

        ResultVO<FileInfoVO> exeRes = FileHelper.save(path,FileHelper.ExeType,executableFile);
        ResultVO<FileInfoVO> docRes = FileHelper.save(path,FileHelper.DocType,docFile);
        try{
            if(Objects.equals(exeRes.getCode(), Constant.REQUEST_FAIL)) {
                throw new Exception(exeRes.getMsg());
            }
            if(Objects.equals(docRes.getCode(), Constant.REQUEST_FAIL)) {
                throw new Exception(docRes.getMsg());
            }
        } catch (Exception e) {
            if(exeRes.getData()!=null && exeRes.getData().getUuid()!=null)
                FileHelper.deleteFile(path,exeRes.getData().getUuid());
            if(docRes.getData()!=null && docRes.getData().getUuid()!=null)
                FileHelper.deleteFile(path,docRes.getData().getUuid());
            return new ResultVO<>(Constant.REQUEST_FAIL,e.getMessage());
        }
        TaskPO taskPO = new TaskPO(task,exeRes.getData().getUuid(),docRes.getData().getUuid());
        if(taskMapper.insert(taskPO) > 0){
            return new ResultVO<TaskVO>(Constant.REQUEST_SUCCESS, "众包任务创建成功", new TaskVO(taskPO));
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "发生未知错误");
    }

    @Override
    public PageInfo<TaskVO> getAllTask(Integer currPage, Integer pageSize) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        List<TaskPO> taskPOList = taskMapper.selectAllTask();
        taskPOList.removeIf(taskPO -> taskPO.getEnd_time().before(new Date()));
        PageInfo<TaskPO> pageInfo = new PageInfo<>(taskPOList);
        PageInfo<TaskVO> result = PageInfoUtil.convert(pageInfo, TaskVO.class);
        return result;
    }

    @Override
    public PageInfo<TaskVO> getList(Integer uid, Integer currPage, Integer pageSize) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        List<TaskPO> taskPOList = taskMapper.selectWithUid(uid);
        taskPOList.removeIf(taskPO -> taskPO.getEnd_time().before(new Date()));
        PageInfo<TaskPO> pageInfo = new PageInfo<>(taskPOList);
        PageInfo<TaskVO> result = PageInfoUtil.convert(pageInfo, TaskVO.class);
        return result;
    }

    @Override
    public PageInfo<TaskVO> getCreated(Integer uid, Integer currPage, Integer pageSize) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<TaskPO> pageInfo = new PageInfo<>(taskMapper.selectCreatedWithUid(uid));
        PageInfo<TaskVO> result = PageInfoUtil.convert(pageInfo, TaskVO.class);
        return result;
    }

    @Override
    public PageInfo<TaskVO> getRecommend(Integer uid, Integer recommendSize) {
        UserPO user = userMapper.selectByUid(uid);
        double ability = user.getAbility();
        List<TaskPO> tasks = taskMapper.selectWithUidWithDiffNearest(uid,ability,recommendSize);
        PageInfo<TaskPO> pageInfo = new PageInfo<>(tasks);
        PageInfo<TaskVO> result = PageInfoUtil.convert(pageInfo, TaskVO.class);
        return result;
    }

    @Override
    public TaskVO getTaskById(Integer taskId) {
        return new TaskVO(taskMapper.selectById(taskId));
    }

    @Value("${web.file-upload-path}")
    private String path;

    @Override
    public ResultVO<FileInfoVO> updateExecutable(int taskId, MultipartFile file) {
        deleteExecutable(taskId);
        return FileHelper.save(path,FileHelper.ExeType,file);
    }

    @Override
    public ResultVO<FileInfoVO> updateDoc(int taskId, MultipartFile file) {
        deleteDoc(taskId);
        return FileHelper.save(path,FileHelper.DocType,file);
    }

    @Override
    public void downloadExecutable(int taskId, HttpServletResponse response) {
        TaskPO taskPO = taskMapper.selectById(taskId);
        FileHelper.downloadFile(path,taskPO.getExecutable_file_uuid(),taskPO.getExecutable_file(),response);
    }

    @Override
    public void downloadDoc(int taskId, HttpServletResponse response) {
        TaskPO taskPO = taskMapper.selectById(taskId);
        FileHelper.downloadFile(path,taskPO.getDoc_file_uuid(),taskPO.getDoc_file(),response);
    }

    private void deleteExecutable(int taskId) {
        TaskPO taskPO = taskMapper.selectById(taskId);
        FileHelper.deleteFile(path,taskPO.getExecutable_file_uuid());
    }

    private void deleteDoc(int taskId) {
        TaskPO taskPO = taskMapper.selectById(taskId);
        FileHelper.deleteFile(path,taskPO.getDoc_file_uuid());
    }
}
