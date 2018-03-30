package com.dirks.cool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dirks.cool.domain.State;

import com.dirks.cool.repository.StateRepository;
import com.dirks.cool.web.rest.errors.BadRequestAlertException;
import com.dirks.cool.web.rest.util.HeaderUtil;
import com.dirks.cool.web.rest.util.PaginationUtil;
import com.dirks.cool.service.dto.StateDTO;
import com.dirks.cool.service.mapper.StateMapper;
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
 * REST controller for managing State.
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    private static final String ENTITY_NAME = "state";

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    public StateResource(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    /**
     * POST  /states : Create a new state.
     *
     * @param stateDTO the stateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stateDTO, or with status 400 (Bad Request) if the state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/states")
    @Timed
    public ResponseEntity<StateDTO> createState(@RequestBody StateDTO stateDTO) throws URISyntaxException {
        log.debug("REST request to save State : {}", stateDTO);
        if (stateDTO.getId() != null) {
            throw new BadRequestAlertException("A new state cannot already have an ID", ENTITY_NAME, "idexists");
        }
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        StateDTO result = stateMapper.toDto(state);
        return ResponseEntity.created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /states : Updates an existing state.
     *
     * @param stateDTO the stateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stateDTO,
     * or with status 400 (Bad Request) if the stateDTO is not valid,
     * or with status 500 (Internal Server Error) if the stateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/states")
    @Timed
    public ResponseEntity<StateDTO> updateState(@RequestBody StateDTO stateDTO) throws URISyntaxException {
        log.debug("REST request to update State : {}", stateDTO);
        if (stateDTO.getId() == null) {
            return createState(stateDTO);
        }
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        StateDTO result = stateMapper.toDto(state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /states : get all the states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/states")
    @Timed
    public ResponseEntity<List<StateDTO>> getAllStates(Pageable pageable) {
        log.debug("REST request to get a page of States");
        Page<State> page = stateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/states");
        return new ResponseEntity<>(stateMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /states/:id : get the "id" state.
     *
     * @param id the id of the stateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/states/{id}")
    @Timed
    public ResponseEntity<StateDTO> getState(@PathVariable Long id) {
        log.debug("REST request to get State : {}", id);
        State state = stateRepository.findOne(id);
        StateDTO stateDTO = stateMapper.toDto(state);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stateDTO));
    }

    /**
     * DELETE  /states/:id : delete the "id" state.
     *
     * @param id the id of the stateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/states/{id}")
    @Timed
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        log.debug("REST request to delete State : {}", id);
        stateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
