package com.example.demo.controller;

import com.example.demo.service.TaskService;
import com.example.demo.util.Constant;
import com.example.demo.vo.FileInfoVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    //创建任务
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResultVO<TaskVO> createTask(TaskVO task) {
        return taskService.createTask(task);
    }


    //获取任务广场全部任务
    @GetMapping("/getAll")
    public PageInfo<TaskVO> getAllTask(@RequestParam Integer page) {
        return taskService.getAllTask(page, Constant.TASK_PAGE_SIZE);
    }

    //众包工人获取任务广场全部任务
    @GetMapping("/getList")
    public PageInfo<TaskVO> getList(@RequestParam Integer uid, @RequestParam Integer page) {
        return taskService.getList(uid, page, Constant.TASK_PAGE_SIZE);
    }

    //发包方获取其发布过的全部任务
    @GetMapping("/getCreated")
    public PageInfo<TaskVO> getCreated(@RequestParam Integer uid, @RequestParam Integer page) {
        return taskService.getCreated(uid, page, Constant.TASK_PAGE_SIZE);
    }

    //获取相关用户的推荐任务列表
    @GetMapping("/getRecommend")
    public PageInfo<TaskVO> getRecommend(@RequestParam Integer uid) {
        return taskService.getRecommend(uid, Constant.TASK_RECOMMEND);
    }

    //查询具体众包任务信息
    @GetMapping("/{taskId}")
    public TaskVO getTaskById(@PathVariable Integer taskId) {
        return taskService.getTaskById(taskId);
    }

    //上传可执行文件
    @PostMapping("/{taskId}/updateExecutable")
    public ResultVO<FileInfoVO> updateExecutable(@PathVariable Integer taskId, @RequestParam("file") MultipartFile file) {
        return taskService.updateExecutable(taskId,file);
    }

    //上传文档
    @PostMapping("/{taskId}/updateDoc")
    public ResultVO<FileInfoVO> updateDoc(@PathVariable Integer taskId, @RequestParam("file") MultipartFile file) {
        return taskService.updateDoc(taskId,file);
    }

    //下载可执行文件
    @GetMapping("/{taskId}/downloadExecutable")
    public void downloadExecutable(@PathVariable Integer taskId, HttpServletResponse response) {
        taskService.downloadExecutable(taskId,response);
    }

    //下载文档
    @GetMapping("/{taskId}/downloadDoc")
    public void downloadDoc(@PathVariable Integer taskId, HttpServletResponse response) {
        taskService.downloadDoc(taskId,response);
    }
}
