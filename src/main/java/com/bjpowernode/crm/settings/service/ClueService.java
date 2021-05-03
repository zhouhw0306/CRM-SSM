package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.Clue;
import com.bjpowernode.crm.settings.domain.Tran;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ClueService {

    boolean save(Clue clue);

    PaginationVO<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    boolean unbund(String id);

    List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId);

    boolean bund(String cid, String[] aids);

    List<Activity> getActivityListByName(String aname);

    boolean convert(String clueId, Tran t, String createBy);
}
