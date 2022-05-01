package headache.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		// パスワードの暗号化
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// セキュリティ設定を、無視するパス
		web.ignoring().antMatchers("/css/**", "/img/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	    .authorizeRequests()
        .antMatchers("/", "/login","/NewUser").permitAll() // 全ユーザーアクセス許可
        .anyRequest().authenticated();  // それ以外は全て認証無しの場合アクセス不許可
      
	        
	        
	    http.formLogin()
	        .loginPage("/") 
	        .loginProcessingUrl("/signIn") //フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
	        .usernameParameter("username") //リクエストパラメータのname属性を明示
	        .passwordParameter("password") 
	        .defaultSuccessUrl("/signIn",true)// 認証成功時の遷移先
	        .failureUrl("/login?error")
	        .permitAll()
	        .and()
	    .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/?logout")
	        .permitAll();
	}

}