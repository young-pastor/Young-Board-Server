
package com.zhisida.board.core.context.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.enums.CommonStatusEnum;
import com.zhisida.board.core.util.CryptogramUtil;
import com.zhisida.board.core.enums.AdminTypeEnum;
import com.zhisida.board.core.enums.SexEnum;
import com.zhisida.board.entity.SysUser;

/**
 * 填充用户附加信息工厂
 *
 * @author young-pastor
 */
public class SysUserFactory {

    /**
     * 管理员类型（1超级管理员 2非管理员）
     * 新增普通用户时填充相关信息
     *
     * @author young-pastor
     */
    public static void fillAddCommonUserInfo(SysUser sysUser) {
        fillBaseUserInfo(sysUser);
        sysUser.setAdminType(AdminTypeEnum.NONE.getCode());
    }

    /**
     * 填充用户基本字段
     *
     * @author young-pastor
     */
    public static void fillBaseUserInfo(SysUser sysUser) {
        //密码为空则设置密码（密码都为哈希值哦）
        if (ObjectUtil.isEmpty(sysUser.getPwdHashValue())) {
            //没有密码则设置默认密码
            SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
            String password = sysConfigCache.getDefaultPassWord();
            //设置密码为Sm3的哈希值，这里代表保护密码的完整性
            sysUser.setPwdHashValue(CryptogramUtil.doHashValue(password));
        }

        if (ObjectUtil.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(null);
        }

        if (ObjectUtil.isEmpty(sysUser.getSex())) {
            sysUser.setSex(SexEnum.NONE.getCode());
        }

        sysUser.setStatus(CommonStatusEnum.ENABLE.getCode());
    }
}
