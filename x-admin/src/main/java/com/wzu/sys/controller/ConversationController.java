package com.wzu.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Conversation;
import com.wzu.sys.service.IConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/conv")
public class ConversationController {
    @Autowired
    private IConversationService conversationService;

    @PostMapping("/list")
    public Result<Page> getList(@RequestBody Conversation conv) {

        Page<Conversation> convPage = new Page<>(conv.getPageNum(), conv.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name", conv.getName());
        wrapper.like("ip", conv.getIp());
        Page page = conversationService.page(convPage, wrapper);
        if (page.getTotal() == 0) {
            Result.fail("没有查询到你所需要的会话信息");
        }
        return Result.success(page, "获取所有会话信息成功");
    }

    @PostMapping("/add")
    public Result<?> addConversation(@RequestBody Conversation conversation) {
        Result<?> add = conversationService.add(conversation);
        return add;
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteConversationById(@PathVariable("id") Integer id) {
        Result<?> result = conversationService.deleteById(id);
        return result;
    }

    @PostMapping("/update")
    public Result<?> updateConversation(@RequestBody Conversation conversation) {
        Result<?> update = conversationService.update(conversation);
        return update;
    }
}
