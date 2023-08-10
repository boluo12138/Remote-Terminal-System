package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzu.common.utils.JwtUtil;
import com.wzu.sys.entity.Menu;
import com.wzu.sys.entity.User;
import com.wzu.sys.entity.UserRole;
import com.wzu.sys.mapper.UserMapper;
import com.wzu.sys.mapper.UserRoleMapper;
import com.wzu.sys.service.IMenuService;
import com.wzu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author laocai
 * @since 2023-02-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;


    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
//        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
        if (loginUser != null && loginUser.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))) {
            // 暂时用UUID, 终极方案是jwt
            // String key = "user:" + UUID.randomUUID();

            // 存入redis
            loginUser.setPassword(null);
            //redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            // 创建jwt
            String token = jwtUtil.createToken(loginUser);

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }
        return null;
    }

    /*@Override
    public Map<String, Object> login(User user) {
        // 根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
        // 结果不为空，则生成token，并将用户信息存入redis
        if(loginUser != null){
            // 暂时用UUID, 终极方案是jwt
            String key = "user:" + UUID.randomUUID();

            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }*/

    @Override
    public Map<String, Object> getUserInfo(String token) {
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginUser != null) {
            //使用token获取到loginUser ，不为null，更新loginUser中的数据
            loginUser = baseMapper.selectById(loginUser.getId());
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getName());
            data.put("username", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            data.put("phone", loginUser.getPhone());
            data.put("type", loginUser.getStatus());
            data.put("email", loginUser.getEmail());
            data.put("createTime", loginUser.getCreateTime());
            data.put("id", loginUser.getId());

            // 角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            // 权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);

            System.out.println("data: " + data);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        // redisTemplate.delete(token);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // 写入用户表
        this.baseMapper.insert(user);
        // 写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }

    @Override
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户表
        this.baseMapper.updateById(user);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = user.getRoleIdList();
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }

    @Override
    public void deleteUserById(Integer id) {
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        userRoleMapper.delete(wrapper);
    }


    @Override
    public Map<String, Object> updatePassword(String token, String username, String oldPassword, String newPassword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User loginUser = this.baseMapper.selectOne(wrapper);  // 从数据库中获取用户名相同的用户的信息
        newPassword =DigestUtils.md5DigestAsHex(newPassword.getBytes());
//        if (loginUser != null && passwordEncoder.matches(oldPassword, loginUser.getPassword())) {
        if (loginUser != null && loginUser.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {

            redisTemplate.delete(token);  // 成功修改密码时需要删除之前的登录信息，重新登录
            loginUser.setPassword(newPassword);
            System.out.println("save login user: " + loginUser);
//            save(loginUser);
            System.out.println("save new password success");
            Map<String, Object> data = new HashMap<>();
            data.put("loginUser", loginUser);
            data.put("access", "update password SUCCESS");
            return data;
        }
        return null;
    }

}