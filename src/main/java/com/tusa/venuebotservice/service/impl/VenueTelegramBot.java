package com.tusa.venuebotservice.service.impl;


import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.VenueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VenueTelegramBot extends TelegramLongPollingBot {

    @Value("${telegrambots.botUsername}")
    private String botUsername;

    @Value("${telegrambots.botToken}")
    private String botToken;

    private final VenueService venueService; // твой сервис

    public VenueTelegramBot(VenueService venueService) {
        this.venueService = venueService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            switch (message) {
                case "/start" -> sendMessage(chatId, "Привет! Я помогу тебе найти заведение.");
                case "/venues" -> showVenues(chatId);
                default -> sendMessage(chatId, "Неизвестная команда.");
            }
        }
    }

    private void showVenues(String chatId) {
        List<Venue> venues = venueService.findAllVenues();
        String text = venues.stream()
                .map(v -> v.getId() + ": " + v.getName())
                .collect(Collectors.joining("\n"));
        sendMessage(chatId, "Список заведений:\n" + text);
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
