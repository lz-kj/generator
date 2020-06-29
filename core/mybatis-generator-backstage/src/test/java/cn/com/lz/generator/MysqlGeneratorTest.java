
package cn.com.lz.generator;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class MysqlGeneratorTest {

    @Test
    public void testCreate()throws Exception {
        try{
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            String xml = "/scripts/generatorConfig-mysql.xml";
//        File configFile = new File(xml);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(MysqlGeneratorTest.class.getResourceAsStream(xml));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSimple()throws Exception {
        try{
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            String xml = "/scripts/generatorConfig-mysql-simple.xml";
//        File configFile = new File(xml);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(MysqlGeneratorTest.class.getResourceAsStream(xml));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateMyBatis3WithInvalidConfig() throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        String xml = "/scripts/generatorConfig-mysql.xml";
        InputStream inputStream = MysqlGeneratorTest.class.getResourceAsStream(xml);
        Configuration config = cp.parseConfiguration(inputStream);

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        InvalidConfigurationException e =
                assertThrows(InvalidConfigurationException.class, () -> {
                    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
                    myBatisGenerator.generate(null, null, null, false);
                });

        assertEquals(2, e.getErrors().size());
    }



    @ParameterizedTest
    @MethodSource("generateJavaFiles")
    public void testJavaParse(GeneratedJavaFile generatedJavaFile) {
        DefaultJavaFormatter formatter = new DefaultJavaFormatter();

        ByteArrayInputStream is = new ByteArrayInputStream(
                formatter.getFormattedContent(generatedJavaFile.getCompilationUnit()).getBytes());
        try {
            StaticJavaParser.parse(is);
        } catch (ParseProblemException e) {
            fail("Generated Java File " + generatedJavaFile.getFileName() + " will not compile");
        }
    }

    public static List<GeneratedJavaFile> generateJavaFiles() throws Exception {
        List<GeneratedJavaFile> generatedFiles = new ArrayList<>();
        generatedFiles.addAll(generateJavaFilesMysql());
        return generatedFiles;
    }

    private static List<GeneratedJavaFile> generateJavaFilesMysql() throws Exception {
        return generateJavaFiles("/scripts/generatorConfig-mysql.xml");
    }

    private static List<GeneratedJavaFile> generateJavaFiles(String configFile) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(MysqlGeneratorTest.class.getResourceAsStream(configFile));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
        return myBatisGenerator.getGeneratedJavaFiles();
    }

}
