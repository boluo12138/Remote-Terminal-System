package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Log;
import com.wzu.sys.mapper.LogMapper;
import com.wzu.sys.service.ILogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志信息表 服务实现类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Override
    public Result<?> add(Log log) {
        int insert = this.baseMapper.insert(log);
        if (insert == 1) {
            return Result.success("添加日志成功");
        }
        return Result.fail("添加日志失败");
    }

    @Override
    public Result<?> deleteById(Integer id) {
        int i = this.baseMapper.deleteById(id);
        if (i == 1) {
            return Result.success("删除日志成功");
        }
        return Result.fail("删除日志失败");
    }

    @Override
    public Result<?> update(Log log) {
        if (log.getId() == null || log.getId() <= 0) {
            return Result.fail("日志id不存在或不合法");
        }
        int i = this.baseMapper.updateById(log);
        if (i == 1) {
            return Result.success("更新日志成功");
        }
        return Result.fail("更新日志失败");
    }
}
