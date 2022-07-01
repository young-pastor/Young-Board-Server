
package com.zhisida.core.listener;

import com.zhisida.core.context.param.RequestParamContext;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * 用来清除临时缓存的@RequestBody的请求参数
 *
 * @author Young-Pastor
 */
public class RemoveRequestParamListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        RequestParamContext.clear();
    }

}
