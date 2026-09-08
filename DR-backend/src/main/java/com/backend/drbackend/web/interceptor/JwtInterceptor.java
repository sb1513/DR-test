package com.backend.drbackend.web.interceptor;

import com.backend.drbackend.utils.JWTutil;
import com.backend.drbackend.web.R;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTutil jwtutil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){return true;}
        String jwt = request.getHeader("Authorization");
        int user_id;
        try{
            Claims claims = jwtutil.parseToken(jwt);
            user_id = (int) claims.get("user_id",Integer.class);
            request.setAttribute("user_id",user_id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print("{\"code\":5001,\"msg\":\"凭证错误，请稍后重试\",\"data\":null}");
            return false;
        }
    }
}
