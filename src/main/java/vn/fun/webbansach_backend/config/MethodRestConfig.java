package vn.fun.webbansach_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.TheLoai;

@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {

    private String url = "http://localhost:3000";

    @Autowired
    private EntityManager entityManager;

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] chanCacPhuongThuc = {
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
        };
        config.exposeIdsFor(
                entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
        // expose ids cho phép id khi trả về tất cả json
        // config.exposeIdsFor(
        // entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
        // expose ids cho 1 class cụ thể
        // config.exposeIdsFor(TheLoai.class);

        // disableHttpMethods(TheLoai.class, config, chanCacPhuongThuc);
        // HttpMethod[] phuongThucDelete = { HttpMethod.DELETE };
        // // Người dùng không thể xóa user
        // disableHttpMethods(NguoiDung.class, config, phuongThucDelete);

        // CORS configuration
        // Cho phép FE kết nối BE với các phương thức như
        cors.addMapping("/**")
                .allowedOrigins(url)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    private void disableHttpMethods(Class c,
            RepositoryRestConfiguration configuration,
            HttpMethod[] methods) {
        configuration
                .getExposureConfiguration()
                .forDomainType(c)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }
}
