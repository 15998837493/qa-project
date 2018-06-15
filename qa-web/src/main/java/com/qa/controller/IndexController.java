
package com.qa.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qa.master.service.QuestionService;

/**
 * 问卷首页
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-5-30 zcq 初版
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private QuestionService questionService;

    /**
     * 跳转到问卷首页
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = "/index.action")
    public String dlym(Model model) {
        // List<QuestionProblemPojo> problemList = questionService.queryProblemAndOptionByQuestionId("Q001");
        // model.addAttribute("problemList", NetJsonUtils.listToJsonArray(problemList));
        // System.out.println(NetJsonUtils.listToJsonArray(problemList));
        // 保存token值
        request.getSession().setAttribute("userCode", "show");
        return "/index";
    }

}
