package com.br.miguelalves.tech_events_api.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.miguelalves.tech_events_api.domain.address.Address;
import com.br.miguelalves.tech_events_api.domain.event.Event;
import com.br.miguelalves.tech_events_api.domain.event.EventRequestDTO;
import com.br.miguelalves.tech_events_api.repositories.AddressRepository;

public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);
        return addressRepository.save(address);
    }
}
