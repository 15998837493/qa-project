
package com.qa.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qa.master.pojo.ItemPojo;
import com.qa.master.service.ItemService;
import com.qa.result.WebResult;

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
public class ItemController extends BaseController {

    @Resource
    private ItemService itemService;

    /**
     * 检索指标列表
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = "/rest/query_item.action")
    public @ResponseBody
    WebResult<List<ItemPojo>> queryItem() {
        WebResult<List<ItemPojo>> webResult = new WebResult<List<ItemPojo>>();
        List<ItemPojo> itemList = itemService.queryItem(null);
        return webResult.setSuc(itemList);
    }

}
