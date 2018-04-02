package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisImportLineService;
import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.mapper.MantisImportLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MantisImportLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MantisImportLines");
        return mantisImportLineRepository.findAll(pageable)
            .map(mantisImportLineMapper::toDto);
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
