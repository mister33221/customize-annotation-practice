package com.kai.customize_annotation_practice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;  // 這是給用在 @OpenAPIDefinition 裡面的 Info
import io.swagger.v3.oas.annotations.security.SecurityScheme; // 這是給用在 @SecuritySchemes 裡面的 SecurityScheme
//import io.swagger.v3.oas.models.info.Info;  // 這是給用在 public OpenAPI customOpenAPI() 裡面的 Info
//import io.swagger.v3.oas.models.security.SecurityScheme; // 這是給用在 public OpenAPI customOpenAPI() 裡面的 SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes;


import org.springframework.context.annotation.Configuration;

/*
@Configuration注解用於定義配置類，
這些配置類充當了傳統XML配置文件的替代品。
使用Java配置允許開發者以更加類型安全和重構友好的方式來配置Spring容器。
@Configuration注解的類會由Spring在啟動時自動掃描和解析，
以便構造和配置應用程序所需的Bean。
使用@Configuration
當一個類被標記為@Configuration時，
它表示該類包含一個或多個@Bean注解的方法。
Spring容器會調用這些方法以獲取並註冊Bean實例。
這些方法在運行時由Spring自動執行，以生成定義的Bean
*/
@Configuration
/*
openapi 的相關設定可以參考這裡
https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations#OpenAPIDefinition
以下就簡單說明一下這兩個 annotation 的用途
 */
/*
@OpenAPIDefinition 是用來定義 OpenAPI 的 metadata 的，
 */
@OpenAPIDefinition(
        info = @Info(
                title = "JWT Authentication API",
                version = "1.0",
                description = "API for JWT token generation and protected resources"
        )
)
/*
@SecuritySchemes 是用來定義 OpenAPI 的 security scheme 的，
這邊定義了一個名為 bearer-jwt 的 security scheme，
你定義了這個之後，在 swagger ui 右上角就會有一個可以輸入 token 的地方，
且在這邊我們有設定會在你輸入你的 JWT token 後，在JWT token 前面加上 bearer 這個字串，
 */
@SecuritySchemes({
        @SecurityScheme(
                name = "bearer-jwt",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
public class OpenApiConfig {
/*
上面跟下面的程式碼是一樣的，擇一使用即可。
如果你連到 swagger 的頁面時有出現錯誤，
java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter
at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641) ~[na:na]
at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188) ~[na:na]
可以試試看以下兩個其中一個 dependency
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>2.3.2</version>
</dependency>
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
*/
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")))
//                .info(new Info()
//                        .title("JWT Authentication API")
//                        .version("1.0")
//                        .description("API for JWT token generation and protected resources"));
//    }
}
