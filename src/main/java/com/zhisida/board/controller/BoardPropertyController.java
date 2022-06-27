
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardPropertyParam;
import com.zhisida.board.service.BoardPropertyService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 属性配置控制器
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:25
 */
@RestController
public class BoardPropertyController {

    @Resource
    private BoardPropertyService boardPropertyService;

    @Permission
    @GetMapping("/boardProperty/tree")
    @BusinessLog(title = "属性配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree(BoardPropertyParam boardPropertyParam) {
        return new SuccessResponseData(boardPropertyService.tree(boardPropertyParam));
    }
    /**
     * 查询属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @GetMapping("/boardProperty/page")
    @BusinessLog(title = "属性配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardPropertyParam boardPropertyParam) {
        return new SuccessResponseData(boardPropertyService.page(boardPropertyParam));
    }

    /**
     * 添加属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @PostMapping("/boardProperty/add")
    @BusinessLog(title = "属性配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardPropertyParam.add.class) BoardPropertyParam boardPropertyParam) {
            boardPropertyService.add(boardPropertyParam);
        return new SuccessResponseData();
    }

    /**
     * 删除属性配置，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @PostMapping("/boardProperty/delete")
    @BusinessLog(title = "属性配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardPropertyParam.delete.class) List<BoardPropertyParam> boardPropertyParamList) {
            boardPropertyService.delete(boardPropertyParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @PostMapping("/boardProperty/edit")
    @BusinessLog(title = "属性配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardPropertyParam.edit.class) BoardPropertyParam boardPropertyParam) {
            boardPropertyService.edit(boardPropertyParam);
        return new SuccessResponseData();
    }

    /**
     * 查看属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @GetMapping("/boardProperty/detail")
    @BusinessLog(title = "属性配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardPropertyParam.detail.class) BoardPropertyParam boardPropertyParam) {
        return new SuccessResponseData(boardPropertyService.detail(boardPropertyParam));
    }

    /**
     * 属性配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @GetMapping("/boardProperty/list")
    @BusinessLog(title = "属性配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardPropertyParam boardPropertyParam) {
        return new SuccessResponseData(boardPropertyService.list(boardPropertyParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    @Permission
    @GetMapping("/boardProperty/export")
    @BusinessLog(title = "属性配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardPropertyParam boardPropertyParam) {
        boardPropertyService.export(boardPropertyParam);
    }

}
