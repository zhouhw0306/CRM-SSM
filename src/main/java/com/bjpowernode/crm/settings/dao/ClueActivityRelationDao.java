package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);
}
