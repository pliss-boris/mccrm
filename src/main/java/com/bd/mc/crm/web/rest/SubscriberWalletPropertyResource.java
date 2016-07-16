package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.SubscriberWalletProperty;
import com.bd.mc.crm.repository.SubscriberWalletPropertyRepository;
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
 * REST controller for managing SubscriberWalletProperty.
 */
@RestController
@RequestMapping("/api")
public class SubscriberWalletPropertyResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberWalletPropertyResource.class);
        
    @Inject
    private SubscriberWalletPropertyRepository subscriberWalletPropertyRepository;
    
    /**
     * POST  /subscriber-wallet-properties : Create a new subscriberWalletProperty.
     *
     * @param subscriberWalletProperty the subscriberWalletProperty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriberWalletProperty, or with status 400 (Bad Request) if the subscriberWalletProperty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscriber-wallet-properties",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWalletProperty> createSubscriberWalletProperty(@RequestBody SubscriberWalletProperty subscriberWalletProperty) throws URISyntaxException {
        log.debug("REST request to save SubscriberWalletProperty : {}", subscriberWalletProperty);
        if (subscriberWalletProperty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subscriberWalletProperty", "idexists", "A new subscriberWalletProperty cannot already have an ID")).body(null);
        }
        SubscriberWalletProperty result = subscriberWalletPropertyRepository.save(subscriberWalletProperty);
        return ResponseEntity.created(new URI("/api/subscriber-wallet-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subscriberWalletProperty", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscriber-wallet-properties : Updates an existing subscriberWalletProperty.
     *
     * @param subscriberWalletProperty the subscriberWalletProperty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriberWalletProperty,
     * or with status 400 (Bad Request) if the subscriberWalletProperty is not valid,
     * or with status 500 (Internal Server Error) if the subscriberWalletProperty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscriber-wallet-properties",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWalletProperty> updateSubscriberWalletProperty(@RequestBody SubscriberWalletProperty subscriberWalletProperty) throws URISyntaxException {
        log.debug("REST request to update SubscriberWalletProperty : {}", subscriberWalletProperty);
        if (subscriberWalletProperty.getId() == null) {
            return createSubscriberWalletProperty(subscriberWalletProperty);
        }
        SubscriberWalletProperty result = subscriberWalletPropertyRepository.save(subscriberWalletProperty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subscriberWalletProperty", subscriberWalletProperty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscriber-wallet-properties : get all the subscriberWalletProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriberWalletProperties in body
     */
    @RequestMapping(value = "/subscriber-wallet-properties",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SubscriberWalletProperty> getAllSubscriberWalletProperties() {
        log.debug("REST request to get all SubscriberWalletProperties");
        List<SubscriberWalletProperty> subscriberWalletProperties = subscriberWalletPropertyRepository.findAll();
        return subscriberWalletProperties;
    }

    /**
     * GET  /subscriber-wallet-properties/:id : get the "id" subscriberWalletProperty.
     *
     * @param id the id of the subscriberWalletProperty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriberWalletProperty, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subscriber-wallet-properties/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWalletProperty> getSubscriberWalletProperty(@PathVariable Long id) {
        log.debug("REST request to get SubscriberWalletProperty : {}", id);
        SubscriberWalletProperty subscriberWalletProperty = subscriberWalletPropertyRepository.findOne(id);
        return Optional.ofNullable(subscriberWalletProperty)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subscriber-wallet-properties/:id : delete the "id" subscriberWalletProperty.
     *
     * @param id the id of the subscriberWalletProperty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subscriber-wallet-properties/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubscriberWalletProperty(@PathVariable Long id) {
        log.debug("REST request to delete SubscriberWalletProperty : {}", id);
        subscriberWalletPropertyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subscriberWalletProperty", id.toString())).build();
    }

}
