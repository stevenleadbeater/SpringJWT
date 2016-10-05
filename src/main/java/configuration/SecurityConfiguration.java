package configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;

//import org.springframework.security.authentication.AuthenticationManager;

/**
 * Created by steven on 31/08/16.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private CustomAuthenticationProvider customAuthenticationProvider;

    /*@Inject
    private SecurityHeaderFilters securityHeaderFilters;*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/authentication");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/authentication").permitAll()
                    .anyRequest().authenticated()
        .and()
                .addFilterBefore(securityHeaderFilters(), BasicAuthenticationFilter.class);
        // @formatter:on
    }

    public SecurityHeaderFilters securityHeaderFilters() {
        return new SecurityHeaderFilters();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
