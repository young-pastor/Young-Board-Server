
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardDataSourceParam;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.service.BoardDataSourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源配置表控制器
 *
 * @author Young-Pastor
 * @date 2022-06-17 15:08:24
 */
@RestController
public class BoardDataSourceController {

    @Resource
    private BoardDataSourceService boardDataSourceService;

    /**
     * 查询数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @GetMapping("/boardDataSource/page")
    @BusinessLog(title = "数据源配置表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardDataSourceParam boardDataSourceParam) {
        return new SuccessResponseData(boardDataSourceService.page(boardDataSourceParam));
    }

    /**
     * 添加数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @PostMapping("/boardDataSource/add")
    @BusinessLog(title = "数据源配置表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardDataSourceParam.add.class) BoardDataSourceParam boardDataSourceParam) {
        boardDataSourceService.add(boardDataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据源配置表，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @PostMapping("/boardDataSource/delete")
    @BusinessLog(title = "数据源配置表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardDataSourceParam.delete.class) List<BoardDataSourceParam> boardDataSourceParamList) {
        boardDataSourceService.delete(boardDataSourceParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @PostMapping("/boardDataSource/edit")
    @BusinessLog(title = "数据源配置表_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardDataSourceParam.edit.class) BoardDataSourceParam boardDataSourceParam) {
        boardDataSourceService.edit(boardDataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 查看数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @GetMapping("/boardDataSource/detail")
    @BusinessLog(title = "数据源配置表_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardDataSourceParam.detail.class) BoardDataSourceParam boardDataSourceParam) {
        return new SuccessResponseData(boardDataSourceService.detail(boardDataSourceParam));
    }

    /**
     * 数据源配置表列表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @GetMapping("/boardDataSource/list")
    @BusinessLog(title = "数据源配置表_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardDataSourceParam boardDataSourceParam) {
        return new SuccessResponseData(boardDataSourceService.list(boardDataSourceParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    @Permission
    @GetMapping("/boardDataSource/export")
    @BusinessLog(title = "数据源配置表_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardDataSourceParam boardDataSourceParam) {
        boardDataSourceService.export(boardDataSourceParam);
    }

}
