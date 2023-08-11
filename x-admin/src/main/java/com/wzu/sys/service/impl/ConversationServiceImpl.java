package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Conversation;
import com.wzu.sys.mapper.ConversationMapper;
import com.wzu.sys.service.IConversationService;
import org.springframework.stereotype.Service;


@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Override
    public Result<?> add(Conversation conversation) {
        conversation.setId(null);
        int insert = this.baseMapper.insert(conversation);
        if (insert == 1) {
            return Result.success("添加会话成功");
        }
        return Result.fail("添加会话失败");
    }

    @Override
    public Result<?> deleteById(Integer id) {
        int i = this.baseMapper.deleteById(id);
        if (i == 1) {
            return Result.success("删除会话成功");
        }
        return Result.fail("删除会话失败");
    }

    @Override
    public Result<?> update(Conversation conversation) {
        if (conversation.getId() == null || conversation.getId() <= 0) {
            return Result.fail("会话id不存在或不合法");
        }
        int result = this.baseMapper.updateById(conversation);
        System.out.println("result: " + result);
        if (result == 1) {
            return Result.success("更新会话成功");
        }
        return Result.fail("更新会话失败");
    }
}
