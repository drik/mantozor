package com.dirks.cool.service;

import com.dirks.cool.domain.Referent;
import com.dirks.cool.repository.ReferentRepository;
import com.dirks.cool.service.dto.ReferentDTO;
import com.dirks.cool.service.mapper.ReferentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Referent.
 */
@Service
@Transactional
public class ReferentService {

    private final Logger log = LoggerFactory.getLogger(ReferentService.class);

    private final ReferentRepository referentRepository;

    private final ReferentMapper referentMapper;

    public ReferentService(ReferentRepository referentRepository, ReferentMapper referentMapper) {
        this.referentRepository = referentRepository;
        this.referentMapper = referentMapper;
    }

    /**
     * Save a referent.
     *
     * @param referentDTO the entity to save
     * @return the persisted entity
     */
    public ReferentDTO save(ReferentDTO referentDTO) {
        log.debug("Request to save Referent : {}", referentDTO);
        Referent referent = referentMapper.toEntity(referentDTO);
        referent = referentRepository.save(referent);
        return referentMapper.toDto(referent);
    }

    /**
     * Get all the referents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReferentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Referents");
        return referentRepository.findAll(pageable)
            .map(referentMapper::toDto);
    }

    /**
     * Get one referent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ReferentDTO findOne(Long id) {
        log.debug("Request to get Referent : {}", id);
        Referent referent = referentRepository.findOne(id);
        return referentMapper.toDto(referent);
    }

    /**
     * Delete the referent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Referent : {}", id);
        referentRepository.delete(id);
    }
}
