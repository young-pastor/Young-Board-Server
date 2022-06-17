
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户表
 *
 * @author young-pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    @Excel(name = "账号", width = 20)
    private String account;

    /**
     * 密码哈希值
     */
    private String pwdHashValue;

    /**
     * 昵称
     */
    @Excel(name = "昵称", width = 20)
    private String nickName;

    /**
     * 姓名
     */
    @Excel(name = "姓名", width = 20)
    private String name;

    /**
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    @Excel(name = "生日", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date birthday;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    @Excel(name = "性别", replace = {"男_1", "女_2"}, width = 20)
    private Integer sex;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", width = 30)
    private String email;

    /**
     * 手机
     */
    @Excel(name = "手机", width = 30)
    private String phone;

    /**
     * 电话
     */
    @Excel(name = "电话", width = 30)
    private String tel;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", width = 30)
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 30)
    private Date lastLoginTime;

    /**
     * 管理员类型（1超级管理员 2非管理员）
     */
    @Excel(name = "管理员类型", replace = {"超级管理员_1", "非管理员_2"}, width = 20)
    private Integer adminType;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @Excel(name = "状态", replace = {"正常_0", "停用_1", "删除_2"}, width = 20)
    private Integer status;


}
