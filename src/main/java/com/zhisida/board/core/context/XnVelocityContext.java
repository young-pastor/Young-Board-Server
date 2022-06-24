
package com.zhisida.board.core.context;

import org.apache.velocity.VelocityContext;
import com.zhisida.board.core.enums.YesOrNotEnum;
import com.zhisida.board.core.param.XnCodeGenParam;
import com.zhisida.board.entity.SysCodeGenerateConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 设置上下文缓存
 *
 * @author Young-Pastor
 */
public class XnVelocityContext {

    /**
     * 创建上下文用到的参数
     *
     * @author Young-Pastor
     */
    public VelocityContext createVelContext (XnCodeGenParam xnCodeGenParam) {
        VelocityContext velocityContext = new VelocityContext();
        // 取得类名
        String DomainName = xnCodeGenParam.getClassName();
        String domainName = DomainName.substring(0,1).toLowerCase()+DomainName.substring(1);
        // 类名称
        velocityContext.put("ClassName",DomainName);
        // 类名（首字母小写）
        velocityContext.put("className",domainName);

        // 功能名
        velocityContext.put("functionName",xnCodeGenParam.getFunctionName());

        // 包名称
        velocityContext.put("packageName",xnCodeGenParam.getPackageName());
        // 模块名称
        velocityContext.put("modularName",xnCodeGenParam.getModularNane());
        // 业务名
        velocityContext.put("busName",xnCodeGenParam.getBusName());

        // 作者姓名
        velocityContext.put("authorName", xnCodeGenParam.getAuthorName());
        // 代码生成时间
        velocityContext.put("createDateString", xnCodeGenParam.getCreateTimeString());

        // 数据库表名
        velocityContext.put("tableName", xnCodeGenParam.getTableName());
        // 数据库字段
        velocityContext.put("tableField", xnCodeGenParam.getConfigList());

        // 前端查询所有
        List<SysCodeGenerateConfig> codeGenerateConfigList = new ArrayList<>();
        xnCodeGenParam.getConfigList().forEach(item -> {
            if (item.getQueryWhether().equals(YesOrNotEnum.Y.getCode())) {
                codeGenerateConfigList.add(item);
            }
        });
        velocityContext.put("queryWhetherList", codeGenerateConfigList);

        velocityContext.put("appCode", xnCodeGenParam.getAppCode());

        velocityContext.put("menuPids", xnCodeGenParam.getMenuPids() + "[" + xnCodeGenParam.getMenuPid() + "],");

        // sql中id的创建
        List<Long> idList = new ArrayList<>();
        for (int a = 0; a <= 7; a++) {
            idList.add(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        }
        velocityContext.put("sqlMenuId", idList);

        return velocityContext;
    }
}
