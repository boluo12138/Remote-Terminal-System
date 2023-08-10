package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzu.sys.entity.Menu;
import com.wzu.sys.mapper.MenuMapper;
import com.wzu.sys.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author laocai
 * @since 2023-02-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getAllMenu() {
        // 一级菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,0);
        List<Menu> menuList = this.list(wrapper);
        // 填充子菜单
        setMenuChildren(menuList);
        System.out.println("--------------------------");
        System.out.println(menuList.toString());
        System.out.println("--------------------------");
        return menuList;
    }


    private void setMenuChildren(List<Menu> menuList) {
        if(menuList != null){
            for (Menu menu : menuList) {
                LambdaQueryWrapper<Menu> subWrapper = new LambdaQueryWrapper<>();
                subWrapper.eq(Menu::getParentId,menu.getMenuId());
                List<Menu> subMenuList = this.list(subWrapper);
                menu.setChildren(subMenuList);
                // 递归
                setMenuChildren(subMenuList);
            }
        }
    }

    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        // 一级菜单
        List<Menu> menuList = this.baseMapper.getMunuListByUserId(userId, 0);
        // 子菜单
        setMenuChildrenByUserId(userId, menuList);
        System.out.println("--------------------------");
        System.out.println(menuList.toString());
        System.out.println("--------------------------");
        return menuList;
    }

    private void setMenuChildrenByUserId(Integer userId, List<Menu> menuList) {
        if(menuList != null){
            for (Menu menu : menuList) {
                List<Menu> subMenuList = this.baseMapper.getMunuListByUserId(userId, menu.getMenuId());
                menu.setChildren(subMenuList);
                // 递归
                setMenuChildrenByUserId(userId,subMenuList);
            }
        }
    }

    @Autowired
    MenuMapper menuMapper;
    @Override
    public void deleteMenuChilren(Menu deletedmenu){
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",deletedmenu.getMenuId());
        List<Menu> menus = menuMapper.selectList(wrapper);
        if(menus != null){
            for(Menu menu : menus){
                deleteMenuChilren(menu);
            }
        }
        menuMapper.deleteById(deletedmenu);
    }
}
