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
package org.mybatis.generator.plugins.base.mapper;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 生成 BaseMapper
 */
public class BaseMapperPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

        StringBuilder baseMapper = new StringBuilder("BaseMapper<");
        baseMapper.append(introspectedTable.getBaseRecordType());
        baseMapper.append(",");
        baseMapper.append(introspectedTable.getExampleType());
        baseMapper.append(",");
        int dbType = introspectedTable.getPrimaryKeyColumns().get(0).getJdbcType();
        switch (dbType){
            case Types.BIGINT:
                baseMapper.append(Long.class.getSimpleName());
                String longImport = Long.class.getName();
                FullyQualifiedJavaType longType = new FullyQualifiedJavaType(longImport);
                interfaze.addImportedType(longType);
                break;
            case Types.INTEGER:
                baseMapper.append(Integer.class.getSimpleName());
                String intImport = Long.class.getName();
                FullyQualifiedJavaType intType = new FullyQualifiedJavaType(intImport);
                interfaze.addImportedType(intType);
                break;
            case Types.VARCHAR:
                baseMapper.append(String.class.getSimpleName());
                String strImport = Long.class.getName();
                FullyQualifiedJavaType strType = new FullyQualifiedJavaType(strImport);
                interfaze.addImportedType(strType);
                break;
        }
        baseMapper.append(">");
        String baseMapperImport = "org.mybatis.generator.plugins.base.mapper.BaseMapper";
        FullyQualifiedJavaType clsBaseMapper = new FullyQualifiedJavaType(baseMapper.toString());
        FullyQualifiedJavaType impBaseMapper = new FullyQualifiedJavaType(baseMapperImport);
        interfaze.addSuperInterface(clsBaseMapper);
        interfaze.addImportedType(impBaseMapper);
        //去掉方法
        interfaze.getMethods().clear();
        //去掉注解
        interfaze.getAnnotations().clear();
        return true;
    }

}
