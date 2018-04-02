package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisApproverService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.MantisApproverDTO;
import com.dirks.cool.service.dto.MantisApproverCriteria;
import com.dirks.cool.service.MantisApproverQueryService;
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
 * REST controller for managing MantisApprover.
 */
@RestController
@RequestMapping("/api")
public class MantisApproverResource {

    private final Logger log = LoggerFactory.getLogger(MantisApproverResource.class);

    private static final String ENTITY_NAME = "mantisApprover";

    private final MantisApproverService mantisApproverService;

    private final MantisApproverQueryService mantisApproverQueryService;

    public MantisApproverResource(MantisApproverService mantisApproverService, MantisApproverQueryService mantisApproverQueryService) {
        this.mantisApproverService = mantisApproverService;
        this.mantisApproverQueryService = mantisApproverQueryService;
    }

    /**
     * POST  /mantis-approvers : Create a new mantisApprover.
     *
     * @param mantisApproverDTO the mantisApproverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisApproverDTO, or with status 400 (Bad Request) if the mantisApprover has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-approvers")
    @Timed
    public ResponseEntity<MantisApproverDTO> createMantisApprover(@Valid @RequestBody MantisApproverDTO mantisApproverDTO) throws URISyntaxException {
        log.debug("REST request to save MantisApprover : {}", mantisApproverDTO);
        if (mantisApproverDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisApprover cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisApproverDTO result = mantisApproverService.save(mantisApproverDTO);
        return ResponseEntity.created(new URI("/api/mantis-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis-approvers : Updates an existing mantisApprover.
     *
     * @param mantisApproverDTO the mantisApproverDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisApproverDTO,
     * or with status 400 (Bad Request) if the mantisApproverDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisApproverDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-approvers")
    @Timed
    public ResponseEntity<MantisApproverDTO> updateMantisApprover(@Valid @RequestBody MantisApproverDTO mantisApproverDTO) throws URISyntaxException {
        log.debug("REST request to update MantisApprover : {}", mantisApproverDTO);
        if (mantisApproverDTO.getId() == null) {
            return createMantisApprover(mantisApproverDTO);
        }
        MantisApproverDTO result = mantisApproverService.save(mantisApproverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisApproverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-approvers : get all the mantisApprovers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisApprovers in body
     */
    @GetMapping("/mantis-approvers")
    @Timed
    public ResponseEntity<List<MantisApproverDTO>> getAllMantisApprovers(MantisApproverCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MantisApprovers by criteria: {}", criteria);
        Page<MantisApproverDTO> page = mantisApproverQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis-approvers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mantis-approvers/:id : get the "id" mantisApprover.
     *
     * @param id the id of the mantisApproverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisApproverDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-approvers/{id}")
    @Timed
    public ResponseEntity<MantisApproverDTO> getMantisApprover(@PathVariable Long id) {
        log.debug("REST request to get MantisApprover : {}", id);
        MantisApproverDTO mantisApproverDTO = mantisApproverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisApproverDTO));
    }

    /**
     * DELETE  /mantis-approvers/:id : delete the "id" mantisApprover.
     *
     * @param id the id of the mantisApproverDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-approvers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisApprover(@PathVariable Long id) {
        log.debug("REST request to delete MantisApprover : {}", id);
        mantisApproverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
