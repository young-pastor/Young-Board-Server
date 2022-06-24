
package com.zhisida.board.entity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.board.core.pojo.base.node.BaseTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统区域表
 *
 * @author Young-Pastor
 */
@Data
@TableName("sys_area")
public class SysArea implements BaseTreeNode {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 层级
     */
    private Integer levelCode;

    /**
     * 父级行政代码
     */
    private String parentCode;

    /**
     * 行政代码
     */
    private String areaCode;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组合名
     */
    private String mergerName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    @TableField(exist = false)
    private List children = CollectionUtil.newArrayList();

    @Override
    public Long getPid() {
        return Convert.toLong(parentCode);
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
