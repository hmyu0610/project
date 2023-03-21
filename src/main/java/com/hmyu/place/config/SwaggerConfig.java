package com.hmyu.place.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * Desc : SWAGGER2 설정을 위한 클래스
 */
@Configuration
@EnableSwagger2
@Profile({"local"})
public class SwaggerConfig implements WebMvcConfigurer {
    private static final String BASE_PACKAGE_PATH = "com.hmyu.place";

    private String title;
    private String version;

    /**
     * Desc : SWAGGER html Document 설정
     */
    @Bean
    public Docket api() {
        version = "v1";
        title = "Hmyu API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(title, version))
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_PATH))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .protocols(getProtocols())
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));	// 토큰 검증 설정

    }

    /**
     * Desc : SWAGGER html Document에 출력될 서비스정보 설정
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(title, "Hmyu API Docs Page", version, "",
                new Contact("유혜미", "", "hmyu@kakao.com"), "", "", new ArrayList<>());
    }

    /**
     * Desc : 전역에서 사용될 헤더 토큰 정보 설정
     */
    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
    }

    /**
     * Desc : 보안 컨텍스트 설정
     */
    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    /**
     * Desc : 기본 인증 설정
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
    }

    /**
     * Desc : 프로토콜 설정
     */
    private Set<String> getProtocols(){
        Set<String> protocols = new HashSet<String>();
        protocols.add("HTTP");
//        protocols.add("HTTPS");

        return protocols;
    }
}
