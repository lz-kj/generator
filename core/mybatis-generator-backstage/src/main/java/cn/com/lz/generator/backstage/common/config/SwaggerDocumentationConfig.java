package cn.com.lz.generator.backstage.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfig {

    @Bean
    public Docket manageApi() {
        //可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                //参数类型支持header, cookie, body, query etc
                .parameterType("header")
                //参数名
                .name("token")
                .description("header中token字段")
                //指定参数值的类型
                .modelRef(new ModelRef("string"))
                //非必需，这里是全局配置，然而在登陆的时候是不用验证的
                .required(false).build();
        List<Parameter> aParameters = new ArrayList<>(16);
//        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).groupName("后台接口")
                .select().apis(RequestHandlerSelectors.basePackage("cn.com.lz.generator.backstage.controller"))
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .pathProvider(new BasePathAwareRelativePathProvider("/backstage" ))
                .globalOperationParameters(aParameters);

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "自动生成API",
                "自动生成API.",
                "1.0",
                "http://自动生成",
                new Contact("侯东生", "自动生成", "houdongsheng@rtmap.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}

class BasePathAwareRelativePathProvider extends AbstractPathProvider {
    private String basePath;

    public BasePathAwareRelativePathProvider(String basePath) {
        this.basePath = basePath;
    }

    @Override
    protected String applicationPath() {
        return basePath;
    }

    @Override
    protected String getDocumentationPath() {
        return "/";
    }

    @Override
    public String getOperationPath(String operationPath) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
        return Paths.removeAdjacentForwardSlashes(
                uriComponentsBuilder.path(operationPath.replaceFirst(basePath, "")).build().toString());
    }
}
