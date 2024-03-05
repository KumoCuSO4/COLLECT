package com.example.demo.service;

import com.example.demo.vo.ResultVO;
import com.example.demo.vo.UserInformationVO;
import com.example.demo.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ResultVO<UserVO> userRegister(UserVO user);

    ResultVO<UserVO> userLogin(HttpServletRequest request, UserVO userVO);

    ResultVO<UserVO> userLogout(HttpServletRequest request);

    ResultVO<UserVO> updateInformation(UserVO userVO);

    UserInformationVO getInformation(Integer uid);
}
