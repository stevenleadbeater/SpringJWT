package boot;

/**
 * Created by steven on 26/08/16.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"mappers", "controllers", "repository", "configuration"})
public class BootConfiguration {
    public static void main(String[] args) {SpringApplication.run(BootConfiguration.class, args);}
}
