package site.zhangerfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:sensitive.properties")
@SpringBootApplication
public class HkbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HkbbsApplication.class, args);
    }

}
