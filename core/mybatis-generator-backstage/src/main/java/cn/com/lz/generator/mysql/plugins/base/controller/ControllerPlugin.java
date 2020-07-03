/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package cn.com.lz.generator.mysql.plugins.base.controller;

import io.swagger.annotations.Api;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.DomainObjectRenamingRule;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成 BaseServicePlugin
 */
public class ControllerPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean controllerGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        //引入注解类
        String tbName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        String path = getControllerPath(introspectedTable,tbName);
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation("@RequestMapping(value = \""+path+"\")");
        // swagger 注解 @Api(value = "/reg", tags = "预约信息")
        topLevelClass.addAnnotation("@Api(value = \""+path+"\", tags = \""+getTags(introspectedTable.getRemarks())+"\")");
        String swgApi = "io.swagger.annotations.Api";
        FullyQualifiedJavaType impSwgApi = new FullyQualifiedJavaType(swgApi);
        topLevelClass.addImportedType(impSwgApi);
        String restController = "org.springframework.web.bind.annotation.RestController";
        FullyQualifiedJavaType impRestController = new FullyQualifiedJavaType(restController);
        topLevelClass.addImportedType(impRestController);
        String restMapping = "org.springframework.web.bind.annotation.RequestMapping";
        FullyQualifiedJavaType impRestMapping = new FullyQualifiedJavaType(restMapping);
        topLevelClass.addImportedType(impRestMapping);

        //继承 BaseController
        String voImport = introspectedTable.getVoRecordType();
        String reqImport = introspectedTable.getReqRecordType();
        String queryImport = introspectedTable.getQueryRecordType();
        StringBuilder baseServiceImpl = new StringBuilder("BaseController<");
        baseServiceImpl.append(voImport.substring(voImport.lastIndexOf(".")+1));
        baseServiceImpl.append(",");
        baseServiceImpl.append(reqImport.substring(reqImport.lastIndexOf(".")+1));
        baseServiceImpl.append(",");
        baseServiceImpl.append(queryImport.substring(queryImport.lastIndexOf(".")+1));
        baseServiceImpl.append(">");
        FullyQualifiedJavaType clsBaseServiceImpl = new FullyQualifiedJavaType(baseServiceImpl.toString());
        topLevelClass.setSuperClass(clsBaseServiceImpl);
        //类引入BaseServiceImpl
        String baseServiceImplImport = "cn.com.lz.generator.mysql.plugins.base.controller.BaseController";
        FullyQualifiedJavaType impBaseServiceImpl = new FullyQualifiedJavaType(baseServiceImplImport);
        topLevelClass.addImportedType(impBaseServiceImpl);
        //类引入vo
        FullyQualifiedJavaType impVo = new FullyQualifiedJavaType(voImport);
        topLevelClass.addImportedType(impVo);
        //类引入req
        FullyQualifiedJavaType impReq = new FullyQualifiedJavaType(reqImport);
        topLevelClass.addImportedType(impReq);
        //类引入query
        FullyQualifiedJavaType impQuery = new FullyQualifiedJavaType(queryImport);
        topLevelClass.addImportedType(impQuery);

        //添加属性 BaseMapper
        String baseService = introspectedTable.getMyBatis3JavaServiceType();
        //类引入Service
        FullyQualifiedJavaType impService = new FullyQualifiedJavaType(baseService);
        topLevelClass.addImportedType(impService);
        //获得名称
        String service = getFieldName(baseService,true) + "Impl";
        FullyQualifiedJavaType listOfCriterion = new FullyQualifiedJavaType(
                getFieldName(baseService,false));
        Field field = new Field(service, listOfCriterion);
        field.setVisibility(JavaVisibility.PROTECTED);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        //引入org.springframework.beans.factory.annotation.Autowired
        String autowired = "org.springframework.beans.factory.annotation.Autowired";
        FullyQualifiedJavaType impAutowired = new FullyQualifiedJavaType(autowired);
        topLevelClass.addImportedType(impAutowired);

        // 添加方法 afterPropertiesSet
        Method method = new Method("afterPropertiesSet");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@Override");
        method.addBodyLine("super.baseService = "+service+";");
        topLevelClass.addMethod(method);

        return true;
    }

    private String getFieldName(String field,boolean toLower){
        String shotName = field.substring(field.lastIndexOf(".")+1);
        if(toLower){
            char[] chars =shotName.toCharArray();
                    chars[0] = Character.toLowerCase(chars[0]);
            shotName = String.valueOf(chars);
        }

        return shotName;
    }

    private String getPath(String tbName){
        StringBuilder sb = new StringBuilder();

        boolean nextPath = true;
        for (int i = 0; i < tbName.length(); i++) {
            char c = tbName.charAt(i);
            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    nextPath = true;
                    break;

                default:
                    if (nextPath) {
                        sb.append("/");
                        sb.append(Character.toLowerCase(c));
                        nextPath = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        return sb.toString();
    }

    private String getControllerPath(IntrospectedTable introspectedTable,String tbName){
        DomainObjectRenamingRule domainObjectRenamingRule = introspectedTable.getTableConfiguration().getDomainObjectRenamingRule();
        String path = "";
        if (domainObjectRenamingRule != null) {
            Pattern pattern = Pattern.compile(domainObjectRenamingRule.getSearchString().toLowerCase());
            String replaceString = domainObjectRenamingRule.getReplaceString();
            replaceString = replaceString == null ? "" : replaceString; //$NON-NLS-1$
            Matcher matcher = pattern.matcher(tbName);
            path = getPath(matcher.replaceAll(replaceString));
        }
        return path;
    }

    private String getTags(String remarks){
        String replaceChars[] = new String[]{"表"};
        for (String repChar : replaceChars) {
            remarks = remarks.replace(repChar,"");
        }
        return remarks;
    }

}
