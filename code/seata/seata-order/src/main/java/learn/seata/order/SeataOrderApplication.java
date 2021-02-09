package learn.seata.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: $desc
 * @author: deific
 * @createDate: 2021-02-09 18:23
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan("learn.seata.order.services.dao")
@EnableFeignClients(basePackages = {"learn.seata.order.feign"})
public class SeataOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication.class, args);
    }
}