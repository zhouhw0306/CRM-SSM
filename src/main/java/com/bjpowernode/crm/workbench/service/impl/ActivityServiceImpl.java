package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ActivityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    ActivityDao activityDao;

    @Resource
    UserDao userDao;

    @Resource
    ActivityRemarkDao activityRemarkDao;

    public boolean save(Activity activity) {

        int save = activityDao.save(activity);
        if (save!=1){
            return false;
        }
            return true;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total
        int total = activityDao.getTotalByCondition(map);

        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAid(ids);

        //删除备注, 返回受到影响的条数(实际删除的数量)
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1!=count2){
            flag = false;
        }

        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //取uList
        List<User> uList = userDao.getUserList();
        //取a
        Activity a = activityDao.getById(id);

        Map<String,Object> map = new HashMap<>();

        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    @Override
    public boolean update(Activity activity) {

        int count = activityDao.update(activity);
        if (count!=1){
            return false;
        }
        return true;

    }

    @Override
    public Activity detail(String id) {

        Activity a = activityDao.detail(id);
        return  a;
    }

    @Override
    public List<ActivityRemark> getRemarkList(String id) {

        List<ActivityRemark> arList = activityRemarkDao.getRemarkList(id);

        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {

        int count = activityRemarkDao.deleteRemark(id);

        if (count!=1){
            return false;
        }

        return true;
    }

    @Override
    public int saveRemark(ActivityRemark ar) {

        int count = activityRemarkDao.saveRemark(ar);
        return count;
    }

    @Override
    public int updateRemark(ActivityRemark ar) {

        int count = activityRemarkDao.updateRemark(ar);
        return count;
    }

}
