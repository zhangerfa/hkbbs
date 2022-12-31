package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"dao", "service"})
@PropertySource("classpath:jdbc.properties") // 导入jdbc配置文件
@Import({JdbcConfig.class, MyBatisConfig.class}) // 导入jdbc和mybatis配置类
@EnableTransactionManagement // 开启Spring事务管理
public class SpringConfig {
}
