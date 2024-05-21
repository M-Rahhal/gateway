package com.streamPatform.gateway.filter;

import com.streamPatform.gateway.utils.annotations.AdminAPI;
import com.streamPatform.gateway.utils.annotations.FilterPattern;
import com.streamPatform.gateway.utils.annotations.PublicAPI;

import com.streamPatform.gateway.utils.annotations.PythonServiceAPI;
import com.streamPatform.gateway.entity.Admin;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.streamPatform.gateway.services.AuthService;

import java.io.IOException;
import java.util.Objects;

@Component
@Order(1)
@FilterPattern(pattern = {})
public class AuthFilter implements Filter {


    @Autowired
    private AuthService authService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin" , "*");

        HandlerMethod handlerMethod = null;
        try {
            RequestMappingHandlerMapping req2HandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
            HandlerExecutionChain handlerExeChain = req2HandlerMapping.getHandler(req);
            if (Objects.nonNull(handlerExeChain)) {
                handlerMethod = (HandlerMethod) handlerExeChain.getHandler();
                if (handlerMethod.getBeanType().getName().startsWith("com.streamPatform.gateway")) {
                    PublicAPI isPublic = handlerMethod.getMethod().getAnnotation(PublicAPI.class);
                    AdminAPI isAdmin = handlerMethod.getMethod().getAnnotation(AdminAPI.class);
                    PythonServiceAPI isPythonService = handlerMethod.getMethod().getAnnotation(PythonServiceAPI.class);
                    if(isPublic == null) {
                        if (isAdmin!=null){
                            String token = req.getHeader("Authorization");
                            if (token==null || token.isEmpty())
                                throw new Exception("only admins can access this api!");

                            Admin admin = authService.verifyToken(token);

                            req.setAttribute("admin" , admin);
                            chain.doFilter(request, response);
                                return;
                        }
                        else if (isPythonService != null){
                            String token = req.getHeader("Authorization");
                            if (token==null || token.isEmpty())
                                throw new Exception("only admins can access this api!");
                            Admin admin = authService.verifyToken(token);
                            req.setAttribute("python-service" , admin);
                            chain.doFilter(request, response);
                            return;
                        }
                        else {
                            res.setStatus(401);
                            res.getWriter().write("Unauthorized");
                        }
                    }
                    chain.doFilter(request, response);
                    return;
                }
            }
            throw new Exception("Couldn't find handler method");
        } catch (Exception e) {
            res.setStatus(401);
            res.getWriter().write("Unauthorized");
        }
    }
}