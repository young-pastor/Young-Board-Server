
package com.zhisida.board.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhisida.board.core.pojo.base.wrapper.BaseWrapper;
import lombok.Data;
import com.zhisida.board.result.SysEmpInfo;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户结果
 *
 * @author Young-Pastor
 */
@Data
public class SysUserResult implements BaseWrapper {

    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String tel;

    /**
     * 用户员工信息
     */
    private SysEmpInfo sysEmpInfo;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    private Integer status;

    @Override
    public Map<String, Object> doWrap(Object beWrappedModel) {
        return null;
    }
}
