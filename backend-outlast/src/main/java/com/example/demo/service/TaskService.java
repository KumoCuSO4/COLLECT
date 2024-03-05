package com.example.demo.service;

import com.example.demo.vo.FileInfoVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface TaskService {
    ResultVO<TaskVO> createTask(TaskVO taskVO);

    PageInfo<TaskVO> getAllTask(Integer currPage, Integer pageSize);

    PageInfo<TaskVO> getList(Integer uid, Integer currPage, Integer pageSize);

    PageInfo<TaskVO> getCreated(Integer uid, Integer currPage, Integer pageSize);

    PageInfo<TaskVO> getRecommend(Integer uid, Integer recommendSize);

    TaskVO getTaskById(Integer taskId);

    ResultVO<FileInfoVO> updateExecutable(int taskId, MultipartFile file);

    ResultVO<FileInfoVO> updateDoc(int taskId, MultipartFile file);

    void downloadExecutable(int taskId, HttpServletResponse response);

    void downloadDoc(int taskId, HttpServletResponse response);
}
