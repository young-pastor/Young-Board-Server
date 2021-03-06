
package com.zhisida.board.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.cache.BoardTableColumnCache;
import com.zhisida.board.param.BoardEventParam;
import com.zhisida.board.service.BoardEventService;
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
import java.util.stream.Collectors;

/**
 * 元事件配置控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:17
 */
@RestController
public class BoardEventController {

    @Resource
    private BoardEventService boardEventService;

    @Resource
    BoardTableColumnCache boardTableColumnCache;

    @Permission
    @GetMapping("/boardEvent/autoCreate")
    @BusinessLog(title = "元事件配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData autoCreate(BoardEventParam boardEventParam) {
        boardEventService.autoCreate(boardEventParam);
        return ResponseData.success();
    }

    @Permission
    @GetMapping("/boardEvent/tree")
    @BusinessLog(title = "元事件配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree(BoardEventParam boardEventParam) {
        return ResponseData.success(boardEventService.tree(boardEventParam));
    }

    /**
     * 查询元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @GetMapping("/boardEvent/page")
    @BusinessLog(title = "元事件配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardEventParam boardEventParam) {
        return ResponseData.success(boardEventService.page(boardEventParam));
    }

    /**
     * 添加元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @PostMapping("/boardEvent/add")
    @BusinessLog(title = "元事件配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardEventParam.add.class) BoardEventParam boardEventParam) {
        boardEventService.add(boardEventParam);
        return ResponseData.success();
    }

    /**
     * 删除元事件配置，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @PostMapping("/boardEvent/delete")
    @BusinessLog(title = "元事件配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardEventParam.delete.class) List<BoardEventParam> boardEventParamList) {
        boardEventService.delete(boardEventParamList);
        return ResponseData.success();
    }

    /**
     * 编辑元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @PostMapping("/boardEvent/edit")
    @BusinessLog(title = "元事件配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardEventParam.edit.class) BoardEventParam boardEventParam) {
        boardEventService.edit(boardEventParam);
        return ResponseData.success();
    }

    /**
     * 查看元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @GetMapping("/boardEvent/detail")
    @BusinessLog(title = "元事件配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardEventParam.detail.class) BoardEventParam boardEventParam) {
        return ResponseData.success(boardEventService.detail(boardEventParam));
    }

    /**
     * 元事件配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @GetMapping("/boardEvent/list")
    @BusinessLog(title = "元事件配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardEventParam boardEventParam) {
        return ResponseData.success(boardEventService.list(boardEventParam));
    }


    @Permission
    @GetMapping("/boardEvent/analysisList")
    @BusinessLog(title = "元事件配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData analysisList(BoardEventParam boardEventParam) {
        List<BoardEventParam> eventParams = boardEventService.list(boardEventParam).stream().map(e -> {
            BoardEventParam eventParam = new BoardEventParam();
            BeanUtil.copyProperties(e, eventParam);
            eventParam.setColumn(boardTableColumnCache.getTableColumnParamById(eventParam.getTableColumnId()));
            return eventParam;
        }).collect(Collectors.toList());
        return ResponseData.success(eventParams);
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    @Permission
    @GetMapping("/boardEvent/export")
    @BusinessLog(title = "元事件配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardEventParam boardEventParam) {
        boardEventService.export(boardEventParam);
    }

}
