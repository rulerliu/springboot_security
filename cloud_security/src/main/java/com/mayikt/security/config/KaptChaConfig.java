package com.mayikt.security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class KaptChaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        Properties properties=new Properties();
        properties.put("kaptcha.width","150");
        properties.put("kaptcha.height","42");
        properties.put("kaptcha.border","no");
        properties.put("kaptcha.textproducer.font.size","40");
        properties.put("kaptcha.textproducer.char.space","10");
        properties.put("kaptcha.textproducer.font.names","微软雅黑");
        properties.put("kaptcha.textproducer.char.string","1234567890");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.background.clear.from","92,189,170");
        properties.put("kaptcha.background.clear.to","255,255,255");
        Config config=new Config(properties);
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
