
package com.zhisida.board.core.pojo.base.node;

import java.util.List;

/**
 * 树节点接口
 *
 * @author young-pastor
 */
public interface BaseTreeNode {


    /**
     * 获取节点id
     *
     * @return 节点id
     * @author young-pastor
     */
    Long getId();

    /**
     * 获取节点父id
     *
     * @return 节点父id
     * @author young-pastor
     */
    Long getPid();

    /**
     * 设置children
     *
     * @param children 子节点集合
     * @author young-pastor
     */
    void setChildren(List children);
}
