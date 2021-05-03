package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> getListByClueId(String clueId);
}
