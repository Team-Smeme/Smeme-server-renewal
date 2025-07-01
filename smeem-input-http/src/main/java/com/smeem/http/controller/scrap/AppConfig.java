package com.smeem.http.controller.scrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register resource handler for images
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}
//
//	@Beanc
//	public SpringResourceTemplateResolver templateResolver(){
//	// SpringResourceTemplateResolver automatically integrates with Spring's own
//	// resource resolution infrastructure, which is highly recommended.
//	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//	templateResolver.setPrefix("classpath:templates/");
//	templateResolver.setSuffix(".html");
//	templateResolver.setCharacterEncoding("UTF-8");
//	// HTML is the default value, added here for the sake of clarity.
//	templateResolver.setTemplateMode(TemplateMode.HTML);
//	// Template cache is true by default. Set to false if you want
//	// templates to be automatically updated when modified.
//	templateResolver.setCacheable(true);
//	return templateResolver;
//	}
//
//	@Bean
//	public SpringTemplateEngine templateEngine(){
//	// SpringTemplateEngine automatically applies SpringStandardDialect and
//	// enables Spring's own MessageSource message resolution mechanisms.
//	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//	templateEngine.setTemplateResolver(templateResolver());
//	// Enabling the SpringEL compiler with Spring 4.2.4 or newer can
//	// speed up execution in most scenarios, but might be incompatible
//	// with specific cases when expressions in one template are reused
//	// across different data types, so this flag is "false" by default
//	// for safer backwards compatibility.
//	templateEngine.setEnableSpringELCompiler(true);
//	return templateEngine;
//	}
//
//	@Bean
//	public ThymeleafViewResolver viewResolver(){
//	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	viewResolver.setTemplateEngine(templateEngine());
//	// NOTE 'order' and 'viewNames' are optional
//	viewResolver.setOrder(1);
//	viewResolver.setViewNames(new String[] {".html", ".xhtml"});
//	return viewResolver;
//	}
}
