package com.wzu.interceptor;

import com.alibaba.fastjson2.JSON;
import com.wzu.common.utils.JwtUtil;
import com.wzu.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token  = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证： " + token);
        if(token != null){
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt无效，请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        return false; // 拦截
    }

//    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 检查JWT令牌是否存在
//        String jwtToken = request.getHeader("Authorization");
//        if (jwtToken == null) {
//            // 用户未登录
//
//            // 检查是否为注册页面的请求
//            String requestURI = request.getRequestURI();
//            System.out.println(requestURI);
//            if (requestURI.equals("/register")) {
//                // 用户访问注册页面，允许继续访问
//                chain.doFilter(request, response);
//            } else {
//                // 非注册页面的请求，重定向到登录页面或返回错误提示
//                response.sendRedirect("/login"); // 重定向到登录页面
//                // 或者返回错误提示
//                // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                // response.getWriter().write("Please login first.");
//            }
//        } else {
//            // 用户已登录，继续处理其他请求
//            // ...
//            chain.doFilter(request, response);
//        }
//    }
}
