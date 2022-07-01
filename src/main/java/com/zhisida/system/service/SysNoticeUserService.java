
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.system.entity.SysNoticeUser;

import java.util.List;

/**
 * 系统通知公告用户service接口
 *
 * @author Young-Pastor
 */
public interface SysNoticeUserService extends IService<SysNoticeUser> {

    /**
     * 添加
     *
     * @param noticeId         通知公告id
     * @param noticeUserIdList 通知到的用户id集合
     * @param noticeUserStatus 阅读状态
     * @author Young-Pastor
     */
    void add(Long noticeId, List<Long> noticeUserIdList, Integer noticeUserStatus);

    /**
     * 编辑
     *
     * @param noticeId         通知公告id
     * @param noticeUserIdList 通知到的用户id集合
     * @param noticeUserStatus 阅读状态
     * @author Young-Pastor
     */
    void edit(Long noticeId, List<Long> noticeUserIdList, Integer noticeUserStatus);

    /**
     * 根据通知公告id查询通知人员信息集合
     *
     * @param noticeId 通知公告id
     * @return 通知用户列表
     * @author Young-Pastor
     */
    List<SysNoticeUser> getSysNoticeUserListByNoticeId(Long noticeId);

    /**
     * 设为已读
     *
     * @param noticeId 通知公告id
     * @param userId   用户id
     * @param status   阅读状态
     * @author Young-Pastor
     */
    void read(Long noticeId, Long userId, Integer status);
}
