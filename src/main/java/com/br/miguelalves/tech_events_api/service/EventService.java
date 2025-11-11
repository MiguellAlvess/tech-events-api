package com.br.miguelalves.tech_events_api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.br.miguelalves.tech_events_api.domain.event.Event;
import com.br.miguelalves.tech_events_api.domain.event.EventRequestDTO;
import com.br.miguelalves.tech_events_api.domain.event.EventResponseDTO;
import com.br.miguelalves.tech_events_api.repositories.EventRepository;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;

    public Event createEvent(EventRequestDTO data) {
        String imageUrl = null;
        if (data.imageUrl() != null) {
            imageUrl = uploadImage(data.imageUrl());
        }
        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()).toString());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setRemote(data.remote());
        newEvent.setImageUrl(imageUrl);
        eventRepository.save(newEvent);
        if (!data.remote()) {
            addressService.createAddress(data, newEvent);
        }
        return newEvent;
    }

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {
            File file = convertMultiPartToFile(multipartFile);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public List<EventResponseDTO> getUpComingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findUpComingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(),
                event.getTitle(),
                event.getDescription(),
                new Date(event.getDate()),
                event.getAddress().getCity() != null ? event.getAddress().getCity() : "",
                event.getAddress().getUf() != null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImageUrl()))
                .stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date startDate,
            Date endDate) {
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = this.eventRepository.findFilteredEvents(city, uf, startDate, endDate,
                pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                new Date(event.getDate()),
                event.getAddress().getCity() != null ? event.getAddress().getCity() : "",
                event.getAddress().getUf() != null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImageUrl()))
                .stream().toList();
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws Exception {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

}
