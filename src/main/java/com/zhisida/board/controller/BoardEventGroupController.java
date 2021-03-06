
package com.zhisida.board.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.service.BoardEventGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 元事件分组控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@RestController
public class BoardEventGroupController {

    @Resource
    private BoardEventGroupService boardEventGroupService;

    @Permission
    @GetMapping("/boardEventGroup/tree")
    @BusinessLog(title = "元事件分组_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree(BoardEventGroupParam boardEventGroupParam) {
        return ResponseData.success(boardEventGroupService.tree(boardEventGroupParam));
    }

    @Permission
    @GetMapping("/boardEventGroup/page")
    @BusinessLog(title = "元事件分组_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardEventGroupParam boardEventGroupParam) {
        return ResponseData.success(boardEventGroupService.page(boardEventGroupParam));
    }

    /**
     * 添加元事件分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @PostMapping("/boardEventGroup/add")
    @BusinessLog(title = "元事件分组_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardEventGroupParam.add.class) BoardEventGroupParam boardEventGroupParam) {
        boardEventGroupService.add(boardEventGroupParam);
        return ResponseData.success();
    }

    /**
     * 删除元事件分组，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @PostMapping("/boardEventGroup/delete")
    @BusinessLog(title = "元事件分组_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardEventGroupParam.delete.class) List<BoardEventGroupParam> boardEventGroupParamList) {
        boardEventGroupService.delete(boardEventGroupParamList);
        return ResponseData.success();
    }

    /**
     * 编辑元事件分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @PostMapping("/boardEventGroup/edit")
    @BusinessLog(title = "元事件分组_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardEventGroupParam.edit.class) BoardEventGroupParam boardEventGroupParam) {
        boardEventGroupService.edit(boardEventGroupParam);
        return ResponseData.success();
    }

    /**
     * 查看元事件分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @GetMapping("/boardEventGroup/detail")
    @BusinessLog(title = "元事件分组_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardEventGroupParam.detail.class) BoardEventGroupParam boardEventGroupParam) {
        return ResponseData.success(boardEventGroupService.detail(boardEventGroupParam));
    }

    /**
     * 元事件分组列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @GetMapping("/boardEventGroup/list")
    @BusinessLog(title = "元事件分组_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardEventGroupParam boardEventGroupParam) {
        return ResponseData.success(boardEventGroupService.list(boardEventGroupParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    @Permission
    @GetMapping("/boardEventGroup/export")
    @BusinessLog(title = "元事件分组_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardEventGroupParam boardEventGroupParam) {
        boardEventGroupService.export(boardEventGroupParam);
    }

}
