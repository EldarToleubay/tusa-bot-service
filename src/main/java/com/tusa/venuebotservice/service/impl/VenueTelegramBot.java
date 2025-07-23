package com.tusa.venuebotservice.service.impl;


import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.VenueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
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
        } else if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());

        }
    }

    private void showVenuesWithButtons(String chatId) {
        List<Venue> venues = venueService.findAllVenues();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Venue venue : venues) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(venue.getName());
            button.setCallbackData("VENUE_" + venue.getId()); // например: VENUE_5

            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            rows.add(row);
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбери заведение:");
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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

    private void handleCallback(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData(); // например "VENUE_5"
        String chatId = callbackQuery.getMessage().getChatId().toString();

        if (callbackData.startsWith("VENUE_")) {
            Long venueId = Long.parseLong(callbackData.substring(6)); // извлекаем id

            Venue venue = venueService.findVenueById(venueId); // ты должен реализовать этот метод
            if (venue != null) {
                sendMessage(chatId, "Layout заведения \"" + venue.getName() + "\":\n" + venue.getAddress());
            } else {
                sendMessage(chatId, "Заведение не найдено.");
            }
        }

        // optionally, acknowledge callback
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        try {
            execute(answer);
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
