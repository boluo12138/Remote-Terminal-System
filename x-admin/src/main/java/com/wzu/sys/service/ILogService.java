package com.wzu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Log;

/**
 * <p>
 * 日志信息表 服务类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
public interface ILogService extends IService<Log> {
    Result<?> add(Log log);
    Result<?> deleteById(Integer id);
    Result<?> update(Log log);
}
