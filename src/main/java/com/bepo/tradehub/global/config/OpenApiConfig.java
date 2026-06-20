package com.bepo.tradehub.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tradeHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TradeHub API")
                        .description("중고 상품 등록과 거래 예약을 관리하는 TradeHub REST API 문서")
                        .version("v1"));
    }
}
