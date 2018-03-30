package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisImportService;
import com.dirks.cool.domain.MantisImport;
import com.dirks.cool.repository.MantisImportRepository;
import com.dirks.cool.service.dto.MantisImportDTO;
import com.dirks.cool.service.mapper.MantisImportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MantisImport.
 */
@Service
@Transactional
public class MantisImportServiceImpl implements MantisImportService {

    private final Logger log = LoggerFactory.getLogger(MantisImportServiceImpl.class);

    private final MantisImportRepository mantisImportRepository;

    private final MantisImportMapper mantisImportMapper;

    public MantisImportServiceImpl(MantisImportRepository mantisImportRepository, MantisImportMapper mantisImportMapper) {
        this.mantisImportRepository = mantisImportRepository;
        this.mantisImportMapper = mantisImportMapper;
    }

    /**
     * Save a mantisImport.
     *
     * @param mantisImportDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MantisImportDTO save(MantisImportDTO mantisImportDTO) {
        log.debug("Request to save MantisImport : {}", mantisImportDTO);
        MantisImport mantisImport = mantisImportMapper.toEntity(mantisImportDTO);
        mantisImport = mantisImportRepository.save(mantisImport);
        return mantisImportMapper.toDto(mantisImport);
    }

    /**
     * Get all the mantisImports.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MantisImportDTO> findAll() {
        log.debug("Request to get all MantisImports");
        return mantisImportRepository.findAll().stream()
            .map(mantisImportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mantisImport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MantisImportDTO findOne(Long id) {
        log.debug("Request to get MantisImport : {}", id);
        MantisImport mantisImport = mantisImportRepository.findOne(id);
        return mantisImportMapper.toDto(mantisImport);
    }

    /**
     * Delete the mantisImport by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MantisImport : {}", id);
        mantisImportRepository.delete(id);
    }
}
