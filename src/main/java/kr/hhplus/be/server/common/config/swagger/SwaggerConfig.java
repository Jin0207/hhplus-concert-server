package kr.hhplus.be.server.common.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("콘서트 예약 시스템 API")
                        .version("1.0.0")
                        .description("콘서트 예약 시스템의 API 명세입니다."))
                .addSecurityItem(new SecurityRequirement().addList("Queue-Token"))
                .components(new Components()
                        .addSecuritySchemes("Queue-Token",
                                new SecurityScheme()
                                        .name("Queue-Token")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)));
    }
}
