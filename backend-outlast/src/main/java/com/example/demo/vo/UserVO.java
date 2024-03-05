package com.example.demo.vo;

import com.example.demo.po.UserPO;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserVO {
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

    public UserVO() {

    }

    public UserVO(@NonNull UserPO user){
        this.uid = user.getUid();
        this.uname = user.getUname();
        this.password = user.getPassword();
        this.user_role = user.getUser_role();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.ability = user.getAbility();
        this.preference = user.getPreference();
        this.activity = user.getActivity();
        this.equipment = user.getEquipment();
        this.cooperation = user.getCooperation();
        this.evaluation = user.getEvaluation();
        this.similarity = user.getSimilarity();
    }
}
