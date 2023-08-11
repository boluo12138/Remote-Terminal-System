package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagePara {
    @TableField(exist = false)
    private int pageSize;

    @TableField(exist = false)
    private int pageNum;
}
