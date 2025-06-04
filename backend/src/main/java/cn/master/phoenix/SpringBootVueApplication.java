package cn.master.phoenix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.master.phoenix.mapper")
public class SpringBootVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootVueApplication.class, args);
    }

}
