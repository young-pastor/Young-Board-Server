
package com.zhisida.board.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zhisida.board.core.pojo.base.node.BaseTreeNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认递归工具类，用于遍历有父子关系的节点，例如菜单树，字典树等等
 *
 * @author young-pastor
 */
@Data
public class TreeBuildFactory<T extends BaseTreeNode> {

    /**
     * 顶级节点的父节点id(默认0)
     */
    private Long rootParentId = 0L;

    /**
     * 树节点构造
     *
     * @author young-pastor
     */
    public List<T> doTreeBuild(List<T> nodes) {

        //具体构建的过程
        List<T> buildComplete = this.executeBuilding(nodes);

        //构建之后的处理工作
        return this.afterBuild(buildComplete);
    }

    /**
     * 查询子节点集合
     *
     * @author young-pastor
     */
    private void buildChildNodes(List<T> totalNodes, T node, List<T> childNodeLists) {
        if (ObjectUtil.hasEmpty(totalNodes, node)) {
            return;
        }
        List<T> nodeSubLists = this.getSubChildLevelOne(totalNodes, node);
        if (ObjectUtil.isNotEmpty(nodeSubLists)) {
            nodeSubLists.forEach(t -> this.buildChildNodes(totalNodes, t, CollectionUtil.newArrayList()));
        }
//        childNodeLists.addAll(nodeSubLists);
        node.setChildren(nodeSubLists);
    }

    /**
     * 获取子一级节点的集合
     *
     * @author young-pastor
     */
    private List<T> getSubChildLevelOne(List<T> list, T node) {
        List<T> nodeList = CollectionUtil.newArrayList();
        if (ObjectUtil.isNotEmpty(list)) {
            list.forEach(t -> {
                if (t.getPid().equals(node.getId())) {
                    nodeList.add(t);
                }
            });
        }
        return nodeList;
    }

    /**
     * 执行构造
     *
     * @author young-pastor
     */
    private List<T> executeBuilding(List<T> nodes) {
        List<T> parentNodes = afterBuild(nodes);
        parentNodes.forEach(t -> this.buildChildNodes(nodes, t, CollectionUtil.newArrayList()));
        return parentNodes;
    }

    /**
     * 构造之后
     *
     * @author young-pastor
     */
    private List<T> afterBuild(List<T> nodes) {
        //去掉所有的二级节点
        ArrayList<T> results = CollectionUtil.newArrayList();
        nodes.forEach(t -> {
            if (rootParentId.equals(t.getPid())) {
                results.add(t);
            }
        });
        return results;
    }
}
