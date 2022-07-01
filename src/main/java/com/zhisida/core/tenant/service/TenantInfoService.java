
package com.zhisida.core.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.tenant.entity.TenantInfo;
import com.zhisida.core.tenant.params.TenantInfoParam;

/**
 * 租户表 服务类
 *
 * @author Young-Pastor
 */
public interface TenantInfoService extends IService<TenantInfo> {

    /**
     * 新增租户
     *
     * @param param 添加参数
     * @author Young-Pastor
     */
    void add(TenantInfoParam param);

    /**
     * 删除租户
     *
     * @param param 删除参数
     * @author Young-Pastor
     */
    void delete(TenantInfoParam param);

    /**
     * 更新租户
     *
     * @param param 更新参数
     * @author Young-Pastor
     */
    void update(TenantInfoParam param);

    /**
     * 分页查询租户列表
     *
     * @param param 查询参数
     * @return 查询结果
     * @author Young-Pastor
     */
    PageResult<TenantInfo> page(TenantInfoParam param);

    /**
     * 获取租户信息，通过租户编码
     *
     * @param code 租户编码
     * @return 租户信息
     * @author Young-Pastor
     */
    TenantInfo getByCode(String code);

}
