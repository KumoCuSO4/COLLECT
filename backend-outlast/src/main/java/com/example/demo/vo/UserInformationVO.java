package com.example.demo.vo;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class UserInformationVO {
    private UserVO userVO;

    private List<Integer> preference_report;

    private List<Integer> preference_supplement;

    private List<Double> preference_score;

    private List<Double> preference_similarity;

    private List<Integer> equipment_report;

    private List<Integer> equipment_supplement;

    private List<Double> equipment_score;

    private List<Double> equipment_similarity;

    public UserInformationVO(UserVO user,List<Integer> preference_report,List<Integer> preference_supplement,List<Double> preference_score,List<Double> preference_similarity,
                             List<Integer> equipment_report,List<Integer> equipment_supplement,List<Double> equipment_score,List<Double> equipment_similarity){
        this.userVO = user;
        this.preference_report = preference_report;
        this.preference_supplement = preference_supplement;
        this.preference_score = preference_score;
        this.preference_similarity = preference_similarity;
        this.equipment_report = equipment_report;
        this.equipment_supplement = equipment_supplement;
        this.equipment_score = equipment_score;
        this.equipment_similarity = equipment_similarity;
    }
}
