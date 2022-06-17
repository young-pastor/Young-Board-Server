
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.context.constant.ConstantContextHolder;
import com.zhisida.board.core.context.login.LoginContextHolder;
import com.zhisida.board.core.enums.CommonStatusEnum;
import com.zhisida.board.core.exception.PermissionException;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.exception.enums.PermissionExceptionEnum;
import com.zhisida.board.core.exception.enums.StatusExceptionEnum;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.CryptogramUtil;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.core.enums.AdminTypeEnum;
import com.zhisida.board.core.enums.OauthSexEnum;
import com.zhisida.board.core.enums.SexEnum;
import com.zhisida.board.service.*;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.board.param.SysEmpParam;
import com.zhisida.board.result.SysEmpInfo;
import com.zhisida.board.entity.SysUser;
import com.zhisida.board.enums.SysUserExceptionEnum;
import com.zhisida.board.context.factory.SysUserFactory;
import com.zhisida.board.mapper.SysUserMapper;
import com.zhisida.board.param.SysUserParam;
import com.zhisida.board.result.SysUserResult;
import com.zhisida.board.service.SysUserDataScopeService;
import com.zhisida.board.service.SysUserRoleService;
import com.zhisida.board.service.SysUserService;
import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户service接口实现类
 *
 * @author young-pastor
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysEmpService sysEmpService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private SysFileInfoService sysFileInfoService;

    @Override
    public SysUser getUserByCount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        return this.getOne(queryWrapper);
    }

    @Override
    public PageResult<SysUserResult> page(SysUserParam sysUserParam) {
        QueryWrapper<SysUserResult> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据关键字模糊查询（姓名，账号，手机号）
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchValue())) {
                queryWrapper.and(q -> q.like("sys_user.account", sysUserParam.getSearchValue())
                        .or().like("sys_user.name", sysUserParam.getSearchValue()));
            }
            //根据员工所属机构查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getSysEmpParam())) {
                SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
                if (ObjectUtil.isNotEmpty(sysEmpParam.getOrgId())) {
                    //查询属于该机构的，或该机构下级所有的人员
                    queryWrapper.and(q -> q.eq("sys_emp.org_id", sysEmpParam.getOrgId())
                            .or().like("sys_org.pids", sysEmpParam.getOrgId()));
                }
            }
            //根据状态查询（0正常 1停用），删除的不会展示在列表
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchStatus())) {
                queryWrapper.eq("sys_user.status", sysUserParam.getSearchStatus());
            }
        }
        //查询非删除状态，排除超级管理员
        queryWrapper.ne("sys_user.status", CommonStatusEnum.DELETED.getCode())
                .ne("sys_user.admin_type", AdminTypeEnum.SUPER_ADMIN.getCode());

        //如果是超级管理员则获取所有用户，否则只获取其数据范围的用户
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            if (ObjectUtil.isEmpty(dataScope)) {
                return new PageResult<>(new Page<>());
            } else {
                Set<Long> dataScopeSet = CollectionUtil.newHashSet(dataScope);
                queryWrapper.in("sys_emp.org_id", dataScopeSet);
            }
        }

        return new PageResult<>(this.baseMapper.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Dict> list(SysUserParam sysUserParam) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据账号，姓名模糊查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getAccount())) {
                queryWrapper.and(i -> i.like(SysUser::getAccount, sysUserParam.getAccount())
                        .or().like(SysUser::getName, sysUserParam.getAccount()));
            }
        }
        //查询正常状态，排除超级管理员
        queryWrapper.eq(SysUser::getStatus, CommonStatusEnum.ENABLE.getCode())
                .ne(SysUser::getAdminType, AdminTypeEnum.SUPER_ADMIN.getCode());
        this.list(queryWrapper).forEach(sysUser -> {
            Dict dict = Dict.create();
            dict.put("id", sysUser.getId().toString());
            dict.put("firstName", sysUser.getName() + SymbolConstant.LEFT_SQUARE_BRACKETS
                    + sysUser.getAccount() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            dictList.add(dict);
        });
        return dictList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysUserParam sysUserParam) {
        checkParam(sysUserParam, false);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取添加的用户的所属机构
            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所添加的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserParam, sysUser);
        SysUserFactory.fillAddCommonUserInfo(sysUser);
        if(ObjectUtil.isNotEmpty(sysUserParam.getPassword())) {
            sysUser.setPwdHashValue(CryptogramUtil.doHashValue(sysUserParam.getPassword()));
        }
        // 对铭感字段（手机号进行加密保护）
        if (ConstantContextHolder.getCryptogramConfigs().getFieldEncDec()) {
            sysUser.setPhone(CryptogramUtil.doEncrypt(sysUserParam.getPhone()));
        }
        this.save(sysUser);
        Long sysUserId = sysUser.getId();
        //增加员工信息
        SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
        sysEmpParam.setId(sysUserId);
        sysEmpService.addOrUpdate(sysEmpParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysUserParam> sysUserParamList) {
        sysUserParamList.forEach(sysUserParam -> {
            SysUser sysUser = this.querySysUser(sysUserParam);
            //不能删除超级管理员
            if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
                throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
            }
            boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
            //如果登录用户不是超级管理员，则进行数据权限校验
            if (!superAdmin) {
                List<Long> dataScope = sysUserParam.getDataScope();
                //获取要删除的用户的所属机构
                SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
                Long orgId = sysEmpInfo.getOrgId();
                //数据范围为空
                if (ObjectUtil.isEmpty(dataScope)) {
                    throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
                } else if (!dataScope.contains(orgId)) {
                    //所要删除的用户的所属机构不在自己的数据范围内
                    throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
                }
            }
            sysUser.setStatus(CommonStatusEnum.DELETED.getCode());
            this.updateById(sysUser);
            Long id = sysUser.getId();
            //删除该用户对应的员工表信息
            sysEmpService.deleteEmpInfoByUserId(id);

            //删除该用户对应的用户-角色表关联信息
            sysUserRoleService.deleteUserRoleListByUserId(id);

            //删除该用户对应的用户-数据范围表关联信息
            sysUserDataScopeService.deleteUserDataScopeListByUserId(id);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        checkParam(sysUserParam, true);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要编辑的用户的所属机构
            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要编辑的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }

        BeanUtil.copyProperties(sysUserParam, sysUser);
        //不能修改状态，用修改状态接口修改状态
        sysUser.setStatus(null);
        //设置密码
        SysUserFactory.fillBaseUserInfo(sysUser);
        if(ObjectUtil.isNotEmpty(sysUserParam.getPassword())) {
            // 将其哈希掉，存进去
            sysUser.setPwdHashValue(CryptogramUtil.doHashValue(sysUserParam.getPassword()));
        }

        // 对铭感字段（手机号进行加密保护）
        if (ConstantContextHolder.getCryptogramConfigs().getFieldEncDec()) {
            sysUser.setPhone(CryptogramUtil.doEncrypt(sysUserParam.getPhone()));
        }

        this.updateById(sysUser);
        Long sysUserId = sysUser.getId();
        //编辑员工信息
        SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
        BeanUtil.copyProperties(sysUserParam, sysEmpParam);
        sysEmpParam.setId(sysUserId);
        sysEmpService.addOrUpdate(sysEmpParam);
    }

    @Override
    public SysUserResult detail(SysUserParam sysUserParam) {
        SysUserResult sysUserResult = new SysUserResult();
        //获取系统用户
        SysUser sysUser = this.querySysUser(sysUserParam);
        BeanUtil.copyProperties(sysUser, sysUserResult);
        //获取对应员工信息
        SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
        //设置员工信息
        sysUserResult.setSysEmpInfo(sysEmpInfo);
        return sysUserResult;
    }

    @Override
    public void changeStatus(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //不能修改超级管理员状态
        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getId();

        Integer status = sysUserParam.getStatus();
        //校验状态在不在枚举值里
        CommonStatusEnum.validateStatus(status);

        //更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, id)
                .and(i -> i.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode()))
                .set(SysUser::getStatus, status);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new ServiceException(StatusExceptionEnum.UPDATE_STATUS_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantRole(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要授权角色的用户的所属机构
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要授权角色的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        sysUserRoleService.grantRole(sysUserParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantData(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要授权数据的用户的所属机构
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要授权数据的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        sysUserDataScopeService.grantData(sysUserParam);
    }

    @Override
    public void updateInfo(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        BeanUtil.copyProperties(sysUserParam, sysUser);
        this.updateById(sysUser);
    }

    @Override
    public void updatePwd(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //新密码与原密码相同
        if (sysUserParam.getNewPassword().equals(sysUserParam.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }
        //原密码错误
        if (!sysUser.getPwdHashValue().equals(CryptogramUtil.doHashValue(sysUserParam.getPassword()))) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_ERROR);
        }
        sysUser.setPwdHashValue(CryptogramUtil.doHashValue(sysUserParam.getNewPassword()));
        this.updateById(sysUser);
    }

    @Override
    public List<Long> getUserDataScopeIdList(Long userId, Long orgId) {
        Set<Long> userDataScopeIdSet = CollectionUtil.newHashSet();
        if (ObjectUtil.isAllNotEmpty(userId, orgId)) {

            //获取该用户对应的数据范围集合
            List<Long> userDataScopeIdListForUser = sysUserDataScopeService.getUserDataScopeIdList(userId);

            //获取该用户的角色对应的数据范围集合
            List<Long> userDataScopeIdListForRole = sysUserRoleService.getUserRoleDataScopeIdList(userId, orgId);

            userDataScopeIdSet.addAll(userDataScopeIdListForUser);
            userDataScopeIdSet.addAll(userDataScopeIdListForRole);
        }
        return CollectionUtil.newArrayList(userDataScopeIdSet);
    }

    @Override
    public String getNameByUserId(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser.getName();
    }

    @Override
    public List<Long> ownRole(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        return sysUserRoleService.getUserRoleIdList(sysUser.getId());
    }

    @Override
    public List<Long> ownData(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        return sysUserDataScopeService.getUserDataScopeIdList(sysUser.getId());
    }

    @Override
    public void resetPwd(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        String password = ConstantContextHolder.getDefaultPassWord();
        sysUser.setPwdHashValue(CryptogramUtil.doHashValue(password));
        this.updateById(sysUser);
    }

    @Override
    public void updateAvatar(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        Long avatar = sysUserParam.getAvatar();
        sysFileInfoService.assertFile(avatar);
        sysUser.setAvatar(avatar);
        this.updateById(sysUser);
    }

    @Override
    public void export(SysUserParam sysUserParam) {
        // 只导出正常的
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getStatus, CommonStatusEnum.ENABLE.getCode());
        // 其他的条件正常来说导出也只能是自己权限范围内看到的用户，改天我们再优化
        List<SysUser> list = this.list(queryWrapper);
        PoiUtil.exportExcelWithStream("SonwyUsers.xls", SysUser.class, list);
    }

    @Override
    public List<Dict> selector(SysUserParam sysUserParam) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据姓名模糊查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getName())) {
                queryWrapper.like(SysUser::getName, sysUserParam.getName());
            }
        }
        //查询非删除状态，排除超级管理员
        queryWrapper.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode())
                .ne(SysUser::getAdminType, AdminTypeEnum.SUPER_ADMIN.getCode());
        this.list(queryWrapper).forEach(sysUser -> {
            Dict dict  = Dict.create();
            dict.put(CommonConstant.ID, sysUser.getId());
            dict.put(CommonConstant.NAME, sysUser.getName());
            dictList.add(dict);
        });
        return dictList;
    }

    @Override
    public SysUser getUserById(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser;
    }

    @Override
    public void saveAuthUserToUser(AuthUser authUser, SysUser sysUser) {
        SysUserFactory.fillAddCommonUserInfo(sysUser);
        //获取oauth用户的账号
        String username = authUser.getUsername();
        //判断账号是否在用户表中有重复的
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, username);
        SysUser existUser = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(existUser)) {
            //如果该账号已经存在了，则将oauth用户的账号后缀拼接随机数
            username = username + RandomUtil.randomString(5);
        }
        sysUser.setAccount(username);
        sysUser.setName(authUser.getNickname());
        sysUser.setNickName(authUser.getNickname());
        sysUser.setEmail(authUser.getEmail());
        if (ObjectUtil.isNotNull(authUser.getGender())) {
            AuthUserGender gender = authUser.getGender();
            //性别转换
            if (OauthSexEnum.MAN.getCode().equals(gender.getCode())) {
                sysUser.setSex(SexEnum.MAN.getCode());
            } else if (OauthSexEnum.WOMAN.getCode().equals(gender.getCode())) {
                sysUser.setSex(SexEnum.WOMAN.getCode());
            } else {
                sysUser.setSex(SexEnum.NONE.getCode());
            }
        }
        this.save(sysUser);
    }

    @Override
    public List<Long> getAllUserIdList() {
        List<Long> resultList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysUser::getAdminType, AdminTypeEnum.SUPER_ADMIN);
        this.list(queryWrapper).forEach(sysUser -> {
            resultList.add(sysUser.getId());
        });
        return resultList;
    }

    @Override
    public boolean hasAllDeletedUser(Set<Long> userIdSet) {
        //查询id在此集合内，且状态为删除的用户，判断其数量是否大于等于集合数量，大于是为了容错
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, CommonStatusEnum.DELETED).in(SysUser::getId, userIdSet);
        return this.count(lambdaQueryWrapper) >= userIdSet.size();
    }

    /**
     * 校验参数，检查是否存在相同的账号
     *
     * @author young-pastor
     */
    private void checkParam(SysUserParam sysUserParam, boolean isExcludeSelf) {
        Long id = sysUserParam.getId();
        String account = sysUserParam.getAccount();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account)
                .ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        //是否排除自己，如果是则查询条件排除自己id
        if (isExcludeSelf) {
            queryWrapper.ne(SysUser::getId, id);
        }
        int countByAccount = this.count(queryWrapper);
        //大于等于1个则表示重复
        if (countByAccount >= 1) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_REPEAT);
        }
    }

    /**
     * 获取系统用户
     *
     * @author young-pastor
     */
    private SysUser querySysUser(SysUserParam sysUserParam) {
        SysUser sysUser = this.getById(sysUserParam.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser;
    }
}
