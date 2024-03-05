package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.UserInformationVO;
import com.example.demo.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResultVO<UserVO> register(@RequestBody UserVO user){
        return userService.userRegister(user);
    }

    @PostMapping("/login")
    public ResultVO<UserVO> login(HttpServletRequest request, @RequestBody UserVO user){
        return userService.userLogin(request, user);
    }

    @PostMapping("/logout")
    public ResultVO<UserVO> logout(HttpServletRequest request){
        return userService.userLogout(request);
    }

    @PostMapping("/updateInformation")
    public ResultVO<UserVO> updateInformation(@RequestBody UserVO user){
        return userService.updateInformation(user);
    }

    @GetMapping("/getInformation")
    public UserInformationVO getInformation(@RequestParam Integer uid){
        return userService.getInformation(uid);
    }
}
