package com.br.miguelalves.tech_events_api.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.miguelalves.tech_events_api.domain.event.Event;
import com.br.miguelalves.tech_events_api.domain.event.EventRequestDTO;

@Service
public class EventService {
    public Event createEvent(EventRequestDTO data) {
        String imageUrl = null;
        if(data.imageUrl() != null) {
           imageUrl = this.uploadImage(data.imageUrl());
        }
        Event newEvent  = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()).toString());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setRemote(data.remote());
        newEvent.setImageUrl(imageUrl);
        return newEvent;
    }

    public String uploadImage(MultipartFile imageFile) {
        return "";
    }
}
