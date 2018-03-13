package com.taoato.manage.controller.api;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("api/item/cat")
@Controller
public class ApiItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    /*
    * 對外提供接口服務，查詢類目數據
    * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryAll(@RequestParam(value = "callback",required = false)String callback){
        try {
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            return ResponseEntity.ok(itemCatResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
