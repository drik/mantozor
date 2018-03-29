package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.service.MantisFileService;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.service.dto.MantisFileDTO;
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
 * REST controller for managing MantisFile.
 */
@RestController
@RequestMapping("/api")
public class MantisFileResource {

    private final Logger log = LoggerFactory.getLogger(MantisFileResource.class);

    private static final String ENTITY_NAME = "mantisFile";

    private final MantisFileService mantisFileService;

    public MantisFileResource(MantisFileService mantisFileService) {
        this.mantisFileService = mantisFileService;
    }

    /**
     * POST  /mantis-files : Create a new mantisFile.
     *
     * @param mantisFileDTO the mantisFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mantisFileDTO, or with status 400 (Bad Request) if the mantisFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mantis-files")
    @Timed
    public ResponseEntity<MantisFileDTO> createMantisFile(@RequestBody MantisFileDTO mantisFileDTO) throws URISyntaxException {
        log.debug("REST request to save MantisFile : {}", mantisFileDTO);
        if (mantisFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisFileDTO result = mantisFileService.save(mantisFileDTO);
        return ResponseEntity.created(new URI("/api/mantis-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mantis-files : Updates an existing mantisFile.
     *
     * @param mantisFileDTO the mantisFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mantisFileDTO,
     * or with status 400 (Bad Request) if the mantisFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the mantisFileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mantis-files")
    @Timed
    public ResponseEntity<MantisFileDTO> updateMantisFile(@RequestBody MantisFileDTO mantisFileDTO) throws URISyntaxException {
        log.debug("REST request to update MantisFile : {}", mantisFileDTO);
        if (mantisFileDTO.getId() == null) {
            return createMantisFile(mantisFileDTO);
        }
        MantisFileDTO result = mantisFileService.save(mantisFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-files : get all the mantisFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mantisFiles in body
     */
    @GetMapping("/mantis-files")
    @Timed
    public List<MantisFileDTO> getAllMantisFiles() {
        log.debug("REST request to get all MantisFiles");
        return mantisFileService.findAll();
        }

    /**
     * GET  /mantis-files/:id : get the "id" mantisFile.
     *
     * @param id the id of the mantisFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mantisFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mantis-files/{id}")
    @Timed
    public ResponseEntity<MantisFileDTO> getMantisFile(@PathVariable Long id) {
        log.debug("REST request to get MantisFile : {}", id);
        MantisFileDTO mantisFileDTO = mantisFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mantisFileDTO));
    }

    /**
     * DELETE  /mantis-files/:id : delete the "id" mantisFile.
     *
     * @param id the id of the mantisFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mantis-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteMantisFile(@PathVariable Long id) {
        log.debug("REST request to delete MantisFile : {}", id);
        mantisFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
