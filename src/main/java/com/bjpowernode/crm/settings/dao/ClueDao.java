package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int save(Clue clue);

    int getTotal(Map<String, Object> map);

    List<Clue> getClueList(Map<String, Object> map);

    Clue detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    int unbund(String id);

    List<Activity> getActivityListByNameAndNotByClueId(@Param("aname")String aname, @Param("clueId") String clueId);

    List<Activity> getActivityListByName(String aname);

    Clue getById(String clueId);

}
