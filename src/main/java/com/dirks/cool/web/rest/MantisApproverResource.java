package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.domain.MantisApprover;

import com.dirks.cool.repository.MantisApproverRepository;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.MantisApproverDTO;
import com.dirks.cool.service.mapper.MantisApproverMapper;
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
 * REST controller for managing MantisApprover.
 */
@RestController
@RequestMapping("/api")
public class MantisApproverResource {

    private final Logger log = LoggerFactory.getLogger(MantisApproverResource.class);

    private static final String ENTITY_NAME = "mantisApprover";

    private final MantisApproverRepository mantisApproverRepository;

    private final MantisApproverMapper mantisApproverMapper;

    public MantisApproverResource(MantisApproverRepository mantisApproverRepository, MantisApproverMapper mantisApproverMapper) {
        this.mantisApproverRepository = mantisApproverRepository;
        this.mantisApproverMapper = mantisApproverMapper;
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
    public ResponseEntity<MantisApproverDTO> createMantisApprover(@RequestBody MantisApproverDTO mantisApproverDTO) throws URISyntaxException {
        log.debug("REST request to save MantisApprover : {}", mantisApproverDTO);
        if (mantisApproverDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantisApprover cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MantisApprover mantisApprover = mantisApproverMapper.toEntity(mantisApproverDTO);
        mantisApprover = mantisApproverRepository.save(mantisApprover);
        MantisApproverDTO result = mantisApproverMapper.toDto(mantisApprover);
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
    public ResponseEntity<MantisApproverDTO> updateMantisApprover(@RequestBody MantisApproverDTO mantisApproverDTO) throws URISyntaxException {
        log.debug("REST request to update MantisApprover : {}", mantisApproverDTO);
        if (mantisApproverDTO.getId() == null) {
            return createMantisApprover(mantisApproverDTO);
        }
        MantisApprover mantisApprover = mantisApproverMapper.toEntity(mantisApproverDTO);
        mantisApprover = mantisApproverRepository.save(mantisApprover);
        MantisApproverDTO result = mantisApproverMapper.toDto(mantisApprover);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mantisApproverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mantis-approvers : get all the mantisApprovers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mantisApprovers in body
     */
    @GetMapping("/mantis-approvers")
    @Timed
    public ResponseEntity<List<MantisApproverDTO>> getAllMantisApprovers(Pageable pageable) {
        log.debug("REST request to get a page of MantisApprovers");
        Page<MantisApprover> page = mantisApproverRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mantis-approvers");
        return new ResponseEntity<>(mantisApproverMapper.toDto(page.getContent()), headers, HttpStatus.OK);
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
        MantisApprover mantisApprover = mantisApproverRepository.findOne(id);
        MantisApproverDTO mantisApproverDTO = mantisApproverMapper.toDto(mantisApprover);
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
        mantisApproverRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
