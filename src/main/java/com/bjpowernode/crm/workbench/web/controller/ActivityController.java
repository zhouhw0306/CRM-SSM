package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/getUserList.do")
    @ResponseBody
    public List<User> getUsers(){

        List<User> users = userService.selectUserList();
        return users;
    }

    @RequestMapping("/workbench/activity/save.do")
    @ResponseBody
    public boolean sace(Activity activity, HttpServletRequest request){

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        User sessionUser =(User) request.getSession().getAttribute("sessionUser");
        activity.setCreateBy(sessionUser.getName());
        boolean flag = activityService.save(activity);
        return flag ;
    }

    @RequestMapping("/workbench/activity/pageList.do")
    @ResponseBody
    public PaginationVO pageList(Integer pageNo, Integer pageSize, String name, String owner, String startDate ,String endDate){

        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        /*
                前端需要: 市场活动信息列表, 查询的总条数
                业务层拿到了以上两项信息之后, 如果做返回


         */

        PaginationVO<Activity> vo = activityService.pageList(map);

        return vo;
    }



    @RequestMapping("/activity/detail.do")
    public String detail(){
        return "workbench/activity/detail";
    }



    @RequestMapping("/workbench/activity/delete.do")
    @ResponseBody
    public boolean delete(HttpServletRequest request){

        String[] ids = request.getParameterValues("id");

        boolean flag = activityService.delete(ids);

        return flag;
    }



    //打开修改的模态窗口,把数据铺上去
    @RequestMapping("/workbench/activity/getUserListAndActivity.do")
    @ResponseBody
    public Map<String,Object> getUserListAndActivity(String id){


        Map<String,Object> map = activityService.getUserListAndActivity(id);

        return map;
    }

    //市场活动修改操作
    @RequestMapping("/workbench/activity/update.do")
    @ResponseBody
    public boolean update(Activity activity, HttpServletRequest request){

        String editBy = ((User)request.getSession().getAttribute("sessionUser")).getName();
        activity.setEditBy(editBy); //修改时间
        activity.setEditTime(DateTimeUtil.getSysTime()); //修改人

        boolean flag = activityService.update(activity);
        return flag ;
    }

    //进入到跳转的详情页
    @RequestMapping("/workbench/activity/detail.do")
    public ModelAndView detail(String id){

        Activity a = activityService.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("a",a);
        mv.setViewName("workbench/activity/detail");
        return mv;

    }

    //根据市场活动id, 取得备注信息列表
    @RequestMapping("/workbench/activity/getRemarkListByAid.do")
    @ResponseBody
    public List getRemarkListByAid(String id){

        List<ActivityRemark> arList = activityService.getRemarkList(id);

        return  arList;
    }


    @RequestMapping("/workbench/activity/deleteRemark.do")
    @ResponseBody
    public boolean deleteRemark(String id){

        boolean flag = activityService.deleteRemark(id);

        return  flag;
    }

    @RequestMapping("/workbench/activity/saveRemark.do")
    @ResponseBody
    public Map saveRemark(String noteContent,String activityId,HttpServletRequest request){

        ActivityRemark ar = new ActivityRemark();
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setId(UUIDUtil.getUUID());
        ar.setCreateTime(DateTimeUtil.getSysTime());
        ar.setCreateBy(((User)request.getSession().getAttribute("sessionUser")).getName());
        ar.setEditFlag("0");

        int count = activityService.saveRemark(ar);

        Map<String,Object> map = new HashMap<>();
        map.put("success",count);
        map.put("ar",ar);

        return map;
    }

    @RequestMapping("/workbench/activity/updateRemark.do")
    @ResponseBody
    public Map updateRemark(String id,String noteContent,HttpServletRequest request){


        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag("1");
        ar.setEditBy(((User)request.getSession().getAttribute("sessionUser")).getName());
        ar.setEditTime(DateTimeUtil.getSysTime());
        int count = activityService.updateRemark(ar);

        Map<String,Object> map = new HashMap<>();
        map.put("success",count);
        map.put("ar",ar);

        return map;
    }

    //跳转线索页面
    @RequestMapping("/clue/index.do")
    public String clue(){
        return "workbench/clue/index";
    }
    //跳转客户页面
    @RequestMapping("/customer/index.do")
    public String customer(){
        return "workbench/customer/index";
    }

    //跳转联系人页面
    @RequestMapping("/contacts/index.do")
    public String contacts(){
        return "workbench/contacts/index";
    }

    //跳转交易页面
    @RequestMapping("/transaction/index.do")
    public String transaction(){
        return "workbench/transaction/index";
    }

    //跳转售后回访页面
    @RequestMapping("/visit/index.do")
    public String visit(){
        return "workbench/visit/index";
    }


}
