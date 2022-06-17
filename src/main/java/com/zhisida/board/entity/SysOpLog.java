
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 系统操作日志表
 *
 * @author young-pastor
 */
@Data
@TableName("sys_op_log")
public class SysOpLog {

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
     * 操作类型（见LogAnnotionOpTypeEnum）
     */
    @Excel(name = "操作类型", width = 20)
    private Integer opType;

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
    @Excel(name = "浏览器", width = 40)
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统", width = 20)
    private String os;

    /**
     * 请求地址
     */
    @Excel(name = "请求地址", width = 40)
    private String url;

    /**
     * 类名称
     */
    @Excel(name = "类名称", width = 20)
    private String className;

    /**
     * 方法名称
     */
    @Excel(name = "方法名称", width = 20)
    private String methodName;

    /**
     * 请求方式（GET POST PUT DELETE)
     */
    @Excel(name = "请求方式", width = 20)
    private String reqMethod;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数", width = 40)
    private String param;

    /**
     * 返回结果
     */
    @Excel(name = "返回结果", width = 20)
    private String result;

    /**
     * 操作时间
     */
    @Excel(name = "操作时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opTime;

    /**
     * 操作人
     */
    private String account;

    /**
     * 签名数据（ID除外）
     */
    private String signValue;

    /**
     * 重写tostring方法 并去除所有空格
     */
    @Override
    public String toString () {
        String toStr = name + opType + success + message + ip + location + browser
                + os + url + className + methodName + reqMethod + param + result
                + opTime + account;
        return toStr.replaceAll(" +","");
    }

}
