package com.koi.ecommerce.config;

import com.koi.ecommerce.entity.Country;
import com.koi.ecommerce.entity.Product;
import com.koi.ecommerce.entity.ProductCategory;
import com.koi.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };

        disableHttpMethod(ProductCategory.class, config, unsupportedActions);
//        disableHttpMethod(Product.class, config, unsupportedActions);
        disableHttpMethod(Country.class, config, unsupportedActions);
        disableHttpMethod(State.class, config, unsupportedActions);

        config.exposeIdsFor(arrayOfEntities());
    }

    private void disableHttpMethod(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedAction) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedAction))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedAction));
    }

    private Class<?>[] arrayOfEntities() {
        // Get a list of all entity classes from metadata
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // Create an array of the entity types
        List<Class<?>> entityClasses = new ArrayList<>();

        // Get the entity type
        for (EntityType<?> entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        // Convert to array and return
        return entityClasses.toArray(new Class[0]);
    }
}
