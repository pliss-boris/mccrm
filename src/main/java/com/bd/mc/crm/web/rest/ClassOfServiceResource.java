package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.ClassOfService;
import com.bd.mc.crm.repository.ClassOfServiceRepository;
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
 * REST controller for managing ClassOfService.
 */
@RestController
@RequestMapping("/api")
public class ClassOfServiceResource {

    private final Logger log = LoggerFactory.getLogger(ClassOfServiceResource.class);
        
    @Inject
    private ClassOfServiceRepository classOfServiceRepository;
    
    /**
     * POST  /class-of-services : Create a new classOfService.
     *
     * @param classOfService the classOfService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classOfService, or with status 400 (Bad Request) if the classOfService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/class-of-services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassOfService> createClassOfService(@Valid @RequestBody ClassOfService classOfService) throws URISyntaxException {
        log.debug("REST request to save ClassOfService : {}", classOfService);
        if (classOfService.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("classOfService", "idexists", "A new classOfService cannot already have an ID")).body(null);
        }
        ClassOfService result = classOfServiceRepository.save(classOfService);
        return ResponseEntity.created(new URI("/api/class-of-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("classOfService", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-of-services : Updates an existing classOfService.
     *
     * @param classOfService the classOfService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classOfService,
     * or with status 400 (Bad Request) if the classOfService is not valid,
     * or with status 500 (Internal Server Error) if the classOfService couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/class-of-services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassOfService> updateClassOfService(@Valid @RequestBody ClassOfService classOfService) throws URISyntaxException {
        log.debug("REST request to update ClassOfService : {}", classOfService);
        if (classOfService.getId() == null) {
            return createClassOfService(classOfService);
        }
        ClassOfService result = classOfServiceRepository.save(classOfService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("classOfService", classOfService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-of-services : get all the classOfServices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classOfServices in body
     */
    @RequestMapping(value = "/class-of-services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClassOfService> getAllClassOfServices() {
        log.debug("REST request to get all ClassOfServices");
        List<ClassOfService> classOfServices = classOfServiceRepository.findAll();
        return classOfServices;
    }

    /**
     * GET  /class-of-services/:id : get the "id" classOfService.
     *
     * @param id the id of the classOfService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classOfService, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/class-of-services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassOfService> getClassOfService(@PathVariable Long id) {
        log.debug("REST request to get ClassOfService : {}", id);
        ClassOfService classOfService = classOfServiceRepository.findOne(id);
        return Optional.ofNullable(classOfService)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /class-of-services/:id : delete the "id" classOfService.
     *
     * @param id the id of the classOfService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/class-of-services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClassOfService(@PathVariable Long id) {
        log.debug("REST request to delete ClassOfService : {}", id);
        classOfServiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("classOfService", id.toString())).build();
    }

}
