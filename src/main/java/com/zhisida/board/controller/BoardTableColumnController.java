
package com.zhisida.board.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardTableColumnParam;
import com.zhisida.board.service.BoardTableColumnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字段配置控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:27:41
 */
@RestController
public class BoardTableColumnController {

    @Resource
    private BoardTableColumnService boardTableColumnService;

    @Permission
    @GetMapping("/boardTableColumn/tree")
    @BusinessLog(title = "数据字段配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree(BoardTableColumnParam boardTableColumnParam) {
        return new SuccessResponseData(boardTableColumnService.tree(boardTableColumnParam));
    }

    /**
     * 查询数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @GetMapping("/boardTableColumn/page")
    @BusinessLog(title = "数据字段配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardTableColumnParam boardTableColumnParam) {
        return new SuccessResponseData(boardTableColumnService.page(boardTableColumnParam));
    }

    /**
     * 添加数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @PostMapping("/boardTableColumn/add")
    @BusinessLog(title = "数据字段配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardTableColumnParam.add.class) BoardTableColumnParam boardTableColumnParam) {
        boardTableColumnService.add(boardTableColumnParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据字段配置，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @PostMapping("/boardTableColumn/delete")
    @BusinessLog(title = "数据字段配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardTableColumnParam.delete.class) List<BoardTableColumnParam> boardTableColumnParamList) {
        boardTableColumnService.delete(boardTableColumnParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @PostMapping("/boardTableColumn/edit")
    @BusinessLog(title = "数据字段配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardTableColumnParam.edit.class) BoardTableColumnParam boardTableColumnParam) {
        boardTableColumnService.edit(boardTableColumnParam);
        return new SuccessResponseData();
    }

    /**
     * 查看数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @GetMapping("/boardTableColumn/detail")
    @BusinessLog(title = "数据字段配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardTableColumnParam.detail.class) BoardTableColumnParam boardTableColumnParam) {
        return new SuccessResponseData(boardTableColumnService.detail(boardTableColumnParam));
    }

    /**
     * 数据字段配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @GetMapping("/boardTableColumn/list")
    @BusinessLog(title = "数据字段配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardTableColumnParam boardTableColumnParam) {
        return new SuccessResponseData(boardTableColumnService.list(boardTableColumnParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @GetMapping("/boardTableColumn/export")
    @BusinessLog(title = "数据字段配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardTableColumnParam boardTableColumnParam) {
        boardTableColumnService.export(boardTableColumnParam);
    }

    /**
     * 同步数据表字段
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    @Permission
    @PostMapping("/boardTableColumn/sync")
    @BusinessLog(title = "数据表字段_同步", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData sync(@RequestBody BoardTableColumnParam boardTableColumnParam) {
        boardTableColumnService.sync(boardTableColumnParam);
        return new SuccessResponseData();
    }
}
