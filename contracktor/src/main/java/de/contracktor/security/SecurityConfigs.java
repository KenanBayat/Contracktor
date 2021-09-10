package de.contracktor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfigs {

	@Autowired
	private UserDetailsServiceH2 userDetailsServiceH2;

	@Configuration
	@Order(1)
	public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceH2);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/api/login").permitAll()
					.antMatchers("/api/**").hasAuthority("USER");

			http.csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			APITokenFilter apiFilter = new APITokenFilter(authenticationManagerBean());
			http.addFilter(apiFilter);
		}
	}

	@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

			// Comment in to enable H2 console on test server (not recommended for release version!)
			//http.csrf().disable();
			//http.headers().frameOptions().disable();
		}

		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder();
		}


	}

}
