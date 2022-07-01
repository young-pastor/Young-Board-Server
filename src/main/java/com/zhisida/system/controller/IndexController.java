
package com.zhisida.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.core.consts.CommonConstant;

/**
 * 首页控制器
 *
 * @author Young-Pastor
 */
@RestController
public class IndexController {

    /**
     * 访问首页，提示语
     *
     * @author Young-Pastor
     */
    @RequestMapping("/")
    public String index() {
        return CommonConstant.INDEX_TIPS;
    }
}
