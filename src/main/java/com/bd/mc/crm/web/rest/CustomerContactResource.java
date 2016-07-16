package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.CustomerContact;
import com.bd.mc.crm.repository.CustomerContactRepository;
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
 * REST controller for managing CustomerContact.
 */
@RestController
@RequestMapping("/api")
public class CustomerContactResource {

    private final Logger log = LoggerFactory.getLogger(CustomerContactResource.class);
        
    @Inject
    private CustomerContactRepository customerContactRepository;
    
    /**
     * POST  /customer-contacts : Create a new customerContact.
     *
     * @param customerContact the customerContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerContact, or with status 400 (Bad Request) if the customerContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-contacts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerContact> createCustomerContact(@RequestBody CustomerContact customerContact) throws URISyntaxException {
        log.debug("REST request to save CustomerContact : {}", customerContact);
        if (customerContact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customerContact", "idexists", "A new customerContact cannot already have an ID")).body(null);
        }
        CustomerContact result = customerContactRepository.save(customerContact);
        return ResponseEntity.created(new URI("/api/customer-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerContact", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-contacts : Updates an existing customerContact.
     *
     * @param customerContact the customerContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerContact,
     * or with status 400 (Bad Request) if the customerContact is not valid,
     * or with status 500 (Internal Server Error) if the customerContact couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-contacts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerContact> updateCustomerContact(@RequestBody CustomerContact customerContact) throws URISyntaxException {
        log.debug("REST request to update CustomerContact : {}", customerContact);
        if (customerContact.getId() == null) {
            return createCustomerContact(customerContact);
        }
        CustomerContact result = customerContactRepository.save(customerContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerContact", customerContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-contacts : get all the customerContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerContacts in body
     */
    @RequestMapping(value = "/customer-contacts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerContact> getAllCustomerContacts() {
        log.debug("REST request to get all CustomerContacts");
        List<CustomerContact> customerContacts = customerContactRepository.findAll();
        return customerContacts;
    }

    /**
     * GET  /customer-contacts/:id : get the "id" customerContact.
     *
     * @param id the id of the customerContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerContact, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/customer-contacts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerContact> getCustomerContact(@PathVariable Long id) {
        log.debug("REST request to get CustomerContact : {}", id);
        CustomerContact customerContact = customerContactRepository.findOne(id);
        return Optional.ofNullable(customerContact)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customer-contacts/:id : delete the "id" customerContact.
     *
     * @param id the id of the customerContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/customer-contacts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerContact(@PathVariable Long id) {
        log.debug("REST request to delete CustomerContact : {}", id);
        customerContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerContact", id.toString())).build();
    }

}
