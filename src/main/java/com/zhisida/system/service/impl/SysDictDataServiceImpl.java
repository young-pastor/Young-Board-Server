
package com.zhisida.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.consts.CommonConstant;
import com.zhisida.core.enums.CommonStatusEnum;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.exception.enums.StatusExceptionEnum;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysDictData;
import com.zhisida.system.enums.SysDictDataExceptionEnum;
import com.zhisida.system.mapper.SysDictDataMapper;
import com.zhisida.system.param.SysDictDataParam;
import com.zhisida.system.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统字典值service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public PageResult<SysDictData> page(SysDictDataParam sysDictDataParam) {

        //构造条件
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysDictDataParam)) {
            //根据字典类型查询
            if (ObjectUtil.isNotEmpty(sysDictDataParam.getTypeId())) {
                queryWrapper.eq(SysDictData::getTypeId, sysDictDataParam.getTypeId());
            }
            //根据字典值的编码模糊查询
            if (ObjectUtil.isNotEmpty(sysDictDataParam.getCode())) {
                queryWrapper.like(SysDictData::getCode, sysDictDataParam.getCode());
            }
            //根据字典值的内容模糊查询
            if (ObjectUtil.isNotEmpty(sysDictDataParam.getValue())) {
                queryWrapper.like(SysDictData::getValue, sysDictDataParam.getValue());
            }
        }
        //查询未删除的
        queryWrapper.ne(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysDictData::getSort);
        //返回分页查询结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysDictData> list(SysDictDataParam sysDictDataParam) {
        //构造条件,查询某个字典类型下的
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysDictDataParam)) {
            if (ObjectUtil.isNotEmpty(sysDictDataParam.getTypeId())) {
                queryWrapper.eq(SysDictData::getTypeId, sysDictDataParam.getTypeId());
            }
        }
        //查询未删除的
        queryWrapper.ne(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysDictData::getSort);
        return this.list(queryWrapper);
    }

    @Override
    public void add(SysDictDataParam sysDictDataParam) {

        //校验参数，检查是否存在重复的编码，不排除当前添加的这条记录
        checkParam(sysDictDataParam, false);

        //将dto转为实体
        SysDictData sysDictData = new SysDictData();
        BeanUtil.copyProperties(sysDictDataParam, sysDictData);

        //设置状态为启用
        sysDictData.setStatus(CommonStatusEnum.ENABLE.getCode());

        this.save(sysDictData);
    }

    @Override
    public void delete(SysDictDataParam sysDictDataParam) {

        //根据id查询实体
        SysDictData sysDictData = this.querySysDictData(sysDictDataParam);

        //逻辑删除，修改状态
        sysDictData.setStatus(CommonStatusEnum.DELETED.getCode());

        //更新实体
        this.updateById(sysDictData);
    }

    @Override
    public void edit(SysDictDataParam sysDictDataParam) {

        //根据id查询实体
        SysDictData sysDictData = this.querySysDictData(sysDictDataParam);

        //校验参数，检查是否存在重复的编码或者名称，排除当前编辑的这条记录
        checkParam(sysDictDataParam, true);

        //请求参数转化为实体
        BeanUtil.copyProperties(sysDictDataParam, sysDictData);

        //不能修改状态，用修改状态接口修改状态
        sysDictData.setStatus(null);

        this.updateById(sysDictData);
    }

    @Override
    public SysDictData detail(SysDictDataParam sysDictDataParam) {
        return this.querySysDictData(sysDictDataParam);
    }

    @Override
    public List<Dict> getDictDataListByDictTypeId(Long dictTypeId) {

        //构造查询条件
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
        queryWrapper.eq(SysDictData::getTypeId, dictTypeId).ne(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysDictData::getSort);
        //查询dictTypeId下所有的字典项
        List<SysDictData> results = this.list(queryWrapper);

        //抽取code和value封装到map返回
        List<Dict> dictList = CollectionUtil.newArrayList();
        results.forEach(sysDictData -> {
            Dict dict = Dict.create();
            dict.put(CommonConstant.CODE, sysDictData.getCode());
            dict.put(CommonConstant.VALUE, sysDictData.getValue());
            dictList.add(dict);
        });

        return dictList;
    }

    @Override
    public void deleteByTypeId(Long typeId) {
        //将所有typeId为某值的记录全部置为delete状态
        LambdaUpdateWrapper<SysDictData> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(SysDictData::getTypeId, typeId)
                .set(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode());
        this.update(queryWrapper);
    }

    @Override
    public void changeStatus(SysDictDataParam sysDictDataParam) {
        //根据id查询实体
        SysDictData sysDictData = this.querySysDictData(sysDictDataParam);
        Long id = sysDictData.getId();

        Integer status = sysDictDataParam.getStatus();

        //校验状态在不在枚举值里
        CommonStatusEnum.validateStatus(status);

        //更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysDictData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDictData::getId, id)
                .and(i -> i.ne(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode()))
                .set(SysDictData::getStatus, status);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new ServiceException(StatusExceptionEnum.UPDATE_STATUS_ERROR);
        }
    }

    @Override
    public List<String> getDictCodesByDictTypeCode(String... dictTypeCodes) {
        return this.baseMapper.getDictCodesByDictTypeCode(dictTypeCodes);
    }


    /**
     * 校验参数，校验是否存在相同的编码
     *
     * @author Young-Pastor
     */
    private void checkParam(SysDictDataParam sysDictDataParam, boolean isExcludeSelf) {
        Long id = sysDictDataParam.getId();
        Long typeId = sysDictDataParam.getTypeId();
        String code = sysDictDataParam.getCode();

        //构建带code的查询条件
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getTypeId, typeId)
                .eq(SysDictData::getCode, code)
                .ne(SysDictData::getStatus, CommonStatusEnum.DELETED.getCode());

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapper.ne(SysDictData::getId, id);
        }

        //查询重复记录的数量
        int countByCode = this.count(queryWrapper);

        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByCode >= 1) {
            throw new ServiceException(SysDictDataExceptionEnum.DICT_DATA_CODE_REPEAT);
        }
    }

    /**
     * 获取系统字典值
     *
     * @author Young-Pastor
     */
    private SysDictData querySysDictData(SysDictDataParam sysDictDataParam) {
        SysDictData sysDictData = this.getById(sysDictDataParam.getId());
        if (ObjectUtil.isNull(sysDictData)) {
            throw new ServiceException(SysDictDataExceptionEnum.DICT_DATA_NOT_EXIST);
        }
        return sysDictData;
    }
}
