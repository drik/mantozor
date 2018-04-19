package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.dto.MantisStatusCriteria;
import com.dirks.cool.service.MantisStatusQueryService;
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
 * REST controller for managing MantisStatus.
 */
@RestController
@RequestMapping("/api")
public class MantisStatusResource {

    private final Logger log = LoggerFactory.getLogger(MantisStatusResource.class);

    private static final String ENTITY_NAME = "mantisStatus";

    private final MantisStatusService mantisStatusService;

    private final MantisStatusQueryService mantisStatusQueryService;

    public MantisStatusResource(MantisStatusService mantisStatusService, MantisStatusQueryService mantisStatusQueryService) {
        this.mantisStatusService = mantisStatusService;
        this.mantisStatusQueryService = mantisStatusQueryService;
    }

    /**
     * POST  /mantis-statuses : Create a new mantisStatus.
     *
     * @param mantisStatusDTO the mantisStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisStatusDTO, or with status 400 (Bad Request) if the mantisStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-statuses")
    @Timed
    public ResponseEntity<MantisStatusDTO> createMantisStatus(@Valid @RequestBody MantisStatusDTO mantisStatusDTO) throws URISyntaxException {
        log.debug("REST request to save MantisStatus : {}", mantisStatusDTO);
        if (mantisStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
        	MantisStatusDTO result = mantisStatusService.save(mantisStatusDTO);
        	return ResponseEntity.created(new URI("/api/mantis-statuses/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                    .body(result);
        } catch(RuntimeException e) {
        	throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getMessage());
        }
    }

    /**
     * PUT  /mantis-statuses : Updates an existing mantisStatus.
     *
     * @param mantisStatusDTO the mantisStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisStatusDTO,
     * or with status 400 (Bad Request) if the mantisStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-statuses")
    @Timed
    public ResponseEntity<MantisStatusDTO> updateMantisStatus(@Valid @RequestBody MantisStatusDTO mantisStatusDTO) throws URISyntaxException {
        log.debug("REST request to update MantisStatus : {}", mantisStatusDTO);
        if (mantisStatusDTO.getId() == null) {
            return createMantisStatus(mantisStatusDTO);
        }
        MantisStatusDTO result = mantisStatusService.save(mantisStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-statuses : get all the mantisStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisStatuses in body
     */
    @GetMapping("/mantis-statuses")
    @Timed
    public ResponseEntity<List<MantisStatusDTO>> getAllMantisStatuses(MantisStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MantisStatuses by criteria: {}", criteria);
        Page<MantisStatusDTO> page = mantisStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mantis-statuses/:id : get the "id" mantisStatus.
     *
     * @param id the id of the mantisStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-statuses/{id}")
    @Timed
    public ResponseEntity<MantisStatusDTO> getMantisStatus(@PathVariable Long id) {
        log.debug("REST request to get MantisStatus : {}", id);
        MantisStatusDTO mantisStatusDTO = mantisStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisStatusDTO));
    }

    /**
     * DELETE  /mantis-statuses/:id : delete the "id" mantisStatus.
     *
     * @param id the id of the mantisStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisStatus(@PathVariable Long id) {
        log.debug("REST request to delete MantisStatus : {}", id);
        mantisStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /mantis-statuses : get all the mantisStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisStatuses in body
     */
    @GetMapping("/mantis-statuses-mantis/{mantisId}")
    @Timed
    public ResponseEntity<List<MantisStatusDTO>> getAllMantisStatusesForMantis(@PathVariable Long mantisId) {
        log.debug("REST request to get MantisStatuses by criteria: {}");
        List<MantisStatusDTO> page = mantisStatusService.findByMantisId(mantisId);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis-statuses");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /mantis-statuses/:id : get the "id" mantisStatus.
     *
     * @param id the id of the mantisStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-statuses-last/{idMantis}")
    @Timed
    public ResponseEntity<MantisStatusDTO> getMantisStatusForMantis(@PathVariable Long idMantis) {
        log.debug("REST request to get last MantisStatus : {}", idMantis);
        MantisStatusDTO mantisStatusDTO = mantisStatusService.findLastOneForMantis(idMantis);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisStatusDTO));
    }
}
