package com.br.miguelalves.tech_events_api.repositories;
import com.br.miguelalves.tech_events_api.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;  


public interface EventRepository extends JpaRepository<Event, UUID> {}
