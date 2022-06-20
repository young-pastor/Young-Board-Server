
package com.zhisida.board.boardpropertyvalue.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardPropertyValueParam;
import com.zhisida.board.service.BoardPropertyValueService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 属性值控制器
 *
 * @author young-pastor
 * @date 2022-06-20 11:57:45
 */
@RestController
public class BoardPropertyValueController {

    @Resource
    private BoardPropertyValueService boardPropertyValueService;

    /**
     * 查询属性值
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/page")
    @BusinessLog(title = "属性值_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardPropertyValueParam boardPropertyValueParam) {
        return new SuccessResponseData(boardPropertyValueService.page(boardPropertyValueParam));
    }

    /**
     * 添加属性值
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/add")
    @BusinessLog(title = "属性值_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardPropertyValueParam.add.class) BoardPropertyValueParam boardPropertyValueParam) {
            boardPropertyValueService.add(boardPropertyValueParam);
        return new SuccessResponseData();
    }

    /**
     * 删除属性值，可批量删除
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/delete")
    @BusinessLog(title = "属性值_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardPropertyValueParam.delete.class) List<BoardPropertyValueParam> boardPropertyValueParamList) {
            boardPropertyValueService.delete(boardPropertyValueParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑属性值
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @PostMapping("/boardPropertyValue/edit")
    @BusinessLog(title = "属性值_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardPropertyValueParam.edit.class) BoardPropertyValueParam boardPropertyValueParam) {
            boardPropertyValueService.edit(boardPropertyValueParam);
        return new SuccessResponseData();
    }

    /**
     * 查看属性值
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/detail")
    @BusinessLog(title = "属性值_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardPropertyValueParam.detail.class) BoardPropertyValueParam boardPropertyValueParam) {
        return new SuccessResponseData(boardPropertyValueService.detail(boardPropertyValueParam));
    }

    /**
     * 属性值列表
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/list")
    @BusinessLog(title = "属性值_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardPropertyValueParam boardPropertyValueParam) {
        return new SuccessResponseData(boardPropertyValueService.list(boardPropertyValueParam));
    }

    /**
     * 导出系统用户
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    @Permission
    @GetMapping("/boardPropertyValue/export")
    @BusinessLog(title = "属性值_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardPropertyValueParam boardPropertyValueParam) {
        boardPropertyValueService.export(boardPropertyValueParam);
    }

}
