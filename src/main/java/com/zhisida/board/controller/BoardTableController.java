
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardTableParam;
import com.zhisida.board.service.BoardTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据表配置控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:17:36
 */
@RestController
public class BoardTableController {

    @Resource
    private BoardTableService boardTableService;

    /**
     * 查询数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @GetMapping("/boardTable/page")
    @BusinessLog(title = "数据表配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardTableParam boardTableParam) {
        return new SuccessResponseData(boardTableService.page(boardTableParam));
    }

    /**
     * 添加数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @PostMapping("/boardTable/add")
    @BusinessLog(title = "数据表配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardTableParam.add.class) BoardTableParam boardTableParam) {
        boardTableService.add(boardTableParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据表配置，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @PostMapping("/boardTable/delete")
    @BusinessLog(title = "数据表配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardTableParam.delete.class) List<BoardTableParam> boardTableParamList) {
        boardTableService.delete(boardTableParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @PostMapping("/boardTable/edit")
    @BusinessLog(title = "数据表配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardTableParam.edit.class) BoardTableParam boardTableParam) {
        boardTableService.edit(boardTableParam);
        return new SuccessResponseData();
    }

    /**
     * 查看数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @GetMapping("/boardTable/detail")
    @BusinessLog(title = "数据表配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardTableParam.detail.class) BoardTableParam boardTableParam) {
        return new SuccessResponseData(boardTableService.detail(boardTableParam));
    }

    /**
     * 数据表配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @GetMapping("/boardTable/list")
    @BusinessLog(title = "数据表配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardTableParam boardTableParam) {
        return new SuccessResponseData(boardTableService.list(boardTableParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @GetMapping("/boardTable/export")
    @BusinessLog(title = "数据表配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardTableParam boardTableParam) {
        boardTableService.export(boardTableParam);
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    @Permission
    @PostMapping("/boardTable/sync")
    @BusinessLog(title = "数据表配置_同步", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData sync(@RequestBody BoardTableParam boardTableParams) {
        boardTableService.sync(boardTableParams);
        return new SuccessResponseData();
    }

}
