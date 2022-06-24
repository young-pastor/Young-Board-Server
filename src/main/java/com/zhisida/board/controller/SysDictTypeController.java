
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.base.param.BaseParam;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysDictTypeParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysDictTypeService;

import javax.annotation.Resource;

/**
 * 系统字典类型控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysDictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 分页查询系统字典类型
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictType/page")
    @BusinessLog(title = "系统字典类型_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.page(sysDictTypeParam));
    }

    /**
     * 获取字典类型列表
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictType/list")
    @BusinessLog(title = "系统字典类型_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.list(sysDictTypeParam));
    }

    /**
     * 获取字典类型下所有字典，举例，返回格式为：[{code:"M",value:"男"},{code:"F",value:"女"}]
     *
     * @author Young-Pastor
     */
    @GetMapping("/sysDictType/dropDown")
    @BusinessLog(title = "系统字典类型_下拉", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dropDown(@Validated(BaseParam.dropDown.class) SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.dropDown(sysDictTypeParam));
    }

    /**
     * 查看系统字典类型
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysDictType/detail")
    @BusinessLog(title = "系统字典类型_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BaseParam.detail.class) SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.detail(sysDictTypeParam));
    }

    /**
     * 添加系统字典类型
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictType/add")
    @BusinessLog(title = "系统字典类型_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BaseParam.add.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.add(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统字典类型
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictType/delete")
    @BusinessLog(title = "系统字典类型_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BaseParam.delete.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.delete(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统字典类型
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictType/edit")
    @BusinessLog(title = "系统字典类型_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BaseParam.edit.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.edit(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysDictType/changeStatus")
    @BusinessLog(title = "系统字典类型_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(BaseParam.changeStatus.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.changeStatus(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 系统字典类型与字典值构造的树
     *
     * @author Young-Pastor
     */
    @GetMapping("/sysDictType/tree")
    @BusinessLog(title = "系统字典类型_树", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree() {
        return new SuccessResponseData(sysDictTypeService.tree());
    }
}
