
package com.zhisida.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.core.consts.CommonConstant;

/**
 * 首页控制器
 *
 * @author young-pastor
 */
@RestController
public class IndexController {

    /**
     * 访问首页，提示语
     *
     * @author young-pastor
     */
    @RequestMapping("/")
    public String index() {
        return CommonConstant.INDEX_TIPS;
    }
}