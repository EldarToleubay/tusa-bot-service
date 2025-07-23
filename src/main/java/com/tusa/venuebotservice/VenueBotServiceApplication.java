package com.tusa.venuebotservice;

import com.tusa.venuebotservice.service.impl.VenueTelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableFeignClients
public class VenueBotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VenueBotServiceApplication.class, args);
    }


    @Bean
    public TelegramBotsApi telegramBotsApi(VenueTelegramBot bot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api;
    }



}
