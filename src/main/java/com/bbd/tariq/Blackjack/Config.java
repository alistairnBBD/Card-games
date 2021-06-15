package com.bbd.tariq.Blackjack;

import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Strategies.CardsApiStrategy;
import com.bbd.tariq.Blackjack.Strategies.RepoFactoryStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bbd.tariq.blackjack")
public class Config {

    //"Register" the Interface and Concrete class
    @Bean
    public ICardsApi cardsApi() {
        return new CardsApiStrategy();
    }


    @Bean
    public IRepoFactory repoFactory() {
        return new RepoFactoryStrategy();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
