package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.service.dto.MantisDTO;
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
 * REST controller for managing Mantis.
 */
@RestController
@RequestMapping("/api")
public class MantisResource {

    private final Logger log = LoggerFactory.getLogger(MantisResource.class);

    private static final String ENTITY_NAME = "mantis";

    private final MantisService mantisService;

    public MantisResource(MantisService mantisService) {
        this.mantisService = mantisService;
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
     * @return the ResponseEntity with status 200 (OK) and the list of mantis in body
     */
    @GetMapping("/mantis")
    @Timed
    public List<MantisDTO> getAllMantis() {
        log.debug("REST request to get all Mantis");
        return mantisService.findAll();
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
