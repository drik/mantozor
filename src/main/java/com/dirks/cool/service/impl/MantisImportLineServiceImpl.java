package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisImportLineService;
import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.mapper.MantisImportLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MantisImportLine.
 */
@Service
@Transactional
public class MantisImportLineServiceImpl implements MantisImportLineService {

    private final Logger log = LoggerFactory.getLogger(MantisImportLineServiceImpl.class);

    private final MantisImportLineRepository mantisImportLineRepository;

    private final MantisImportLineMapper mantisImportLineMapper;

    public MantisImportLineServiceImpl(MantisImportLineRepository mantisImportLineRepository, MantisImportLineMapper mantisImportLineMapper) {
        this.mantisImportLineRepository = mantisImportLineRepository;
        this.mantisImportLineMapper = mantisImportLineMapper;
    }

    /**
     * Save a mantisImportLine.
     *
     * @param mantisImportLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MantisImportLineDTO save(MantisImportLineDTO mantisImportLineDTO) {
        log.debug("Request to save MantisImportLine : {}", mantisImportLineDTO);
        MantisImportLine mantisImportLine = mantisImportLineMapper.toEntity(mantisImportLineDTO);
        mantisImportLine = mantisImportLineRepository.save(mantisImportLine);
        return mantisImportLineMapper.toDto(mantisImportLine);
    }

    /**
     * Get all the mantisImportLines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MantisImportLineDTO> findAll() {
        log.debug("Request to get all MantisImportLines");
        return mantisImportLineRepository.findAll().stream()
            .map(mantisImportLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mantisImportLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MantisImportLineDTO findOne(Long id) {
        log.debug("Request to get MantisImportLine : {}", id);
        MantisImportLine mantisImportLine = mantisImportLineRepository.findOne(id);
        return mantisImportLineMapper.toDto(mantisImportLine);
    }

    /**
     * Delete the mantisImportLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MantisImportLine : {}", id);
        mantisImportLineRepository.delete(id);
    }
}
