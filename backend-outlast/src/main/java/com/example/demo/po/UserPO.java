package com.example.demo.po;

import com.example.demo.vo.UserVO;
import lombok.Data;

@Data
public class UserPO {
    private Integer uid;

    private String uname;

    private String password;

    private Integer user_role;

    private String phone;

    private String email;

    private String description;

    private Double ability;

    private String preference;

    private Integer activity;

    private String equipment;

    private Double cooperation;

    private Double evaluation;

    private Double similarity;

    public UserPO() {

    }

    public UserPO(UserVO userVO){
        this.uid = userVO.getUid();
        this.uname = userVO.getUname();
        this.password = userVO.getPassword();
        this.user_role = userVO.getUser_role();
        this.phone = userVO.getPhone();
        this.email = userVO.getEmail();
        this.description = userVO.getDescription();
        this.ability = userVO.getAbility();
        this.preference = userVO.getPreference();
        this.activity = userVO.getActivity();
        this.equipment = userVO.getEquipment();
        this.cooperation = userVO.getCooperation();
        this.evaluation = userVO.getEvaluation();
        this.similarity = userVO.getSimilarity();
    }
}
