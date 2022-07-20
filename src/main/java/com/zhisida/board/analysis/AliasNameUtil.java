package com.zhisida.board.analysis;

import cn.hutool.core.util.RandomUtil;
import com.zhisida.board.analysis.enums.AliasNameEnum;

import java.util.Map;

public class AliasNameUtil {
    public static String getAliasName(Long id, AliasNameEnum aliseEnum, Map<String, String> aliasNames) {

        String key = aliseEnum.name() + id;
        if (aliasNames.containsKey(key)) {
            return aliasNames.get(key);
        }

        String aliasName = null;
        do {
            aliasName = "_" + RandomUtil.randomString(6);
        } while (aliasNames.containsValue(aliasName));
        aliasNames.put(key, aliasName);
        return aliasName;
    }
}
