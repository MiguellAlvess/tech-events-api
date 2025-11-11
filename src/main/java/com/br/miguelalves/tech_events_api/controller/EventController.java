package com.br.miguelalves.tech_events_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.miguelalves.tech_events_api.domain.event.Event;
import com.br.miguelalves.tech_events_api.domain.event.EventRequestDTO;
import com.br.miguelalves.tech_events_api.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    public ResponseEntity<Event> create(@RequestBody EventRequestDTO body) {
        Event event = this.eventService.createEvent(body);
        return ResponseEntity.ok(event);
    }
}
