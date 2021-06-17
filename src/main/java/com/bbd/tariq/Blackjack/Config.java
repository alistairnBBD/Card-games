package com.bbd.tariq.Blackjack;

import com.bbd.tariq.Blackjack.Interfaces.IBlackjackService;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IHiloService;
import com.bbd.tariq.Blackjack.Interfaces.IPileFactory;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Interfaces.IRummyService;
import com.bbd.tariq.Blackjack.Strategies.BlackjackServiceStrategy;
import com.bbd.tariq.Blackjack.Strategies.CardsApiStrategy;
import com.bbd.tariq.Blackjack.Strategies.HiloServiceStrategy;
import com.bbd.tariq.Blackjack.Strategies.PileFactoryStrategy;
import com.bbd.tariq.Blackjack.Strategies.RepoFactoryStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bbd.tariq.Blackjack.Strategies.RummyServiceStrategy;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ComponentScan("com.bbd.tariq.blackjack")
public class Config {

    private static final IPileFactory _pileFactory = new PileFactoryStrategy();

    //"Register" the Interface and Concrete class
    @Bean
    public ICardsApi cardsApi() {
        return new CardsApiStrategy(pileFactory());
    }


    @Bean
    public IRepoFactory repoFactory() {
        return new RepoFactoryStrategy();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public IPileFactory pileFactory() {return _pileFactory;}

    @Bean
    public IBlackjackService blackjackService() { return new BlackjackServiceStrategy(cardsApi(),repoFactory());}

    @Bean
    public IRummyService rummyService() { return new RummyServiceStrategy(cardsApi(),repoFactory());}

    @Bean
    public IHiloService hiloService() { return new HiloServiceStrategy(cardsApi(), repoFactory());}

}
