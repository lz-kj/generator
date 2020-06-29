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
package cn.com.lz.generator.mysql.plugins.base.service;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

import java.sql.Types;
import java.util.List;

/**
 * 生成 BaseServicePlugin
 */
public class BaseServicePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean serviceGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

        StringBuilder baseService = new StringBuilder("BaseService<Vo, Req, Query, ");
        String modelImport = introspectedTable.getBaseRecordType();
        baseService.append(modelImport.substring(modelImport.lastIndexOf(".")+1));
        baseService.append(">");
        //类创建
        FullyQualifiedJavaType clsBaseMapper = new FullyQualifiedJavaType(baseService.toString());
        interfaze.addSuperInterface(clsBaseMapper);
        //类引入
        FullyQualifiedJavaType impModel = new FullyQualifiedJavaType(modelImport);
        interfaze.addImportedType(impModel);
        String baseServiceImport = "cn.com.lz.generator.mysql.plugins.base.service.BaseService";
        FullyQualifiedJavaType impBaseMapper = new FullyQualifiedJavaType(baseServiceImport);
        interfaze.addImportedType(impBaseMapper);
        String voImport = "cn.com.lz.generator.mysql.plugins.base.controller.vo.Vo";
        FullyQualifiedJavaType impVo = new FullyQualifiedJavaType(voImport);
        interfaze.addImportedType(impVo);
        String reqImport = "cn.com.lz.generator.mysql.plugins.base.controller.req.Req";
        FullyQualifiedJavaType impReq = new FullyQualifiedJavaType(reqImport);
        interfaze.addImportedType(impReq);
        String queryImport = "cn.com.lz.generator.mysql.plugins.base.controller.query.Query";
        FullyQualifiedJavaType impQuery = new FullyQualifiedJavaType(queryImport);
        interfaze.addImportedType(impQuery);
        //去掉方法
        interfaze.getMethods().clear();
        //去掉注解
        interfaze.getAnnotations().clear();
        return true;
    }

}
