package config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;

@MapperScan("dao") // 设置mapper接口扫描包
public class MyBatisConfig {

    // 需要注入的对象（连接池）在Spring容器中，因此写在形参后将根据类型自动装配
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 为session（jdbc连接）工厂bean绑定jdbc连接池
        factoryBean.setDataSource(dataSource);
        // 设置pojo类扫描包，mybatis会将数据打包封装到pojo对象中
        factoryBean.setTypeAliasesPackage("pojo");
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));//配置Mapper映射文件的路径
        return factoryBean;
    }
}
