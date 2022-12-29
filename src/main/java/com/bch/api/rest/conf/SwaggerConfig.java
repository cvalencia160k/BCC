package com.bch.api.rest.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration 
@OpenAPIDefinition(
       info = @Info(
              title = "API BCH Banco Compensador", 
              version = "1.0", 
              description = "Banco de Chile - Banco Compensador"
              ))
public class SwaggerConfig {

 /**************************************************************************
  * Nombre funcion: Docket.................................................*
  * Action: ejecutar swagger...............................................*
  * Inp:...................................................................*
    * Out:...................................................................*
  **************************************************************************/
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
           .select()
           .apis(RequestHandlerSelectors.basePackage("com.bch.api.rest"))
           .paths(PathSelectors.any())
           .build();
    }
}