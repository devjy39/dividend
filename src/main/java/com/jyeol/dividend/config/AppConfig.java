package com.jyeol.dividend.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*
    *   싱글톤으로 관리하기 위해 빈 등록
    * */
    @Bean
    public Trie<String, String> trie() {
        return new PatriciaTrie<>();
    }
}
