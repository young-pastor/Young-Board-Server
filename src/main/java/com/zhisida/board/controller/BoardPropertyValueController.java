
package com.zhisida.board.controller;

import com.zhisida.board.param.BoardPropertyValueParam;
import com.zhisida.board.service.BoardPropertyValueService;
import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 属性值控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:45
 */
@RestController
public class BoardPropertyValueController {

    @Resource
    private BoardPropertyValueService boardPropertyValueService;

    /**
     * 查询属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/page")
    @BusinessLog(title = "属性值_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardPropertyValueParam boardPropertyValueParam) {
        return ResponseData.success(boardPropertyValueService.page(boardPropertyValueParam));
    }

    /**
     * 添加属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/add")
    @BusinessLog(title = "属性值_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardPropertyValueParam.add.class) BoardPropertyValueParam boardPropertyValueParam) {
            boardPropertyValueService.add(boardPropertyValueParam);
        return ResponseData.success();
    }

    /**
     * 删除属性值，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/delete")
    @BusinessLog(title = "属性值_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardPropertyValueParam.delete.class) List<BoardPropertyValueParam> boardPropertyValueParamList) {
            boardPropertyValueService.delete(boardPropertyValueParamList);
        return ResponseData.success();
    }

    /**
     * 编辑属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/edit")
    @BusinessLog(title = "属性值_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardPropertyValueParam.edit.class) BoardPropertyValueParam boardPropertyValueParam) {
            boardPropertyValueService.edit(boardPropertyValueParam);
        return ResponseData.success();
    }

    /**
     * 查看属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/detail")
    @BusinessLog(title = "属性值_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardPropertyValueParam.detail.class) BoardPropertyValueParam boardPropertyValueParam) {
        return ResponseData.success(boardPropertyValueService.detail(boardPropertyValueParam));
    }

    /**
     * 属性值列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/list")
    @BusinessLog(title = "属性值_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardPropertyValueParam boardPropertyValueParam) {
        return ResponseData.success(boardPropertyValueService.list(boardPropertyValueParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/export")
    @BusinessLog(title = "属性值_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardPropertyValueParam boardPropertyValueParam) {
        boardPropertyValueService.export(boardPropertyValueParam);
    }

}
