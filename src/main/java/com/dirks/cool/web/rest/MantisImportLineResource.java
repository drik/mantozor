package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisImportLineService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.dto.MantisImportLineCriteria;
import com.dirks.cool.service.MantisImportLineQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MantisImportLine.
 */
@RestController
@RequestMapping("/api")
public class MantisImportLineResource {

    private final Logger log = LoggerFactory.getLogger(MantisImportLineResource.class);

    private static final String ENTITY_NAME = "mantisImportLine";

    private final MantisImportLineService mantisImportLineService;

    private final MantisImportLineQueryService mantisImportLineQueryService;

    public MantisImportLineResource(MantisImportLineService mantisImportLineService, MantisImportLineQueryService mantisImportLineQueryService) {
        this.mantisImportLineService = mantisImportLineService;
        this.mantisImportLineQueryService = mantisImportLineQueryService;
    }

    /**
     * POST  /mantis-import-lines : Create a new mantisImportLine.
     *
     * @param mantisImportLineDTO the mantisImportLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisImportLineDTO, or with status 400 (Bad Request) if the mantisImportLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-import-lines")
    @Timed
    public ResponseEntity<MantisImportLineDTO> createMantisImportLine(@Valid @RequestBody MantisImportLineDTO mantisImportLineDTO) throws URISyntaxException {
        log.debug("REST request to save MantisImportLine : {}", mantisImportLineDTO);
        if (mantisImportLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisImportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisImportLineDTO result = mantisImportLineService.save(mantisImportLineDTO);
        return ResponseEntity.created(new URI("/api/mantis-import-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis-import-lines : Updates an existing mantisImportLine.
     *
     * @param mantisImportLineDTO the mantisImportLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisImportLineDTO,
     * or with status 400 (Bad Request) if the mantisImportLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisImportLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-import-lines")
    @Timed
    public ResponseEntity<MantisImportLineDTO> updateMantisImportLine(@Valid @RequestBody MantisImportLineDTO mantisImportLineDTO) throws URISyntaxException {
        log.debug("REST request to update MantisImportLine : {}", mantisImportLineDTO);
        if (mantisImportLineDTO.getId() == null) {
            return createMantisImportLine(mantisImportLineDTO);
        }
        MantisImportLineDTO result = mantisImportLineService.save(mantisImportLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisImportLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-import-lines : get all the mantisImportLines.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mantisImportLines in body
     */
    @GetMapping("/mantis-import-lines")
    @Timed
    public ResponseEntity<List<MantisImportLineDTO>> getAllMantisImportLines(MantisImportLineCriteria criteria) {
        log.debug("REST request to get MantisImportLines by criteria: {}", criteria);
        List<MantisImportLineDTO> entityList = mantisImportLineQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /mantis-import-lines/:id : get the "id" mantisImportLine.
     *
     * @param id the id of the mantisImportLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisImportLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-import-lines/{id}")
    @Timed
    public ResponseEntity<MantisImportLineDTO> getMantisImportLine(@PathVariable Long id) {
        log.debug("REST request to get MantisImportLine : {}", id);
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisImportLineDTO));
    }

    /**
     * DELETE  /mantis-import-lines/:id : delete the "id" mantisImportLine.
     *
     * @param id the id of the mantisImportLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-import-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisImportLine(@PathVariable Long id) {
        log.debug("REST request to delete MantisImportLine : {}", id);
        mantisImportLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
