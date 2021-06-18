package com.bbd.tariq.Blackjack;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Interfaces.IObserver;
import com.bbd.tariq.Blackjack.Interfaces.ISubject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableSwagger2
public class BlackjackApplication implements ISubject {

	public static ArrayList<IObserver> _observers = new ArrayList<>();

	public static void main(String[] args) {

		SpringApplication.run(BlackjackApplication.class, args);

	}


	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bbd.tariq.Blackjack")).build();}

	@Override
	public void AddObserver(IObserver observer) {
		_observers.add(observer);
	}

	@Override
	@EventListener(ApplicationReadyEvent.class)
	public void UpdateObservers() {

		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				_observers.forEach(observer -> observer.update());
			}
		}, Constants.Schedules.SECURITY_SERVICE_JWT_REFRESH, Constants.Schedules.SECURITY_SERVICE_JWT_REFRESH);

	}
}
