package cn.com.lz.generator.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@MapperScan(basePackages = "cn.com.lz.generator.backstage.dao")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
public class BackstageServerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackstageServerApiApplication.class, args);
  }

//  @Bean
//  public Executor asyncExecutor() {
//    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//    executor.setCorePoolSize(5);
//    executor.setMaxPoolSize(10);
//    executor.setQueueCapacity(50);
//    executor.initialize();
//    return executor;
//  }

}
