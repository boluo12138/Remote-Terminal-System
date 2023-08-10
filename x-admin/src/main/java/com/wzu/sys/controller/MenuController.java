package com.wzu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Menu;
import com.wzu.sys.mapper.MenuMapper;
import com.wzu.sys.mapper.RoleMenuMapper;
import com.wzu.sys.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author laocai
 * @since 2023-02-07
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleMenuMapper roleMenuMapper;
    @ApiOperation("查询所有菜单数据")
    @GetMapping
    public Result<List<Menu>> getAllMenu(){
        System.out.println("调用了getAllMenu");
        List<Menu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }

    @PostMapping("/delete")
    public Result<Menu> delete(@RequestBody Menu menu1){
        Menu menu = menuMapper.selectById(menu1);
        menuService.deleteMenuChilren(menu);
        return Result.success(menu);
    }

    @RequestMapping("/getInfo")
    public Result getInfo(@RequestBody Menu menu1){
        Menu menu = menuMapper.selectById(menu1);
        return Result.success(menu);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        log.info("Update menu:   "+menu.toString());
        menuMapper.updateById(menu);
        return Result.success(menuMapper.selectById(menu.getMenuId()));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        log.info("Adding menu:   "+menu.toString());
        menu.setIsLeaf("Y");
        menuMapper.insert(menu);
        return Result.success("添加成功");
    }

}
