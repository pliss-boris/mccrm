package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.CustomerAddres;
import com.bd.mc.crm.repository.CustomerAddresRepository;
import com.bd.mc.crm.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerAddres.
 */
@RestController
@RequestMapping("/api")
public class CustomerAddresResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAddresResource.class);

    @Inject
    private CustomerAddresRepository customerAddresRepository;

    /**
     * POST  /customer-addres : Create a new customerAddres.
     *
     * @param customerAddres the customerAddres to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerAddres, or with status 400 (Bad Request) if the customerAddres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-addres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerAddres> createCustomerAddres(@Valid @RequestBody CustomerAddres customerAddres) throws URISyntaxException {
        log.debug("REST request to save CustomerAddres : {}", customerAddres);
        if (customerAddres.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customerAddres", "idexists", "A new customerAddres cannot already have an ID")).body(null);
        }
        CustomerAddres result = customerAddresRepository.save(customerAddres);
        return ResponseEntity.created(new URI("/api/customer-addres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerAddres", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-addres : Updates an existing customerAddres.
     *
     * @param customerAddres the customerAddres to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerAddres,
     * or with status 400 (Bad Request) if the customerAddres is not valid,
     * or with status 500 (Internal Server Error) if the customerAddres couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-addres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerAddres> updateCustomerAddres(@Valid @RequestBody CustomerAddres customerAddres) throws URISyntaxException {
        log.debug("REST request to update CustomerAddres : {}", customerAddres);
        if (customerAddres.getId() == null) {
            return createCustomerAddres(customerAddres);
        }
        CustomerAddres result = customerAddresRepository.save(customerAddres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerAddres", customerAddres.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-addres : get all the customerAddres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerAddres in body
     */
    @RequestMapping(value = "/customer-addres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerAddres> getAllCustomerAddres() {
        log.debug("REST request to get all CustomerAddres");
        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        return customerAddres;
    }

    /**
     * GET  /customer-addres/:id : get the "id" customerAddres.
     *
     * @param id the id of the customerAddres to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAddres, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/customer-addres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerAddres> getCustomerAddres(@PathVariable Long id) {
        log.debug("REST request to get CustomerAddres : {}", id);
        CustomerAddres customerAddres = customerAddresRepository.findOne(id);
        return Optional.ofNullable(customerAddres)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customer-addres/:id : delete the "id" customerAddres.
     *
     * @param id the id of the customerAddres to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/customer-addres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerAddres(@PathVariable Long id) {
        log.debug("REST request to delete CustomerAddres : {}", id);
        customerAddresRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerAddres", id.toString())).build();
    }

    @RequestMapping(value = "/customer-addres/customer/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerAddres> findByCustomerAddress(@PathVariable Long id){
        return customerAddresRepository.findByCustomerId(id);
    }

}
