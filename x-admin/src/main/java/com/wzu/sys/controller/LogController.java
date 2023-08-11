package com.wzu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Conversation;
import com.wzu.sys.entity.Log;
import com.wzu.sys.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private ILogService logService;

    @PostMapping("/list")
    public Result<Page> getList(@RequestBody Log log) {
        Page<Log> logPage = new Page<>(log.getPageNum(), log.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name", log.getName());
        wrapper.like("server", log.getServer());
        Page page = logService.page(logPage, wrapper);
        if (page.getTotal() == 0) {
            Result.fail("没有查询到你所需要的会话信息");
        }
        return Result.success(page,"获取所有日志信息成功");
    }

    @PostMapping("/add")
    public Result<?> addConversation(@RequestBody Log log) {
        Result<?> add = logService.add(log);
        return add;
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteConversationById(@PathVariable("id") Integer id) {
        Result<?> result = logService.deleteById(id);
        return result;
    }

    @PostMapping("/update")
    public Result<?> updateConversation(@RequestBody Log log) {
        Result<?> update = logService.update(log);
        return update;
    }
}
