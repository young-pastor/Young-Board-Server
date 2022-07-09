package com.zhisida.board.analysis;

import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.analysis.provider.DataSourceProvider;
import com.zhisida.board.entity.BoardDataSource;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;


public class DataSourceProviderManager {

    public static DataSourceProvider getDataProvider(BoardDataSource boardDataSource) {
        Assert.notNull(boardDataSource, "传入参数不能为空");
        Assert.hasLength(boardDataSource.getType(), "数据源类型不能为空");
        Map<String, DataSourceProvider> beans = SpringUtil.getBeansOfType(DataSourceProvider.class);
        Assert.notEmpty(beans, "未找到可查询数据源");
        String curKey = boardDataSource.getType() + "DataSourceProvider";
        Optional<String> d = beans.keySet().stream().filter(k -> k.equalsIgnoreCase(curKey)).findFirst();
        if (d.isPresent()) {
            DataSourceProvider provider = beans.get(d.get());
            provider.setConfig(boardDataSource.getConfig());
            Assert.notNull(provider, "未找到可查询数据源");
            return provider;
        }
        throw new RuntimeException("未找到可查询数据源");
    }
}
