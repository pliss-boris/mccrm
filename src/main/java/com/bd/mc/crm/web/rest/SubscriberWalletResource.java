package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.SubscriberWallet;
import com.bd.mc.crm.repository.SubscriberWalletRepository;
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
 * REST controller for managing SubscriberWallet.
 */
@RestController
@RequestMapping("/api")
public class SubscriberWalletResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberWalletResource.class);
        
    @Inject
    private SubscriberWalletRepository subscriberWalletRepository;
    
    /**
     * POST  /subscriber-wallets : Create a new subscriberWallet.
     *
     * @param subscriberWallet the subscriberWallet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriberWallet, or with status 400 (Bad Request) if the subscriberWallet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscriber-wallets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWallet> createSubscriberWallet(@RequestBody SubscriberWallet subscriberWallet) throws URISyntaxException {
        log.debug("REST request to save SubscriberWallet : {}", subscriberWallet);
        if (subscriberWallet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subscriberWallet", "idexists", "A new subscriberWallet cannot already have an ID")).body(null);
        }
        SubscriberWallet result = subscriberWalletRepository.save(subscriberWallet);
        return ResponseEntity.created(new URI("/api/subscriber-wallets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subscriberWallet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscriber-wallets : Updates an existing subscriberWallet.
     *
     * @param subscriberWallet the subscriberWallet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriberWallet,
     * or with status 400 (Bad Request) if the subscriberWallet is not valid,
     * or with status 500 (Internal Server Error) if the subscriberWallet couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subscriber-wallets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWallet> updateSubscriberWallet(@RequestBody SubscriberWallet subscriberWallet) throws URISyntaxException {
        log.debug("REST request to update SubscriberWallet : {}", subscriberWallet);
        if (subscriberWallet.getId() == null) {
            return createSubscriberWallet(subscriberWallet);
        }
        SubscriberWallet result = subscriberWalletRepository.save(subscriberWallet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subscriberWallet", subscriberWallet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscriber-wallets : get all the subscriberWallets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriberWallets in body
     */
    @RequestMapping(value = "/subscriber-wallets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SubscriberWallet> getAllSubscriberWallets() {
        log.debug("REST request to get all SubscriberWallets");
        List<SubscriberWallet> subscriberWallets = subscriberWalletRepository.findAll();
        return subscriberWallets;
    }

    /**
     * GET  /subscriber-wallets/:id : get the "id" subscriberWallet.
     *
     * @param id the id of the subscriberWallet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriberWallet, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subscriber-wallets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubscriberWallet> getSubscriberWallet(@PathVariable Long id) {
        log.debug("REST request to get SubscriberWallet : {}", id);
        SubscriberWallet subscriberWallet = subscriberWalletRepository.findOne(id);
        return Optional.ofNullable(subscriberWallet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subscriber-wallets/:id : delete the "id" subscriberWallet.
     *
     * @param id the id of the subscriberWallet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subscriber-wallets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubscriberWallet(@PathVariable Long id) {
        log.debug("REST request to delete SubscriberWallet : {}", id);
        subscriberWalletRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subscriberWallet", id.toString())).build();
    }

}
