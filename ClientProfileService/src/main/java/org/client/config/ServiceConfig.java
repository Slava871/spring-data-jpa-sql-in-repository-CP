package org.client.config;


import org.client.dto.IndividualDto;
import org.client.repo.IndividualRepo;
import org.client.service.AddressService;
import org.client.service.IndividualService;
import org.client.service.impl.AddressServiceImpl;
import org.client.service.impl.IndividualServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    public IndividualService individualService() {

        return new IndividualServiceImpl();
    }



    @Bean
    public AddressService addressService(IndividualService individualService) {
        return new AddressServiceImpl(individualService);
    }

}
