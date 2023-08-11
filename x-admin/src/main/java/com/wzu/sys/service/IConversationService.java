package com.wzu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Conversation;


public interface IConversationService extends IService<Conversation> {
    Result<?> add(Conversation conversation);
    Result<?> deleteById(Integer id);
    Result<?> update(Conversation conversation);
}
