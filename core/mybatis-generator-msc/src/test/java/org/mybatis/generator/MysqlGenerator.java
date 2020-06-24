package org.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

public class MysqlGenerator {

    public static void main(String args[]) throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String xml = "/scripts/generatorConfig-mysql.xml";
//        File configFile = new File(xml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(MysqlGeneratorTest.class.getResourceAsStream(xml));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);


    }

}
