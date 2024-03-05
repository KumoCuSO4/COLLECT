package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.SupplementMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.UserPO;
import com.example.demo.service.NLPService;
import com.example.demo.service.ReportService;
import com.example.demo.service.UserService;
import com.example.demo.util.Constant;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.UserInformationVO;
import com.example.demo.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    TaskMapper taskMapper;
    @Resource
    ReportMapper reportMapper;
    @Resource
    SupplementMapper supplementMapper;
    @Resource
    ReportService reportService;
    @Resource
    NLPService nlpService;

    @Override
    public ResultVO<UserVO> userRegister(UserVO user){
        String uname = user.getUname();
        String password = user.getPassword();
        if(userMapper.selectByUname(uname) == null){
            if(StringUtils.hasText(uname) && StringUtils.hasText(password)){
                user.setAbility(0.00);
                user.setActivity(1);
                user.setDescription("");
                user.setEquipment("");
                user.setPreference("");
                user.setCooperation(0.00);
                user.setEvaluation(0.00);
                user.setSimilarity(0.00);
                UserPO userpo = new UserPO(user);
                userMapper.insert(userpo);
                return new ResultVO<>(Constant.REQUEST_SUCCESS, "账号注册成功！",new UserVO(userpo));
            }else{
                return new ResultVO<>(Constant.REQUEST_FAIL, "用户名或密码不得为空！");
            }
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "该用户名已被使用。");
    }

    @Override
    public ResultVO<UserVO> userLogin(HttpServletRequest request, UserVO userVO){
        String uname = userVO.getUname();
        String password = userVO.getPassword();
        UserPO user = userMapper.selectByUname(uname);
        if(user == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "用户名无效。");
        }else{
            if(!user.getPassword().equals(password))
                return new ResultVO<>(Constant.REQUEST_FAIL, "账号或密码错误！");
        }
        request.getSession().setAttribute("user", user);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "账号登陆成功！", new UserVO(user));
    }

    @Override
    public ResultVO<UserVO> userLogout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return new ResultVO<>(Constant.REQUEST_SUCCESS,"注销成功");
    }

    @Override
    public ResultVO<UserVO> updateInformation(UserVO userVO) {
        userMapper.update(userVO.getUid(),userVO.getDescription());
        return new ResultVO<UserVO>(Constant.REQUEST_SUCCESS,"用户信息修改成功",new UserVO(userMapper.selectByUid(userVO.getUid())));
    }

    @Override
    public UserInformationVO getInformation(Integer uid) {
        List<ReportPO> reportPOList = reportMapper.selectByUid(uid);
        double count = 0.0;
        double similarity = 0.0;
        double ability = 0.0;
        for(ReportPO reportPO:reportPOList){
            count+=reportPO.getScore();
            similarity+=reportService.getSimilarity(reportPO.getId());

            String des = reportPO.getDescription();
            String step = reportPO.getStep();
            ability += nlpService.calculateAbility(des);
            ability += nlpService.calculateAbility(step);
            if(ability>0.2) ability=0.2;
        }
        if(reportPOList.size()>0){
            userMapper.updateEvaluation(uid,count/reportPOList.size());
            userMapper.updateSimilarity(uid,similarity/reportPOList.size());
            userMapper.updateAbility(uid,ability);
        }
        String user_equipment = taskMapper.selectEquipmentByUid(uid);
        if(user_equipment!=null){
            userMapper.updateEquipment(uid,user_equipment);
        }
        String user_preference = taskMapper.selectTypeByUid(uid);
        if(user_preference!=null){
            userMapper.updatePreference(uid,user_preference);
        }
        UserVO user = new UserVO(userMapper.selectByUid(uid));

        String[] preferences = Constant.PREFERENCES;
        String[] equipments = Constant.EQUIPMENTS;
        List<Integer> preference_report = new ArrayList<>();
        List<Integer> preference_supplement = new ArrayList<>();
        List<Double> preference_score = new ArrayList<>();
        List<Double> preference_similarity = new ArrayList<>();
        List<Integer> equipment_report = new ArrayList<>();
        List<Integer> equipment_supplement = new ArrayList<>();
        List<Double> equipment_score = new ArrayList<>();
        List<Double> equipment_similarity = new ArrayList<>();
        for(String preference:preferences){
            List<ReportPO> reportList = reportMapper.selectPreferenceByUid(uid,preference);
            //preference_supplement.add(supplementMapper.selectPreferenceByUid(uid,preference));
            if(reportList.size()>0) {
                preference_report.add(reportList.size());
                double score_sum = 0.0;
                double similarity_sum = 0.0;
                for (ReportPO reportPO : reportPOList) {
                    score_sum += reportPO.getScore();
                    similarity_sum += reportService.getSimilarity(reportPO.getId());
                }
                preference_score.add(score_sum / reportList.size());
                preference_similarity.add(similarity_sum / reportList.size());
            }else {
                preference_report.add(11);
                preference_supplement.add(9);
                preference_score.add(3.14);
                preference_similarity.add(15.9);
            }
        }
        for (String equipment:equipments){
            List<ReportPO> reportList = reportMapper.selectEquipmentByUid(uid,equipment);
            //equipment_supplement.add(supplementMapper.selectEquipmentByUid(uid,equipment));
            if(reportList.size()>0) {
                equipment_report.add(reportList.size());
                double score_sum = 0.0;
                double similarity_sum = 0.0;
                for (ReportPO reportPO : reportPOList) {
                    score_sum += reportPO.getScore();
                    similarity_sum += reportService.getSimilarity(reportPO.getId());
                }
                equipment_score.add(score_sum / reportList.size());
                equipment_similarity.add(similarity_sum / reportList.size());
            }else {
                equipment_report.add(77);
                equipment_supplement.add(66);
                equipment_score.add(4.18);
                equipment_similarity.add(60.9);
            }
        }
        return new UserInformationVO(user,preference_report,preference_supplement,preference_score,preference_similarity,equipment_report,equipment_supplement,equipment_score,equipment_similarity);
    }
}
