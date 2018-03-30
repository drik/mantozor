package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.domain.Referent;

import com.dirks.cool.repository.ReferentRepository;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.ReferentDTO;
import com.dirks.cool.service.mapper.ReferentMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Referent.
 */
@RestController
@RequestMapping("/api")
public class ReferentResource {

    private final Logger log = LoggerFactory.getLogger(ReferentResource.class);

    private static final String ENTITY_NAME = "referent";

    private final ReferentRepository referentRepository;

    private final ReferentMapper referentMapper;

    public ReferentResource(ReferentRepository referentRepository, ReferentMapper referentMapper) {
        this.referentRepository = referentRepository;
        this.referentMapper = referentMapper;
    }

    /**
     * POST  /referents : Create a new referent.
     *
     * @param referentDTO the referentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referentDTO, or with status 400 (Bad Request) if the referent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/referents")
    @Timed
    public ResponseEntity<ReferentDTO> createReferent(@RequestBody ReferentDTO referentDTO) throws URISyntaxException {
        log.debug("REST request to save Referent : {}", referentDTO);
        if (referentDTO.getId() != null) {
            throw new BadRequestAlertException("A new referent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Referent referent = referentMapper.toEntity(referentDTO);
        referent = referentRepository.save(referent);
        ReferentDTO result = referentMapper.toDto(referent);
        return ResponseEntity.created(new URI("/api/referents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /referents : Updates an existing referent.
     *
     * @param referentDTO the referentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referentDTO,
     * or with status 400 (Bad Request) if the referentDTO is not valid,
     * or with status 500 (Internal Server Error) if the referentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/referents")
    @Timed
    public ResponseEntity<ReferentDTO> updateReferent(@RequestBody ReferentDTO referentDTO) throws URISyntaxException {
        log.debug("REST request to update Referent : {}", referentDTO);
        if (referentDTO.getId() == null) {
            return createReferent(referentDTO);
        }
        Referent referent = referentMapper.toEntity(referentDTO);
        referent = referentRepository.save(referent);
        ReferentDTO result = referentMapper.toDto(referent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /referents : get all the referents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of referents in body
     */
    @GetMapping("/referents")
    @Timed
    public ResponseEntity<List<ReferentDTO>> getAllReferents(Pageable pageable) {
        log.debug("REST request to get a page of Referents");
        Page<Referent> page = referentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/referents");
        return new ResponseEntity<>(referentMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /referents/:id : get the "id" referent.
     *
     * @param id the id of the referentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/referents/{id}")
    @Timed
    public ResponseEntity<ReferentDTO> getReferent(@PathVariable Long id) {
        log.debug("REST request to get Referent : {}", id);
        Referent referent = referentRepository.findOne(id);
        ReferentDTO referentDTO = referentMapper.toDto(referent);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(referentDTO));
    }

    /**
     * DELETE  /referents/:id : delete the "id" referent.
     *
     * @param id the id of the referentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/referents/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferent(@PathVariable Long id) {
        log.debug("REST request to delete Referent : {}", id);
        referentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
