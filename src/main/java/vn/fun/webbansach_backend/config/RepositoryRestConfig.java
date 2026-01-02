package vn.fun.webbansach_backend.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> config.exposeIdsFor(
                entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new)));
    }
}
