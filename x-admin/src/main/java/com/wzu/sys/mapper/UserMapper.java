package com.wzu.sys.mapper;

import com.wzu.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author laocai
 * @since 2023-02-07
 */
public interface UserMapper extends BaseMapper<User> {

     List<String> getRoleNameByUserId(Integer userId);
}
