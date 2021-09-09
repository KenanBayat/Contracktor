package de.contracktor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfigs {


	@Configuration
	@Order(1)
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		UserDetailsServiceH2 userDetailsServiceH2;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceH2);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
					.antMatchers("/api/**").authenticated()
					.antMatchers("/register").permitAll()
					.antMatchers("/h2-console/**").permitAll()
					.antMatchers("/**").permitAll()
					.and()
					.formLogin()
					.and()
					.httpBasic();

			http.csrf().disable();

		}

		@Configuration
		public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter{

		}
		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder();
		}


	}

}