package com.kai.customize_annotation_practice.config;

import com.kai.customize_annotation_practice.interceptor.JwtInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
/*
WebMvcConfigurer 它提供了一系列的回調方法（callback methods），
允許開發者對 Spring MVC 的配置進行細微的調整和擴展，
而不需要完全替換或修改現有的配置。

配置視圖解析器 (configureViewResolvers): 允許開發者自定義視圖解析的方式，從而控制如何將視圖名稱映射到具體的視圖實現上。
添加靜態資源處理器 (addResourceHandlers): 用於指定靜態資源的位置和如何處理它們，例如圖片、CSS 和 JavaScript 文件。
添加攔截器 (addInterceptors): 提供了一種方式來添加自定義攔截器，這些攔截器可以在請求處理之前或之後執行特定的操作。
配置跨域請求 (addCorsMappings): 允許為應用配置跨源資源共享（CORS）策略，從而控制哪些跨域請求是被允許的。
配置內容協商 (configureContentNegotiation): 使開發者能夠自定義內容協商的策略，以支援不同的媒體類型。
添加格式化器和轉換器 (addFormatters): 允許添加自定義的格式化器和轉換器，用於數據綁定和類型轉換。
配置異步支持 (configureAsyncSupport): 提供了配置異步請求處理的選項，例如設置默認的超時時間或自定義 Callable 和 DeferredResult 的處理方式。
 */
public class WebMvcConfig implements WebMvcConfigurer {

    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        把我們自製的攔截器加入到攔截器鏈中
        registry.addInterceptor(jwtInterceptor);
    }
}
