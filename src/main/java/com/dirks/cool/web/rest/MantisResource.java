package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.MantisDTO;
import com.dirks.cool.service.dto.MantisCriteria;
import com.dirks.cool.service.MantisQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mantis.
 */
@RestController
@RequestMapping("/api")
public class MantisResource {

    private final Logger log = LoggerFactory.getLogger(MantisResource.class);

    private static final String ENTITY_NAME = "mantis";

    private final MantisService mantisService;

    private final MantisQueryService mantisQueryService;

    public MantisResource(MantisService mantisService, MantisQueryService mantisQueryService) {
        this.mantisService = mantisService;
        this.mantisQueryService = mantisQueryService;
    }

    /**
     * POST  /mantis : Create a new mantis.
     *
     * @param mantisDTO the mantisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisDTO, or with status 400 (Bad Request) if the mantis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis")
    @Timed
    public ResponseEntity<MantisDTO> createMantis(@Valid @RequestBody MantisDTO mantisDTO) throws URISyntaxException {
        log.debug("REST request to save Mantis : {}", mantisDTO);
        if (mantisDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisDTO result = mantisService.save(mantisDTO);
        return ResponseEntity.created(new URI("/api/mantis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis : Updates an existing mantis.
     *
     * @param mantisDTO the mantisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisDTO,
     * or with status 400 (Bad Request) if the mantisDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis")
    @Timed
    public ResponseEntity<MantisDTO> updateMantis(@Valid @RequestBody MantisDTO mantisDTO) throws URISyntaxException {
        log.debug("REST request to update Mantis : {}", mantisDTO);
        if (mantisDTO.getId() == null) {
            return createMantis(mantisDTO);
        }
        MantisDTO result = mantisService.save(mantisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis : get all the mantis.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantis in body
     */
    @GetMapping("/mantis")
    @Timed
    public ResponseEntity<List<MantisDTO>> getAllMantis(MantisCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Mantis by criteria: {}", criteria);
        Page<MantisDTO> page = mantisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mantis/:id : get the "id" mantis.
     *
     * @param id the id of the mantisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis/{id}")
    @Timed
    public ResponseEntity<MantisDTO> getMantis(@PathVariable Long id) {
        log.debug("REST request to get Mantis : {}", id);
        MantisDTO mantisDTO = mantisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisDTO));
    }

    /**
     * DELETE  /mantis/:id : delete the "id" mantis.
     *
     * @param id the id of the mantisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantis(@PathVariable Long id) {
        log.debug("REST request to delete Mantis : {}", id);
        mantisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
