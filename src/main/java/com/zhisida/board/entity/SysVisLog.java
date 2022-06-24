
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 系统访问日志表
 *
 * @author Young-Pastor
 */
@Data
@TableName("sys_vis_log")
public class SysVisLog {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 20)
    private String name;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    @Excel(name = "是否执行成功", replace = {"是_Y", "否_N"}, width = 20)
    private String success;

    /**
     * 具体消息
     */
    @Excel(name = "具体消息", width = 20)
    private String message;

    /**
     * ip
     */
    @Excel(name = "ip", width = 20)
    private String ip;

    /**
     * 地址
     */
    @Excel(name = "地址", width = 20)
    private String location;

    /**
     * 浏览器
     */
    @Excel(name = "浏览器", width = 20)
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统", width = 20)
    private String os;

    /**
     * 访问类型（字典 1登入 2登出）
     */
    @Excel(name = "访问类型", replace = {"登入_1", "登出_2"}, width = 20)
    private Integer visType;

    /**
     * 访问时间
     */
    @Excel(name = "操作时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visTime;

    /**
     * 访问人
     */
    private String account;

    /**
     * 签名数据（ID除外）
     */
    private String signValue;

    /**
     * 重写tostring方法
     */
    @Override
    public String toString(){
        String toStr = name + success + message + ip + location + browser
                + os + visType + visTime + account;
        return toStr.replaceAll(" +","");
    }
}
