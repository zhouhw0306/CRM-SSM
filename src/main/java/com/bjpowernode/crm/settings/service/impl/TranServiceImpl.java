package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.TranDao;
import com.bjpowernode.crm.settings.dao.TranHistoryDao;
import com.bjpowernode.crm.settings.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;


}
