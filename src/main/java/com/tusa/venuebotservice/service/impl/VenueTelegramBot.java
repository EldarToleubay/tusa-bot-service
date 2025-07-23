package com.tusa.venuebotservice.service.impl;

import com.tusa.venuebotservice.dto.PlaceDto;
import com.tusa.venuebotservice.dto.PlaceStatusDto;
import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.PlaceService;
import com.tusa.venuebotservice.service.PlaceStatusService;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VenueTelegramBot extends TelegramLongPollingBot {

    @Value("${telegrambots.botUsername}")
    private String botUsername;

    @Value("${telegrambots.botToken}")
    private String botToken;

    private final VenueService venueService;
    private final PlaceService placeService;
    private final PlaceStatusService placeStatusService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            switch (message) {
                case "/start" -> sendMessage(chatId, "Привет! Я помогу тебе найти заведение.");
                case "/venues" -> showVenuesWithButtons(chatId);
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
            button.setCallbackData("VENUE_" + venue.getId());

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

    private void handleCallback(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();

        try {
            if (callbackData.startsWith("VENUE_")) {
                Long venueId = Long.parseLong(callbackData.substring(6));
                Venue venue = venueService.findVenueById(venueId);

                if (venue != null) {
                    sendMessage(chatId, "Заведение: " + venue.getName() + "\nАдрес: " + venue.getAddress());

                    List<PlaceDto> places = placeService.getAllPlace(venueId);
                    List<List<InlineKeyboardButton>> rows = new ArrayList<>();

                    for (PlaceDto place : places) {
                        if (Boolean.TRUE.equals(place.getActive())) {
                            InlineKeyboardButton btn = new InlineKeyboardButton();
                            btn.setText(place.getLabel());
                            btn.setCallbackData("PLACE_" + place.getId() + "_VENUE_" + venueId);
                            rows.add(List.of(btn));
                        }
                    }

                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    markup.setKeyboard(rows);

                    SendMessage placeMsg = new SendMessage();
                    placeMsg.setChatId(chatId);
                    placeMsg.setText("Выберите стол для бронирования:");
                    placeMsg.setReplyMarkup(markup);

                    execute(placeMsg);
                } else {
                    sendMessage(chatId, "Заведение не найдено.");
                }

            } else if (callbackData.startsWith("PLACE_")) {
                String[] parts = callbackData.split("_");
                Long placeId = Long.parseLong(parts[1]);
                Long venueId = Long.parseLong(parts[3]);

                String timeSlot = LocalTime.now().withSecond(0).withNano(0).toString();

                PlaceStatusDto statusDto = placeStatusService.setPlaceStatus(venueId, placeId, timeSlot);

                StringBuilder sb = new StringBuilder();
                sb.append("✅ Бронь выполнена!\n");
                sb.append("Стол: ").append(placeId).append("\n");
                sb.append("Заведение ID: ").append(venueId).append("\n");
                sb.append("Дата: ").append(statusDto.getDate()).append("\n");
                sb.append("Время: ").append(statusDto.getTimeSlot()).append("\n");
                sb.append("Статус: ").append(statusDto.getStatus());

                sendMessage(chatId, sb.toString());
            }

            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(callbackQuery.getId());
            execute(answer);

        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(chatId, "Произошла ошибка при обработке запроса.");
        }
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
