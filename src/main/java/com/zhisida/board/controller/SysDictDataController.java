
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.base.param.BaseParam;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysDictDataParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysDictDataService;

import javax.annotation.Resource;

/**
 * 系统字典值控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysDictDataController {

    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 查询系统字典值
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictData/page")
    @BusinessLog(title = "系统字典值_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.page(sysDictDataParam));
    }

    /**
     * 某个字典类型下所有的字典
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictData/list")
    @BusinessLog(title = "系统字典值_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@Validated(BaseParam.list.class) SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.list(sysDictDataParam));
    }

    /**
     * 查看系统字典值
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictData/detail")
    @BusinessLog(title = "系统字典值_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BaseParam.detail.class) SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.detail(sysDictDataParam));
    }

    /**
     * 添加系统字典值
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictData/add")
    @BusinessLog(title = "系统字典值_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BaseParam.add.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.add(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统字典值
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictData/delete")
    @BusinessLog(title = "系统字典值_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BaseParam.delete.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.delete(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统字典值
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictData/edit")
    @BusinessLog(title = "系统字典值_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BaseParam.edit.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.edit(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictData/changeStatus")
    @BusinessLog(title = "系统字典值_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(BaseParam.changeStatus.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.changeStatus(sysDictDataParam);
        return new SuccessResponseData();
    }

}
