package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.Lre;
import com.bd.mc.crm.repository.LreRepository;
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
 * REST controller for managing Lre.
 */
@RestController
@RequestMapping("/api")
public class LreResource {

    private final Logger log = LoggerFactory.getLogger(LreResource.class);
        
    @Inject
    private LreRepository lreRepository;
    
    /**
     * POST  /lres : Create a new lre.
     *
     * @param lre the lre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lre, or with status 400 (Bad Request) if the lre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lre> createLre(@Valid @RequestBody Lre lre) throws URISyntaxException {
        log.debug("REST request to save Lre : {}", lre);
        if (lre.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lre", "idexists", "A new lre cannot already have an ID")).body(null);
        }
        Lre result = lreRepository.save(lre);
        return ResponseEntity.created(new URI("/api/lres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lre", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lres : Updates an existing lre.
     *
     * @param lre the lre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lre,
     * or with status 400 (Bad Request) if the lre is not valid,
     * or with status 500 (Internal Server Error) if the lre couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lre> updateLre(@Valid @RequestBody Lre lre) throws URISyntaxException {
        log.debug("REST request to update Lre : {}", lre);
        if (lre.getId() == null) {
            return createLre(lre);
        }
        Lre result = lreRepository.save(lre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lre", lre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lres : get all the lres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lres in body
     */
    @RequestMapping(value = "/lres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lre> getAllLres() {
        log.debug("REST request to get all Lres");
        List<Lre> lres = lreRepository.findAll();
        return lres;
    }

    /**
     * GET  /lres/:id : get the "id" lre.
     *
     * @param id the id of the lre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lre, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lre> getLre(@PathVariable Long id) {
        log.debug("REST request to get Lre : {}", id);
        Lre lre = lreRepository.findOne(id);
        return Optional.ofNullable(lre)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lres/:id : delete the "id" lre.
     *
     * @param id the id of the lre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLre(@PathVariable Long id) {
        log.debug("REST request to delete Lre : {}", id);
        lreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lre", id.toString())).build();
    }

}
