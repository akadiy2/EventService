package com.projects.EventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/")
public class EventController {

    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    private final EventRepository repository;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value  = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        LOG.info("Getting all users.");
        return repository.findAll();
    }

    @Transactional
    @RequestMapping(value  = "/events", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addEvent(@RequestBody Event event) {
        LOG.info("Add a user...");
            repository.save(event);
    }

    @Transactional
    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateEvent(@RequestBody Event event, @PathVariable("id") String id) {
        LOG.info("Add a user...");
        try {
            Event existingEvent = repository.findById(id).orElse(null);
            if (existingEvent != null) {
                LOG.info(String.format("Retrieved Event with id: %s and name: %s, description: %s", existingEvent.getId(),
                        existingEvent.getName(), existingEvent.getDescription()));

                existingEvent.setName(event.getName());
                existingEvent.setDescription(event.getDescription());
                Event updatedEvent = repository.save(existingEvent);
                assert updatedEvent.getId().equals(existingEvent.getId());
                LOG.info(String.format("Updating Event with id: %s with new data (name: %s, description: %s)", updatedEvent.getId(),
                        updatedEvent.getName(), updatedEvent.getDescription()));
            } else {
                repository.save(event);
            }
        } catch (Exception e) {
            LOG.error(String.format("There was an issue updating the Event with id: %s", id), e);
        }
    }


    @Transactional
    @RequestMapping(value  = "/events/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable("id") String id) {
        LOG.info("Deleting event: " + id);
        repository.deleteById(id);
    }

}