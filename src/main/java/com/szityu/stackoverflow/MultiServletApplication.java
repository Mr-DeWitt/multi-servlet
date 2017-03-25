package com.szityu.stackoverflow;

import com.szityu.stackoverflow.config.ServletAConfig;
import com.szityu.stackoverflow.config.ServletBConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = "com.szityu.stackoverflow.parent")
public class MultiServletApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultiServletApplication.class, args);
	}

    @Bean
    public ServletRegistrationBean servletARegistration(MultipartConfigElement mutlipart) {
        return createServletRegistrationForConfig(ServletAConfig.class, "/servletA", "servlet-A", 1, mutlipart);
    }

    @Bean
    public ServletRegistrationBean servletBRegistration(MultipartConfigElement mutlipart) {
        return createServletRegistrationForConfig(ServletBConfig.class, "/servletB", "servlet-B", 2, mutlipart);
    }

    private ServletRegistrationBean createServletRegistrationForConfig(Class servletConfig, String contextPath, String name, int loadOnStartup, MultipartConfigElement mutlipartConfig) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(servletConfig);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setApplicationContext(context);

        ServletRegistrationBean registration = new ServletRegistrationBean(
                servlet, contextPath);

        registration.setMultipartConfig(mutlipartConfig);
        registration.setLoadOnStartup(loadOnStartup);
        registration.setName(name);

        return registration;
    }

}
