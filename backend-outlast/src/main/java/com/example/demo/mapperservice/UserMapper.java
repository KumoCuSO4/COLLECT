package com.example.demo.mapperservice;

import com.example.demo.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insert(UserPO userPO);

    UserPO selectByUid(int uid);

    UserPO selectByUname(String uname);

    void increaseActivity(Integer uid, Integer num);

    void update(Integer uid, String description);

    void updateEquipment(Integer uid, String equipment);

    void updatePreference(Integer uid, String preference);

    void updateEvaluation(Integer uid, Double evaluation);

    void updateSimilarity(Integer uid, Double similarity);

    void updateAbility(Integer uid, Double ability);
}
