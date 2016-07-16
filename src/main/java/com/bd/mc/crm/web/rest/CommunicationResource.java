package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.Communication;
import com.bd.mc.crm.repository.CommunicationRepository;
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
 * REST controller for managing Communication.
 */
@RestController
@RequestMapping("/api")
public class CommunicationResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationResource.class);
        
    @Inject
    private CommunicationRepository communicationRepository;
    
    /**
     * POST  /communications : Create a new communication.
     *
     * @param communication the communication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communication, or with status 400 (Bad Request) if the communication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/communications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Communication> createCommunication(@RequestBody Communication communication) throws URISyntaxException {
        log.debug("REST request to save Communication : {}", communication);
        if (communication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("communication", "idexists", "A new communication cannot already have an ID")).body(null);
        }
        Communication result = communicationRepository.save(communication);
        return ResponseEntity.created(new URI("/api/communications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("communication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communications : Updates an existing communication.
     *
     * @param communication the communication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communication,
     * or with status 400 (Bad Request) if the communication is not valid,
     * or with status 500 (Internal Server Error) if the communication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/communications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Communication> updateCommunication(@RequestBody Communication communication) throws URISyntaxException {
        log.debug("REST request to update Communication : {}", communication);
        if (communication.getId() == null) {
            return createCommunication(communication);
        }
        Communication result = communicationRepository.save(communication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("communication", communication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communications : get all the communications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communications in body
     */
    @RequestMapping(value = "/communications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Communication> getAllCommunications() {
        log.debug("REST request to get all Communications");
        List<Communication> communications = communicationRepository.findAll();
        return communications;
    }

    /**
     * GET  /communications/:id : get the "id" communication.
     *
     * @param id the id of the communication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communication, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/communications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Communication> getCommunication(@PathVariable Long id) {
        log.debug("REST request to get Communication : {}", id);
        Communication communication = communicationRepository.findOne(id);
        return Optional.ofNullable(communication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /communications/:id : delete the "id" communication.
     *
     * @param id the id of the communication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/communications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCommunication(@PathVariable Long id) {
        log.debug("REST request to delete Communication : {}", id);
        communicationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("communication", id.toString())).build();
    }

}
