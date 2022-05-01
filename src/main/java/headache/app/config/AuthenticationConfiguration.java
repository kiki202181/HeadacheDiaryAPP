package headache.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import headache.app.service.UserDetailsServiceImpl;

@Configuration class AuthenticationConfiguration
extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // 認証するユーザーを設定する
        auth.userDetailsService(userDetailsService)
        // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
        .passwordEncoder(new BCryptPasswordEncoder());

    }
}