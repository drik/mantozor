package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisConsumptionService;
import com.dirks.cool.service.UserService;
import com.dirks.cool.domain.MantisConsumption;
import com.dirks.cool.repository.MantisConsumptionRepository;
import com.dirks.cool.service.dto.MantisConsumptionDTO;
import com.dirks.cool.service.mapper.MantisConsumptionMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MantisConsumption.
 */
@Service
@Transactional
public class MantisConsumptionServiceImpl implements MantisConsumptionService {

    private final Logger log = LoggerFactory.getLogger(MantisConsumptionServiceImpl.class);

    private final MantisConsumptionRepository mantisConsumptionRepository;
    
    private final UserService userService;

    private final MantisConsumptionMapper mantisConsumptionMapper;

    public MantisConsumptionServiceImpl(MantisConsumptionRepository mantisConsumptionRepository, MantisConsumptionMapper mantisConsumptionMapper, UserService userService) {
        this.mantisConsumptionRepository = mantisConsumptionRepository;
        this.mantisConsumptionMapper = mantisConsumptionMapper;
        this.userService = userService;
    }

    /**
     * Save a mantisConsumption.
     *
     * @param mantisConsumptionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MantisConsumptionDTO save(MantisConsumptionDTO mantisConsumptionDTO) {
        log.debug("Request to save MantisConsumption : {}", mantisConsumptionDTO);
        MantisConsumption mantisConsumption = mantisConsumptionMapper.toEntity(mantisConsumptionDTO);
        mantisConsumption.setUser(userService.getUserWithAuthorities().get());
        mantisConsumption = mantisConsumptionRepository.save(mantisConsumption);
        return mantisConsumptionMapper.toDto(mantisConsumption);
    }

    /**
     * Get all the mantisConsumptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MantisConsumptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MantisConsumptions");
        return mantisConsumptionRepository.findAll(pageable)
            .map(mantisConsumptionMapper::toDto);
    }

    /**
     * Get one mantisConsumption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MantisConsumptionDTO findOne(Long id) {
        log.debug("Request to get MantisConsumption : {}", id);
        MantisConsumption mantisConsumption = mantisConsumptionRepository.findOne(id);
        return mantisConsumptionMapper.toDto(mantisConsumption);
    }

    /**
     * Delete the mantisConsumption by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MantisConsumption : {}", id);
        mantisConsumptionRepository.delete(id);
    }

	@Override
	public List<MantisConsumption> findByMantisId(Long mantisId) {
		log.debug("Request to get all MantisConsumption :  for a mantis {}", mantisId);
		return mantisConsumptionRepository.findByMantisId(mantisId);
	}

	@Override
	public Double calculateTotalConsumptionConsumed(Long mantisId) {
		log.debug("Request to calculate Total Consumption Consumed :  for a mantis {}", mantisId);
		return mantisConsumptionRepository.calculateTotalConsumptionConsumed(mantisId);
	}

	@Override
	public Double calculateTotalConsumptionToBill(Long mantisId) {
		log.debug("Request to calculate Total Consumption to bill :  for a mantis {}", mantisId);
		return mantisConsumptionRepository.calculateTotalConsumptionToBill(mantisId);
	}
}
