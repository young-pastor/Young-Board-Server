
package com.zhisida.core.pojo.node;

import com.zhisida.core.pojo.base.node.BaseTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 通用树节点
 *
 * @author Young-Pastor
 */
@Data
public class CommonBaseTreeNode implements BaseTreeNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 节点父id
     */
    private Long pid;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 子节点集合
     */
    private List children;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Long getPid() {
        return this.pid;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    /*@Override
    public String getNodeId() {
        return this.nodeId;
    }

    @Override
    public String getNodeParentId() {
        return this.nodeParentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.childrenNodes = childrenNodes;
    }*/
}
