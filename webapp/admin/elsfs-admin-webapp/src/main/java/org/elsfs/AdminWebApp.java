package org.elsfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan("org.elsfs.admin.infrastructure.repository.mybatis")
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@ComponentScan(basePackages = { "org.elsfs.admin" })
public class AdminWebApp {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(AdminWebApp.class, args);

        // SysPermissionDB db = new SysPermissionDB();
        // db.setParentId("0");
        // db.setPath("/permission");
        // db.setName("Permission");
        // db.setComponent("LAYOUT");
        // db.setRedirect("/permission/front/page");
        // db.setIcon("carbon:user-role");
        //
        // db.setHidden(true);
        // db.setTitle("routes.demo.permission.permission");
        // db.setMenuType(0);
        // db.setPermissionId("2");
        // repository.insert(db);
    }

}