package com.zhisida.board.core.context.wrapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.pojo.base.wrapper.BaseWrapper;
import com.zhisida.board.core.util.CryptogramUtil;
import com.zhisida.board.result.SysUserResult;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户管理的包装类
 *
 * @author young-pastor
 */
public class SysUserWrapper implements BaseWrapper<SysUserResult> {

    @Override
    public Map<String, Object> doWrap(SysUserResult sysUserResult) {
        Map<String, Object> map = new HashMap<>();

        // 是否开启用户表字段加解密，如果是被加密的，返回列表时需要脱敏
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getFieldEncDec()) {
            if (ObjectUtil.isNotEmpty(sysUserResult.getPhone())) {
                map.put("phone", CryptogramUtil.doDecrypt(sysUserResult.getPhone()));
            }
        }

        // 下面这里其他的字段，需要做翻译脱敏的，下面处理即可

        return map;
    }

}
