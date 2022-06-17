
package com.zhisida.board.service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.board.param.SysAppParam;
import com.zhisida.board.entity.SysVisLog;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一个service实现
 *
 * @author young-pastor
 */
@Service
public class DatasourceExampleService {

    @Resource
    private SysVisLogService sysVisLogService;

    @Resource
    private SysAppService sysAppService;

    public List<SysVisLog> masterDatasource() {
        return sysVisLogService.list();
    }

    public List<SysVisLog> backupDatasource() {
        return sysVisLogService.list();
    }

    public void datasourceTransactionNone() {

        SysAppParam sysAppParam = new SysAppParam();
        sysAppParam.setName(RandomUtil.randomNumbers(5));
        sysAppParam.setCode(RandomUtil.randomNumbers(5));
        sysAppParam.setActive("N");

        sysAppService.add(sysAppParam);

        //抛异常测试
        int i = 1 / 0;
    }

    //@DataSource(name = "master")
    @Transactional(rollbackFor = Exception.class)
    public void datasourceTransaction() {

        SysAppParam sysAppParam = new SysAppParam();
        sysAppParam.setName(RandomUtil.randomNumbers(5));
        sysAppParam.setCode(RandomUtil.randomNumbers(5));
        sysAppParam.setActive("N");

        sysAppService.add(sysAppParam);

        //抛异常测试
        int i = 1 / 0;
    }

}
