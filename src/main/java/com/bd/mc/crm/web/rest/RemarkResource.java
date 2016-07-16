package com.bd.mc.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bd.mc.crm.domain.Remark;
import com.bd.mc.crm.repository.RemarkRepository;
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
 * REST controller for managing Remark.
 */
@RestController
@RequestMapping("/api")
public class RemarkResource {

    private final Logger log = LoggerFactory.getLogger(RemarkResource.class);
        
    @Inject
    private RemarkRepository remarkRepository;
    
    /**
     * POST  /remarks : Create a new remark.
     *
     * @param remark the remark to create
     * @return the ResponseEntity with status 201 (Created) and with body the new remark, or with status 400 (Bad Request) if the remark has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/remarks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remark> createRemark(@Valid @RequestBody Remark remark) throws URISyntaxException {
        log.debug("REST request to save Remark : {}", remark);
        if (remark.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("remark", "idexists", "A new remark cannot already have an ID")).body(null);
        }
        Remark result = remarkRepository.save(remark);
        return ResponseEntity.created(new URI("/api/remarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("remark", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remarks : Updates an existing remark.
     *
     * @param remark the remark to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated remark,
     * or with status 400 (Bad Request) if the remark is not valid,
     * or with status 500 (Internal Server Error) if the remark couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/remarks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remark> updateRemark(@Valid @RequestBody Remark remark) throws URISyntaxException {
        log.debug("REST request to update Remark : {}", remark);
        if (remark.getId() == null) {
            return createRemark(remark);
        }
        Remark result = remarkRepository.save(remark);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("remark", remark.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remarks : get all the remarks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of remarks in body
     */
    @RequestMapping(value = "/remarks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Remark> getAllRemarks() {
        log.debug("REST request to get all Remarks");
        List<Remark> remarks = remarkRepository.findAll();
        return remarks;
    }

    /**
     * GET  /remarks/:id : get the "id" remark.
     *
     * @param id the id of the remark to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the remark, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/remarks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remark> getRemark(@PathVariable Long id) {
        log.debug("REST request to get Remark : {}", id);
        Remark remark = remarkRepository.findOne(id);
        return Optional.ofNullable(remark)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /remarks/:id : delete the "id" remark.
     *
     * @param id the id of the remark to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/remarks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRemark(@PathVariable Long id) {
        log.debug("REST request to delete Remark : {}", id);
        remarkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("remark", id.toString())).build();
    }

}
