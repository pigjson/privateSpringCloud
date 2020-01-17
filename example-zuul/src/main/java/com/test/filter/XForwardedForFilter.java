package com.test.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class XForwardedForFilter extends ZuulFilter {
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("------------>ZuulException");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String remoteAddr = request.getRemoteAddr();
        ctx.getZuulRequestHeaders().put(HTTP_X_FORWARDED_FOR, remoteAddr);
        return null;
    }
}
