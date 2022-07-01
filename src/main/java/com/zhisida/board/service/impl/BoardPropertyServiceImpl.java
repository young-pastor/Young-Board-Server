
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.factory.TreeBuildFactory;
import com.zhisida.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import com.zhisida.board.entity.BoardProperty;
import com.zhisida.board.enums.BoardPropertyExceptionEnum;
import com.zhisida.board.mapper.BoardPropertyMapper;
import com.zhisida.board.param.BoardPropertyParam;
import com.zhisida.board.service.BoardPropertyGroupService;
import com.zhisida.board.service.BoardPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 属性配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:25
 */
@Service
public class BoardPropertyServiceImpl extends ServiceImpl<BoardPropertyMapper, BoardProperty> implements BoardPropertyService {
    @Resource
    BoardPropertyGroupService propertyGroupService;
    @Override
    public List<AntdBaseTreeNode> tree(BoardPropertyParam boardPropertyParam) {
        List<AntdBaseTreeNode> treeNodeList = CollectionUtil.newArrayList();
        propertyGroupService.list().forEach(sysOrg -> {
            AntdBaseTreeNode orgTreeNode = new AntdBaseTreeNode();
            orgTreeNode.setId(sysOrg.getId());
            orgTreeNode.setParentId(sysOrg.getPid());
            orgTreeNode.setTitle(sysOrg.getDisplayName());
            orgTreeNode.setValue(String.valueOf(sysOrg.getId()));
            treeNodeList.add(orgTreeNode);
        });
        this.list().forEach(sysOrg -> {
            AntdBaseTreeNode orgTreeNode = new AntdBaseTreeNode();
            orgTreeNode.setId(sysOrg.getId());
            orgTreeNode.setParentId(sysOrg.getPropertyGroupId());
            orgTreeNode.setTitle(sysOrg.getDisplayName());
            orgTreeNode.setValue(String.valueOf(sysOrg.getId()));
            treeNodeList.add(orgTreeNode);
        });
        return new TreeBuildFactory<AntdBaseTreeNode>().doTreeBuild(treeNodeList);
    }
    @Override
    public PageResult<BoardProperty> page(BoardPropertyParam boardPropertyParam) {
        QueryWrapper<BoardProperty> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardPropertyParam)) {

            // 根据属性名称 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardProperty::getDisplayName, boardPropertyParam.getDisplayName());
            }
            // 根据属性分组 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getPropertyGroupId())) {
                queryWrapper.lambda().eq(BoardProperty::getPropertyGroupId, boardPropertyParam.getPropertyGroupId());
            }
            // 根据表字段ID 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getTableColumnId())) {
                queryWrapper.lambda().eq(BoardProperty::getTableColumnId, boardPropertyParam.getTableColumnId());
            }
            // 根据计算方式 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getMeasure())) {
                queryWrapper.lambda().eq(BoardProperty::getMeasure, boardPropertyParam.getMeasure());
            }
            // 根据属性值 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getValue())) {
                queryWrapper.lambda().eq(BoardProperty::getValue, boardPropertyParam.getValue());
            }
            // 根据属性值类型 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getValueType())) {
                queryWrapper.lambda().eq(BoardProperty::getValueType, boardPropertyParam.getValueType());
            }
            // 根据属性值 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getUnit())) {
                queryWrapper.lambda().eq(BoardProperty::getUnit, boardPropertyParam.getUnit());
            }
            // 根据属性值类型 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getUnitType())) {
                queryWrapper.lambda().eq(BoardProperty::getUnitType, boardPropertyParam.getUnitType());
            }
            // 根据属性值类型 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getIsDefault())) {
                queryWrapper.lambda().eq(BoardProperty::getIsDefault, boardPropertyParam.getIsDefault());
            }
            // 根据属性值类型 查询
            if (ObjectUtil.isNotEmpty(boardPropertyParam.getRemark())) {
                queryWrapper.lambda().eq(BoardProperty::getRemark, boardPropertyParam.getRemark());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardProperty> list(BoardPropertyParam boardPropertyParam) {
        return this.list();
    }

    @Override
    public void add(BoardPropertyParam boardPropertyParam) {
        BoardProperty boardProperty = new BoardProperty();
        BeanUtil.copyProperties(boardPropertyParam, boardProperty);
        this.save(boardProperty);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardPropertyParam> boardPropertyParamList) {
        boardPropertyParamList.forEach(boardPropertyParam -> {
            this.removeById(boardPropertyParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardPropertyParam boardPropertyParam) {
        BoardProperty boardProperty = this.queryBoardProperty(boardPropertyParam);
        BeanUtil.copyProperties(boardPropertyParam, boardProperty);
        this.updateById(boardProperty);
    }

    @Override
    public BoardProperty detail(BoardPropertyParam boardPropertyParam) {
        return this.queryBoardProperty(boardPropertyParam);
    }

    /**
     * 获取属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    private BoardProperty queryBoardProperty(BoardPropertyParam boardPropertyParam) {
        BoardProperty boardProperty = this.getById(boardPropertyParam.getId());
        if (ObjectUtil.isNull(boardProperty)) {
            throw new ServiceException(BoardPropertyExceptionEnum.NOT_EXIST);
        }
        return boardProperty;
    }

    @Override
    public void export(BoardPropertyParam boardPropertyParam) {
        List<BoardProperty> list = this.list(boardPropertyParam);
        PoiUtil.exportExcelWithStream("BoardProperty.xls", BoardProperty.class, list);
    }


}
