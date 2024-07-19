package com.kai.customize_annotation_practice.interceptor;

import com.kai.customize_annotation_practice.annotation.RequireJwt;
import com.kai.customize_annotation_practice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/*
HandlerInterceptor接口用於攔截處理器的執行。
可以在控制器（Controller）處理請求之前、之後以及完成請求處理後（即渲染視圖之後）進行自定義的操作。
這對於實現如日誌記錄、身份驗證、授權等跨切面功能非常有用。

HandlerInterceptor接口定義了三個方法：

preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)：
在控制器處理請求之前調用。如果返回true，則繼續向下執行（到下一個攔截器或處理器）；如果返回false，則中斷執行流程。
postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)：
在控制器處理請求之後、渲染視圖之前調用。
afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)：
在完成請求處理後調用，這時可以進行資源清理等操作。
 */
@Component
@AllArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*
這邊在確認 handler Object 是否是 HandlerMethod 的 instance，
但為什麼要做這樣的確認?
因為 HandlerMethod 是一個 Spring MVC 的類別，
它包含了一個方法的所有資訊，包括方法名稱、參數、返回值等等。
如果 handler 不是 HandlerMethod 的 instance，
那就代表這個請求可能不是映射到Controller中的一個方法，
而是其他類型的請求（例如對靜態資源的請求），則直接返回true，
表示不對這類請求進行後續的攔截處理。
 */
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
/*
從 handlerMethod 中取得方法上的 RequireJwt annotation，
 */
        RequireJwt requireJwt = handlerMethod.getMethodAnnotation(RequireJwt.class);

        if (requireJwt != null) {
//            從 Headers 中取得 Authorization 的值
            String token = request.getHeader("Authorization");
//            如果 Authorization 的值不存在或不是以 Bearer 開頭，則回傳 401
            if (token == null || !token.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token found in request headers");
                return false;
            }

//            取得 token 的值，去掉 Bearer 字串並對其進行各種驗證
            token = token.substring(7);
            if (!jwtUtil.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return false;
            }
        }

        return true;
    }
}
