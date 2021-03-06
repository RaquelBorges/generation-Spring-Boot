package com.exercicio.exercicio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailService; // injeção de dependencia de uma 
												//classe que existe dentro de websecurity
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailService);
	}
	
	@Bean
	public PasswordEncoder passwordEnconder ()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure (HttpSecurity http) throws Exception 
	{
		http.authorizeRequests().antMatchers("/usuarios/logar").permitAll() //libera end point sem token
								.antMatchers("/usuarios/cadastrar").permitAll()//libera end point sem token
								.anyRequest().authenticated() //todas as outras requisições deverão ser autenticadas
								.and().httpBasic()
								.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //não guarda sessão
								.and().cors()
								.and().csrf().disable();
	}
	

}
