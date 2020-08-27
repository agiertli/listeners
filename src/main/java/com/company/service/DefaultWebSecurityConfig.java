package com.company.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.kie.api.task.UserGroupCallback;
import org.kie.internal.identity.IdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration("kieServerSecurity")
@EnableWebSecurity
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(DefaultWebSecurityConfig.class);

	@PostConstruct
	public void init() {
		logger.info("Setting local deployment strategy");
		System.setProperty("org.kie.server.startup.strategy", "LocalContainersStartupStrategy");
//		logger.info("Setting controller user/password");
		System.setProperty("org.kie.server.controller.user", "kieserver");
		System.setProperty("org.kie.server.controller.password", "kieserver1!");
		System.setProperty("org.kie.server.bypass.auth.user", "true");

	}

	@Bean
	public UserGroupCallback userGroupCallback() {
		return new SimpleUserGroupCallback();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/rest/*").authenticated().and().httpBasic()
				.and().headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password("user").roles("kie-server");
		auth.inMemoryAuthentication().withUser("anton").password("password1!").roles("kie-server", "rest-all");
		auth.inMemoryAuthentication().withUser("wbadmin").password("wbadmin").roles("admin");
		auth.inMemoryAuthentication().withUser("kieserver").password("kieserver1!").roles("kie-server", "rest-all");
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
				HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name()));
		corsConfiguration.applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	
    @Bean
    public IdentityProvider identityProvider() {

        return new IdentityProvider() {

            private List<String> roles = Arrays.asList("kie-server", "Administrators");

            @Override
            public boolean hasRole(String arg0) {
                return true;
            }

            @Override
            public List<String> getRoles() {
                return roles;
            }

            @Override
            public String getName() {
                return "kieserver";
            }

        };
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
