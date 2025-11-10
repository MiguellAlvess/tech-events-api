package com.br.miguelalves.tech_events_api.domain.address;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.br.miguelalves.tech_events_api.domain.event.Event;


@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private UUID id;
    private String city; 
    private String uf;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
