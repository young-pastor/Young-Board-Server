
package com.zhisida.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.consts.CommonConstant;
import com.zhisida.core.consts.SymbolConstant;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.context.login.LoginContextHolder;
import com.zhisida.core.enums.CommonStatusEnum;
import com.zhisida.core.exception.PermissionException;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.exception.enums.PermissionExceptionEnum;
import com.zhisida.core.exception.enums.StatusExceptionEnum;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.CryptogramUtil;
import com.zhisida.core.util.PoiUtil;
import com.zhisida.core.enums.AdminTypeEnum;
import com.zhisida.core.enums.OauthSexEnum;
import com.zhisida.core.enums.SexEnum;
import com.zhisida.system.service.*;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.system.param.SysEmpParam;
import com.zhisida.system.result.SysEmpInfo;
import com.zhisida.system.entity.SysUser;
import com.zhisida.system.enums.SysUserExceptionEnum;
import com.zhisida.core.context.factory.SysUserFactory;
import com.zhisida.system.mapper.SysUserMapper;
import com.zhisida.system.param.SysUserParam;
import com.zhisida.system.result.SysUserResult;

import javax.annotation.Resource;
import java.util.*;

/**
 * ????????????service???????????????
 *
 * @author Young-Pastor
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
            //????????????????????????????????????????????????????????????
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchValue())) {
                queryWrapper.and(q -> q.like("sys_user.account", sysUserParam.getSearchValue())
                        .or().like("sys_user.name", sysUserParam.getSearchValue()));
            }
            //??????????????????????????????
            if (ObjectUtil.isNotEmpty(sysUserParam.getSysEmpParam())) {
                SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
                if (ObjectUtil.isNotEmpty(sysEmpParam.getOrgId())) {
                    //????????????????????????????????????????????????????????????
                    queryWrapper.and(q -> q.eq("sys_emp.org_id", sysEmpParam.getOrgId())
                            .or().like("sys_org.pids", sysEmpParam.getOrgId()));
                }
            }
            //?????????????????????0?????? 1??????????????????????????????????????????
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchStatus())) {
                queryWrapper.eq("sys_user.status", sysUserParam.getSearchStatus());
            }
        }
        //?????????????????????????????????????????????
        queryWrapper.ne("sys_user.status", CommonStatusEnum.DELETED.getCode())
                .ne("sys_user.admin_type", AdminTypeEnum.SUPER_ADMIN.getCode());

        //???????????????????????????????????????????????????????????????????????????????????????
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
            //?????????????????????????????????
            if (ObjectUtil.isNotEmpty(sysUserParam.getAccount())) {
                queryWrapper.and(i -> i.like(SysUser::getAccount, sysUserParam.getAccount())
                        .or().like(SysUser::getName, sysUserParam.getAccount()));
            }
        }
        //??????????????????????????????????????????
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
        //?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //????????????????????????????????????
            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
            //??????????????????
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //???????????????????????????????????????????????????????????????
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserParam, sysUser);
        SysUserFactory.fillAddCommonUserInfo(sysUser);
        if(ObjectUtil.isNotEmpty(sysUserParam.getPassword())) {
            sysUser.setPwdHashValue(CryptogramUtil.doHashValue(sysUserParam.getPassword()));
        }
        // ????????????????????????????????????????????????
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getFieldEncDec()) {
            sysUser.setPhone(CryptogramUtil.doEncrypt(sysUserParam.getPhone()));
        }
        this.save(sysUser);
        Long sysUserId = sysUser.getId();
        //??????????????????
        SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
        sysEmpParam.setId(sysUserId);
        sysEmpService.addOrUpdate(sysEmpParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysUserParam> sysUserParamList) {
        sysUserParamList.forEach(sysUserParam -> {
            SysUser sysUser = this.querySysUser(sysUserParam);
            //???????????????????????????
            if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
                throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
            }
            boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
            //?????????????????????????????????????????????????????????????????????
            if (!superAdmin) {
                List<Long> dataScope = sysUserParam.getDataScope();
                //???????????????????????????????????????
                SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
                Long orgId = sysEmpInfo.getOrgId();
                //??????????????????
                if (ObjectUtil.isEmpty(dataScope)) {
                    throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
                } else if (!dataScope.contains(orgId)) {
                    //??????????????????????????????????????????????????????????????????
                    throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
                }
            }
            sysUser.setStatus(CommonStatusEnum.DELETED.getCode());
            this.updateById(sysUser);
            Long id = sysUser.getId();
            //???????????????????????????????????????
            sysEmpService.deleteEmpInfoByUserId(id);

            //??????????????????????????????-?????????????????????
            sysUserRoleService.deleteUserRoleListByUserId(id);

            //??????????????????????????????-???????????????????????????
            sysUserDataScopeService.deleteUserDataScopeListByUserId(id);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        checkParam(sysUserParam, true);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //???????????????????????????????????????
            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
            //??????????????????
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //??????????????????????????????????????????????????????????????????
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }

        BeanUtil.copyProperties(sysUserParam, sysUser);
        //??????????????????????????????????????????????????????
        sysUser.setStatus(null);
        //????????????
        SysUserFactory.fillBaseUserInfo(sysUser);
        if(ObjectUtil.isNotEmpty(sysUserParam.getPassword())) {
            // ???????????????????????????
            sysUser.setPwdHashValue(CryptogramUtil.doHashValue(sysUserParam.getPassword()));
        }

        // ????????????????????????????????????????????????
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getFieldEncDec()) {
            sysUser.setPhone(CryptogramUtil.doEncrypt(sysUserParam.getPhone()));
        }

        this.updateById(sysUser);
        Long sysUserId = sysUser.getId();
        //??????????????????
        SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
        BeanUtil.copyProperties(sysUserParam, sysEmpParam);
        sysEmpParam.setId(sysUserId);
        sysEmpService.addOrUpdate(sysEmpParam);
    }

    @Override
    public SysUserResult detail(SysUserParam sysUserParam) {
        SysUserResult sysUserResult = new SysUserResult();
        //??????????????????
        SysUser sysUser = this.querySysUser(sysUserParam);
        BeanUtil.copyProperties(sysUser, sysUserResult);
        //????????????????????????
        SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
        //??????????????????
        sysUserResult.setSysEmpInfo(sysEmpInfo);
        return sysUserResult;
    }

    @Override
    public void changeStatus(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //?????????????????????????????????
        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getId();

        Integer status = sysUserParam.getStatus();
        //?????????????????????????????????
        CommonStatusEnum.validateStatus(status);

        //???????????????????????????????????????????????????
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
        //?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //?????????????????????????????????????????????
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //??????????????????
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //????????????????????????????????????????????????????????????????????????
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
        //?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //?????????????????????????????????????????????
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //??????????????????
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //????????????????????????????????????????????????????????????????????????
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
        //???????????????????????????
        if (sysUserParam.getNewPassword().equals(sysUserParam.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }
        //???????????????
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

            //??????????????????????????????????????????
            List<Long> userDataScopeIdListForUser = sysUserDataScopeService.getUserDataScopeIdList(userId);

            //???????????????????????????????????????????????????
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
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        SysUser sysUser = this.querySysUser(sysUserParam);
        String password = sysConfigCache.getDefaultPassWord();
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
        // ??????????????????
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getStatus, CommonStatusEnum.ENABLE.getCode());
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        List<SysUser> list = this.list(queryWrapper);
        PoiUtil.exportExcelWithStream("SysUsers.xls", SysUser.class, list);
    }

    @Override
    public List<Dict> selector(SysUserParam sysUserParam) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //????????????????????????
            if (ObjectUtil.isNotEmpty(sysUserParam.getName())) {
                queryWrapper.like(SysUser::getName, sysUserParam.getName());
            }
        }
        //?????????????????????????????????????????????
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
        //??????oauth???????????????
        String username = authUser.getUsername();
        //?????????????????????????????????????????????
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, username);
        SysUser existUser = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(existUser)) {
            //???????????????????????????????????????oauth????????????????????????????????????
            username = username + RandomUtil.randomString(5);
        }
        sysUser.setAccount(username);
        sysUser.setName(authUser.getNickname());
        sysUser.setNickName(authUser.getNickname());
        sysUser.setEmail(authUser.getEmail());
        if (ObjectUtil.isNotNull(authUser.getGender())) {
            AuthUserGender gender = authUser.getGender();
            //????????????
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
        //??????id?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, CommonStatusEnum.DELETED).in(SysUser::getId, userIdSet);
        return this.count(lambdaQueryWrapper) >= userIdSet.size();
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @author Young-Pastor
     */
    private void checkParam(SysUserParam sysUserParam, boolean isExcludeSelf) {
        Long id = sysUserParam.getId();
        String account = sysUserParam.getAccount();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account)
                .ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        //?????????????????????????????????????????????????????????id
        if (isExcludeSelf) {
            queryWrapper.ne(SysUser::getId, id);
        }
        int countByAccount = this.count(queryWrapper);
        //????????????1??????????????????
        if (countByAccount >= 1) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_REPEAT);
        }
    }

    /**
     * ??????????????????
     *
     * @author Young-Pastor
     */
    private SysUser querySysUser(SysUserParam sysUserParam) {
        SysUser sysUser = this.getById(sysUserParam.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser;
    }
}
