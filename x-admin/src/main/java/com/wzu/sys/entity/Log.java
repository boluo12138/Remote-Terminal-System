package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("x_log")
@ToString
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 部署服务器名
     */
    private String server;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 查看日志需要执行的命令
     */
    private String command;

    private String username;

    /**
     * 密码
     */
    private String password;


}
