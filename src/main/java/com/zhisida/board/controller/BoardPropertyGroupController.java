
package com.zhisida.board.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.param.BoardPropertyGroupParam;
import com.zhisida.board.service.BoardPropertyGroupService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 属性分组控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:37
 */
@RestController
public class BoardPropertyGroupController {

    @Resource
    private BoardPropertyGroupService boardPropertyGroupService;

    @Permission
    @GetMapping("/boardPropertyGroup/tree")
    @BusinessLog(title = "数据源配置表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree(BoardEventGroupParam boardEventGroupParam) {
        return new SuccessResponseData(boardPropertyGroupService.tree(boardEventGroupParam));
    }

    /**
     * 查询属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @GetMapping("/boardPropertyGroup/page")
    @BusinessLog(title = "属性分组_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardPropertyGroupParam boardPropertyGroupParam) {
        return new SuccessResponseData(boardPropertyGroupService.page(boardPropertyGroupParam));
    }

    /**
     * 添加属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @PostMapping("/boardPropertyGroup/add")
    @BusinessLog(title = "属性分组_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardPropertyGroupParam.add.class) BoardPropertyGroupParam boardPropertyGroupParam) {
            boardPropertyGroupService.add(boardPropertyGroupParam);
        return new SuccessResponseData();
    }

    /**
     * 删除属性分组，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @PostMapping("/boardPropertyGroup/delete")
    @BusinessLog(title = "属性分组_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardPropertyGroupParam.delete.class) List<BoardPropertyGroupParam> boardPropertyGroupParamList) {
            boardPropertyGroupService.delete(boardPropertyGroupParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @PostMapping("/boardPropertyGroup/edit")
    @BusinessLog(title = "属性分组_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardPropertyGroupParam.edit.class) BoardPropertyGroupParam boardPropertyGroupParam) {
            boardPropertyGroupService.edit(boardPropertyGroupParam);
        return new SuccessResponseData();
    }

    /**
     * 查看属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @GetMapping("/boardPropertyGroup/detail")
    @BusinessLog(title = "属性分组_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardPropertyGroupParam.detail.class) BoardPropertyGroupParam boardPropertyGroupParam) {
        return new SuccessResponseData(boardPropertyGroupService.detail(boardPropertyGroupParam));
    }

    /**
     * 属性分组列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @GetMapping("/boardPropertyGroup/list")
    @BusinessLog(title = "属性分组_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardPropertyGroupParam boardPropertyGroupParam) {
        return new SuccessResponseData(boardPropertyGroupService.list(boardPropertyGroupParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    @Permission
    @GetMapping("/boardPropertyGroup/export")
    @BusinessLog(title = "属性分组_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardPropertyGroupParam boardPropertyGroupParam) {
        boardPropertyGroupService.export(boardPropertyGroupParam);
    }

}
