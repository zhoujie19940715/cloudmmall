package com.zj.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;//序号越小越靠前
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *  填写过滤的逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        //这里可以从url中哪，也可以从cookie,header中获取
//        String token = request.getParameter("token");
//        if(StringUtils.isEmpty(token)){
//            currentContext.setSendZuulResponse(false);
//            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//        }
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest httpServletRequest = currentContext.getRequest();
//        //todo ①拿到token
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        //todo ②判断是否登陆，登陆，重新设置token的有效期，没有登陆不用管
//        if(StringUtils.isNotEmpty(loginToken)){
//            //判断logintoken是否为空或者""；
//            //如果不为空的话，符合条件，继续拿user信息
//            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//            User user = JsonUtil.string2Obj(userJsonStr,User.class);
//            if(user != null){
//                //todo ③如果user不为空，则重置session的时间，即调用expire命令
//                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
//            }
//        }
         RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        request.getRequestURI();
        return null;
    }
}
