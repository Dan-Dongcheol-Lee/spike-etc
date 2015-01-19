import domain.DomainDelete;
import domain.DomainDeleteRepository;
import domain.DomainDeleteSum;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTest.SimpleConfiguration.class)
public class RepositoryTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    @Autowired
    private DomainDeleteRepository repository;

    @Test
    public void shouldGetSome() {

        givenDomain("domain1", new Date());
        givenDomain("domain2", new Date());
        givenDomain("domain3", new Date());
        givenDomain("domain4", new Date());

        List<DomainDeleteSum> list = repository.getSum();

//        List<Object[]> list = repository.getSum2();

        System.out.println(list);
    }

    private void givenDomain(String domainName, Date createdOn) {
        repository.saveAndFlush(new DomainDelete(domainName, dateFormat.format(createdOn)));
    }

    @Configuration
    @EnableJpaRepositories("domain")
    public static class SimpleConfiguration {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
            return new JpaTransactionManager(emf);
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
            jpaVendorAdapter.setGenerateDdl(true);
            jpaVendorAdapter.setShowSql(true);
            return jpaVendorAdapter;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
            lemfb.setDataSource(dataSource());
            lemfb.setJpaVendorAdapter(jpaVendorAdapter());
            Map<String, Object> map = new HashMap<>();
            map.put(Environment.DIALECT, "domain.ExtendedH2Dialect");
            lemfb.setJpaPropertyMap(map);
            lemfb.setPackagesToScan("domain");
            return lemfb;
        }
    }
}
