package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisImportService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.service.dto.MantisImportDTO;
import com.dirks.cool.service.dto.MantisImportCriteria;
import com.dirks.cool.service.MantisImportQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MantisImport.
 */
@RestController
@RequestMapping("/api")
public class MantisImportResource {

    private final Logger log = LoggerFactory.getLogger(MantisImportResource.class);

    private static final String ENTITY_NAME = "mantisImport";

    private final MantisImportService mantisImportService;

    private final MantisImportQueryService mantisImportQueryService;

    public MantisImportResource(MantisImportService mantisImportService, MantisImportQueryService mantisImportQueryService) {
        this.mantisImportService = mantisImportService;
        this.mantisImportQueryService = mantisImportQueryService;
    }

    /**
     * POST  /mantis-imports : Create a new mantisImport.
     *
     * @param mantisImportDTO the mantisImportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisImportDTO, or with status 400 (Bad Request) if the mantisImport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-imports")
    @Timed
    public ResponseEntity<MantisImportDTO> createMantisImport(@RequestBody MantisImportDTO mantisImportDTO) throws URISyntaxException {
        log.debug("REST request to save MantisImport : {}", mantisImportDTO);
        if (mantisImportDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisImport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisImportDTO result = mantisImportService.save(mantisImportDTO);
        return ResponseEntity.created(new URI("/api/mantis-imports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis-imports : Updates an existing mantisImport.
     *
     * @param mantisImportDTO the mantisImportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisImportDTO,
     * or with status 400 (Bad Request) if the mantisImportDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisImportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-imports")
    @Timed
    public ResponseEntity<MantisImportDTO> updateMantisImport(@RequestBody MantisImportDTO mantisImportDTO) throws URISyntaxException {
        log.debug("REST request to update MantisImport : {}", mantisImportDTO);
        if (mantisImportDTO.getId() == null) {
            return createMantisImport(mantisImportDTO);
        }
        MantisImportDTO result = mantisImportService.save(mantisImportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisImportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-imports : get all the mantisImports.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisImports in body
     */
    @GetMapping("/mantis-imports")
    @Timed
    public ResponseEntity<List<MantisImportDTO>> getAllMantisImports(MantisImportCriteria criteria) {
        log.debug("REST request to get MantisImports by criteria: {}", criteria);
        List<MantisImportDTO> entityList = mantisImportQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /mantis-imports/:id : get the "id" mantisImport.
     *
     * @param id the id of the mantisImportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisImportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-imports/{id}")
    @Timed
    public ResponseEntity<MantisImportDTO> getMantisImport(@PathVariable Long id) {
        log.debug("REST request to get MantisImport : {}", id);
        MantisImportDTO mantisImportDTO = mantisImportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisImportDTO));
    }

    /**
     * DELETE  /mantis-imports/:id : delete the "id" mantisImport.
     *
     * @param id the id of the mantisImportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-imports/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisImport(@PathVariable Long id) {
        log.debug("REST request to delete MantisImport : {}", id);
        mantisImportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
