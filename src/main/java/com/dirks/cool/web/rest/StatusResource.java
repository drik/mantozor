package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.domain.Status;

import com.dirks.cool.repository.StatusRepository;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.StatusDTO;
import com.dirks.cool.service.mapper.StatusMapper;
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
 * REST controller for managing Status.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "status";

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusResource(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * POST  /statuses : Create a new status.
     *
     * @param statusDTO the statusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusDTO, or with status 400 (Bad Request) if the status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statuses")
    @Timed
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to save Status : {}", statusDTO);
        if (statusDTO.getId() != null) {
            throw new BadRequestAlertException("A new status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        StatusDTO result = statusMapper.toDto(status);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statuses : Updates an existing status.
     *
     * @param statusDTO the statusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusDTO,
     * or with status 400 (Bad Request) if the statusDTO is not valid,
     * or with status 500 (Internal Server Error) if the statusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statuses")
    @Timed
    public ResponseEntity<StatusDTO> updateStatus(@RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to update Status : {}", statusDTO);
        if (statusDTO.getId() == null) {
            return createStatus(statusDTO);
        }
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        StatusDTO result = statusMapper.toDto(status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statuses : get all the statuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statuses in body
     */
    @GetMapping("/statuses")
    @Timed
    public ResponseEntity<List<StatusDTO>> getAllStatuses(Pageable pageable) {
        log.debug("REST request to get a page of Statuses");
        Page<Status> page = statusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statuses");
        return new ResponseEntity<>(statusMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /statuses/:id : get the "id" status.
     *
     * @param id the id of the statusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<StatusDTO> getStatus(@PathVariable Long id) {
        log.debug("REST request to get Status : {}", id);
        Status status = statusRepository.findOne(id);
        StatusDTO statusDTO = statusMapper.toDto(status);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statusDTO));
    }

    /**
     * DELETE  /statuses/:id : delete the "id" status.
     *
     * @param id the id of the statusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        statusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
