package com.wzu.webssh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* @Description: webssh数据传输
* @Author: NoCortY
* @Date: 2020/3/8
*/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WebSSHData {
    //操作
    private String operate;
    private String host;
    //端口号默认为22
    private Integer port = 22;
    private String username;
    private String password;
    private String command = "";


}
