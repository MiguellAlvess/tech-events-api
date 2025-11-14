package com.br.miguelalves.tech_events_api.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.miguelalves.tech_events_api.domain.event.Event;
import com.br.miguelalves.tech_events_api.domain.event.EventDetailsDTO;
import com.br.miguelalves.tech_events_api.domain.event.EventRequestDTO;
import com.br.miguelalves.tech_events_api.domain.event.EventResponseDTO;
import com.br.miguelalves.tech_events_api.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("date") Long date,
            @RequestParam("city") String city,
            @RequestParam("uf") String uf,
            @RequestParam("remote") Boolean remote,
            @RequestParam("eventUrl") String eventUrl,
            @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl) {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, uf, remote, eventUrl,
                imageUrl);
        Event event = eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable UUID eventId) {
        EventDetailsDTO eventDetails = eventService.getEventDetails(eventId);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam int size) {
        List<EventResponseDTO> events = eventService.getUpComingEvents(page, size);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String city,
            @RequestParam String uf,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<EventResponseDTO> events = eventService.getFilteredEvents(page, size, city, uf, startDate, endDate);
        return ResponseEntity.ok(events);
    }
}
