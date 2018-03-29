package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisFileService;
import com.dirks.cool.domain.MantisFile;
import com.dirks.cool.repository.MantisFileRepository;
import com.dirks.cool.service.dto.MantisFileDTO;
import com.dirks.cool.service.mapper.MantisFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MantisFile.
 */
@Service
@Transactional
public class MantisFileServiceImpl implements MantisFileService {

    private final Logger log = LoggerFactory.getLogger(MantisFileServiceImpl.class);

    private final MantisFileRepository mantisFileRepository;

    private final MantisFileMapper mantisFileMapper;

    public MantisFileServiceImpl(MantisFileRepository mantisFileRepository, MantisFileMapper mantisFileMapper) {
        this.mantisFileRepository = mantisFileRepository;
        this.mantisFileMapper = mantisFileMapper;
    }

    /**
     * Save a mantisFile.
     *
     * @param mantisFileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MantisFileDTO save(MantisFileDTO mantisFileDTO) {
        log.debug("Request to save MantisFile : {}", mantisFileDTO);
        MantisFile mantisFile = mantisFileMapper.toEntity(mantisFileDTO);
        mantisFile = mantisFileRepository.save(mantisFile);
        return mantisFileMapper.toDto(mantisFile);
    }

    /**
     * Get all the mantisFiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MantisFileDTO> findAll() {
        log.debug("Request to get all MantisFiles");
        return mantisFileRepository.findAll().stream()
            .map(mantisFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mantisFile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MantisFileDTO findOne(Long id) {
        log.debug("Request to get MantisFile : {}", id);
        MantisFile mantisFile = mantisFileRepository.findOne(id);
        return mantisFileMapper.toDto(mantisFile);
    }

    /**
     * Delete the mantisFile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MantisFile : {}", id);
        mantisFileRepository.delete(id);
    }
}
