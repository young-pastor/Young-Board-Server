
package com.zhisida.board.core.filter.xss;


import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.cache.SysConfigCache;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * xss过滤器
 *
 * @author Young-Pastor
 */
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();

        // 获取不进行url过滤的接口
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        List<String> unXssFilterUrl = sysConfigCache.getUnXssFilterUrl();
        if (unXssFilterUrl != null && unXssFilterUrl.contains(servletPath)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        }
    }

}
