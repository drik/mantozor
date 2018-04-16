package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisConsumptionService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.MantisConsumptionDTO;
import com.dirks.cool.service.dto.MantisConsumptionCriteria;
import com.dirks.cool.service.MantisConsumptionQueryService;
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
 * REST controller for managing MantisConsumption.
 */
@RestController
@RequestMapping("/api")
public class MantisConsumptionResource {

    private final Logger log = LoggerFactory.getLogger(MantisConsumptionResource.class);

    private static final String ENTITY_NAME = "mantisConsumption";

    private final MantisConsumptionService mantisConsumptionService;

    private final MantisConsumptionQueryService mantisConsumptionQueryService;

    public MantisConsumptionResource(MantisConsumptionService mantisConsumptionService, MantisConsumptionQueryService mantisConsumptionQueryService) {
        this.mantisConsumptionService = mantisConsumptionService;
        this.mantisConsumptionQueryService = mantisConsumptionQueryService;
    }

    /**
     * POST  /mantis-consumptions : Create a new mantisConsumption.
     *
     * @param mantisConsumptionDTO the mantisConsumptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisConsumptionDTO, or with status 400 (Bad Request) if the mantisConsumption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-consumptions")
    @Timed
    public ResponseEntity<MantisConsumptionDTO> createMantisConsumption(@Valid @RequestBody MantisConsumptionDTO mantisConsumptionDTO) throws URISyntaxException {
        log.debug("REST request to save MantisConsumption : {}", mantisConsumptionDTO);
        if (mantisConsumptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisConsumption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisConsumptionDTO result = mantisConsumptionService.save(mantisConsumptionDTO);
        return ResponseEntity.created(new URI("/api/mantis-consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis-consumptions : Updates an existing mantisConsumption.
     *
     * @param mantisConsumptionDTO the mantisConsumptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisConsumptionDTO,
     * or with status 400 (Bad Request) if the mantisConsumptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisConsumptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-consumptions")
    @Timed
    public ResponseEntity<MantisConsumptionDTO> updateMantisConsumption(@Valid @RequestBody MantisConsumptionDTO mantisConsumptionDTO) throws URISyntaxException {
        log.debug("REST request to update MantisConsumption : {}", mantisConsumptionDTO);
        if (mantisConsumptionDTO.getId() == null) {
            return createMantisConsumption(mantisConsumptionDTO);
        }
        MantisConsumptionDTO result = mantisConsumptionService.save(mantisConsumptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisConsumptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-consumptions : get all the mantisConsumptions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisConsumptions in body
     */
    @GetMapping("/mantis-consumptions")
    @Timed
    public ResponseEntity<List<MantisConsumptionDTO>> getAllMantisConsumptions(MantisConsumptionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MantisConsumptions by criteria: {}", criteria);
        Page<MantisConsumptionDTO> page = mantisConsumptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis-consumptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mantis-consumptions/:id : get the "id" mantisConsumption.
     *
     * @param id the id of the mantisConsumptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisConsumptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-consumptions/{id}")
    @Timed
    public ResponseEntity<MantisConsumptionDTO> getMantisConsumption(@PathVariable Long id) {
        log.debug("REST request to get MantisConsumption : {}", id);
        MantisConsumptionDTO mantisConsumptionDTO = mantisConsumptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisConsumptionDTO));
    }

    /**
     * DELETE  /mantis-consumptions/:id : delete the "id" mantisConsumption.
     *
     * @param id the id of the mantisConsumptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-consumptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisConsumption(@PathVariable Long id) {
        log.debug("REST request to delete MantisConsumption : {}", id);
        mantisConsumptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
