package com.minicloud.authentication.config;

import com.minicloud.authentication.service.MiniCloudUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Author alan.wang
 */

@Configuration
@EnableWebSecurity
public class MiniCloudSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * @desc:对本web 服务的url拦截设置
     * 这里开放了所有/oauth/** 的权限，因为是框架的路径
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/token/login").loginProcessingUrl("/token/form")
                .and().logout()
                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .and().authorizeRequests().antMatchers("/token/**", "/actuator/**", "/mobile/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }

    /**
     * @desc:注入默认的AuthenticationManager bean
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




    /**
     * @desc:注入默认的UserDetailsService bean 并添加两个测试用户
     * username：用户名
     * password：密码，同样注意标明{加密方式}密文
     * roles：角色，可以实多个
     */
    @Bean
    public UserDetailsService userDetailsService() {

//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.builder().username("user1").password("{bcrypt}" + new BCryptPasswordEncoder().encode("123")).roles("USER").build());
//        manager.createUser(User.builder().username("admin").password("{bcrypt}" + new BCryptPasswordEncoder().encode("123")).roles("USER", "ADMIN").build());
        return new MiniCloudUserDetailServiceImpl();
    }

    /**
     * @desc:注入 PasswordEncoder 加密器
     * PasswordEncoderFactories.createDelegatingPasswordEncoder() 默认为bcrypt 加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
