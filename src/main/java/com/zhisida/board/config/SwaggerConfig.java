
package com.zhisida.board.config;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import com.zhisida.board.core.consts.CommonConstant;

import java.util.List;

/**
 * swagger配置
 *
 * @author young-pastor
 * 加入分组功能(默认注释掉)
 * <p>
 * https://doc.xiaominfo.com/knife4j/changelog/2017-12-18-swagger-bootstrap-ui-1.7-issue.html
 * </p>
 * @author young-pastor
 **/
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    private List<Parameter> getParameters() {
        Parameter parameter = new ParameterBuilder()
                .name("Authorization")
                .description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> parameters = CollectionUtil.newArrayList();
        parameters.add(parameter);
        return parameters;
    }

    @Bean
    public Docket defaultApi() {
        List<Parameter> parameters = getParameters();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(defaultApiInfo())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage(CommonConstant.DEFAULT_PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo defaultApiInfo() {
        return new ApiInfoBuilder()
                .title("Young-Board Doc")
                .description("Young-Board Doc文档")
                .termsOfServiceUrl("https://board.zhisida.com")
                .contact(new Contact("young-pastor", "https://board.zhisida.com", ""))
                .version("1.0")
                .build();
    }

    /**
     * 想分组请放开注释
     */

    // @Bean
    // public Docket groupRestApi() {
    //     List<Parameter> parameters = getParameters();
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .apiInfo(groupApiInfo())
    //             .groupName("自定义")
    //             .select()
    //             //TODO 这里改为自己的包名
    //             .apis(RequestHandlerSelectors.basePackage("com.example.XXX"))
    //             .paths(PathSelectors.any())
    //             .build()
    //             .globalOperationParameters(parameters);
    // }
    //
    // private ApiInfo groupApiInfo() {
    //     return new ApiInfoBuilder()
    //             .title("自定义")
    //             .description("自定义API")
    //             .termsOfServiceUrl("http://www.example.com/")
    //             .version("1.0")
    //             .build();
    // }

}
