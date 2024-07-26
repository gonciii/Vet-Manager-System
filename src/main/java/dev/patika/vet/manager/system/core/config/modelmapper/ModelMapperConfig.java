package dev.patika.vet.manager.system.core.config.modelmapper;


import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    // @bean anatasyonu :  // program başladığında bu bean ıos container içerisine nesneyi atıcak !
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
