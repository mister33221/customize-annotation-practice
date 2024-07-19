package com.kai.customize_annotation_practice.interceptor;

import com.kai.customize_annotation_practice.annotation.RequireJwt;
import com.kai.customize_annotation_practice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequireJwt requireJwt = handlerMethod.getMethodAnnotation(RequireJwt.class);

        if (requireJwt != null) {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token found in request headers");
                return false;
            }

            token = token.substring(7);
            if (!jwtUtil.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return false;
            }
        }

        return true;
    }
}
