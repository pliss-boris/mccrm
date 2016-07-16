package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.Subscriber;
import com.bd.mc.crm.repository.SubscriberRepository;
import com.bd.mc.crm.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Subscriber.
 */
@RestController
@RequestMapping("/api")
public class SubscriberResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberResource.class);
        
    @Inject
    private SubscriberRepository subscriberRepository;
    
    /**
     * POST  /subscribers : Create a new subscriber.
     *
     * @param subscriber the subscriber to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriber, or with status 400 (Bad Request) if the subscriber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscribers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) throws URISyntaxException {
        log.debug("REST request to save Subscriber : {}", subscriber);
        if (subscriber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subscriber", "idexists", "A new subscriber cannot already have an ID")).body(null);
        }
        Subscriber result = subscriberRepository.save(subscriber);
        return ResponseEntity.created(new URI("/api/subscribers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subscriber", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscribers : Updates an existing subscriber.
     *
     * @param subscriber the subscriber to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriber,
     * or with status 400 (Bad Request) if the subscriber is not valid,
     * or with status 500 (Internal Server Error) if the subscriber couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscribers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) throws URISyntaxException {
        log.debug("REST request to update Subscriber : {}", subscriber);
        if (subscriber.getId() == null) {
            return createSubscriber(subscriber);
        }
        Subscriber result = subscriberRepository.save(subscriber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subscriber", subscriber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscribers : get all the subscribers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscribers in body
     */
    @RequestMapping(value = "/subscribers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Subscriber> getAllSubscribers() {
        log.debug("REST request to get all Subscribers");
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers;
    }

    /**
     * GET  /subscribers/:id : get the "id" subscriber.
     *
     * @param id the id of the subscriber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriber, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subscribers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriber> getSubscriber(@PathVariable Long id) {
        log.debug("REST request to get Subscriber : {}", id);
        Subscriber subscriber = subscriberRepository.findOne(id);
        return Optional.ofNullable(subscriber)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subscribers/:id : delete the "id" subscriber.
     *
     * @param id the id of the subscriber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subscribers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        log.debug("REST request to delete Subscriber : {}", id);
        subscriberRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subscriber", id.toString())).build();
    }

}
