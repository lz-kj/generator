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

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

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
        topLevelClass.addField(field);

        // 添加方法 afterPropertiesSet
        Method method = new Method("afterPropertiesSet");
        method.setVisibility(JavaVisibility.PUBLIC);
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

}
