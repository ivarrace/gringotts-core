package com.ivarrace.gringotts.infrastructure.config.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gringotts API",
                description = "Accountancy tracker",
                version = "v0",
                contact = @Contact(
                        name = "ivarrace",
                        url = "https://github.com/ivarrace/gringotts-core"//,
                        //email = "ivan.arraez@gmail.com"
                ),
                license = @License(
                        name = "GPLv3",
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"))
)
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP)
public class OpenApiConfig {
/*
    @Bean
    public OpenAPI springShopOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme().name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("Gringotts API")
                .description("Accounting manager")
                .version("v0");
    }*/
}
