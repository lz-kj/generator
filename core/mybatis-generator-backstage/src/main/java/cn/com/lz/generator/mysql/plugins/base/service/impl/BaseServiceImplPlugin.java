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
package cn.com.lz.generator.mysql.plugins.base.service.impl;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成 BaseServicePlugin
 */
public class BaseServiceImplPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean serviceImplGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        //继承 BaseServiceImpl
        String modelImport = introspectedTable.getBaseRecordType();
        StringBuilder baseServiceImpl = new StringBuilder("BaseServiceImpl<");
        baseServiceImpl.append(modelImport.substring(modelImport.lastIndexOf(".")+1));
        baseServiceImpl.append(">");
        FullyQualifiedJavaType clsBaseServiceImpl = new FullyQualifiedJavaType(baseServiceImpl.toString());
        topLevelClass.setSuperClass(clsBaseServiceImpl);
        //类引入BaseServiceImpl
        String baseServiceImplImport = "cn.com.lz.generator.mysql.plugins.base.service.impl.BaseServiceImpl";
        FullyQualifiedJavaType impBaseServiceImpl = new FullyQualifiedJavaType(baseServiceImplImport);
        topLevelClass.addImportedType(impBaseServiceImpl);
        //类引入model
        FullyQualifiedJavaType impModel = new FullyQualifiedJavaType(modelImport);
        topLevelClass.addImportedType(impModel);

        //实现接口
        String service = introspectedTable.getMyBatis3JavaServiceType();
        FullyQualifiedJavaType clsBaseMapper = new FullyQualifiedJavaType(service.substring(service.lastIndexOf(".")+1));
        topLevelClass.addSuperInterface(clsBaseMapper);
        FullyQualifiedJavaType impBaseMapper = new FullyQualifiedJavaType(service);
        topLevelClass.addImportedType(impBaseMapper);


        //添加属性 BaseMapper
        String baseMapper = introspectedTable.getMyBatis3JavaMapperType();
        //类引入Mapper
        FullyQualifiedJavaType impMapper = new FullyQualifiedJavaType(baseMapper);
        topLevelClass.addImportedType(impMapper);
        //获得名称
        String mapper = getFieldName(baseMapper,true);
        FullyQualifiedJavaType listOfCriterion = new FullyQualifiedJavaType(
                getFieldName(baseMapper,false));
        Field field = new Field(mapper, listOfCriterion);
        field.setVisibility(JavaVisibility.PROTECTED);
        topLevelClass.addField(field);

        // 添加方法 afterPropertiesSet
        String model = introspectedTable.getBaseRecordType();
        String modelName = getFieldName(model,false);
        String example = introspectedTable.getExampleType();
        String exampleName = getFieldName(example,false);
        String vo = introspectedTable.getVoRecordType();
        String voName = getFieldName(vo,false);
        Method method = new Method("afterPropertiesSet");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addBodyLine("super.setMevt(this."+mapper+","+modelName+".class, "+
                exampleName+".class, "+exampleName+".Criteria.class, "+voName+".class);");
        topLevelClass.addMethod(method);
        //类引入Example
        FullyQualifiedJavaType impExample = new FullyQualifiedJavaType(example);
        topLevelClass.addImportedType(impExample);


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
