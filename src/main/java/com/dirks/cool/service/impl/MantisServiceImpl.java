package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisConsumptionService;
import com.dirks.cool.service.MantisService;
import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.service.StateService;
import com.dirks.cool.service.StatusService;
import com.dirks.cool.service.UserService;
import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.State;
import com.dirks.cool.repository.MantisRepository;
import com.dirks.cool.service.dto.MantisConsumptionDTO;
import com.dirks.cool.service.dto.MantisDTO;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.dto.StateDTO;
import com.dirks.cool.service.dto.StatusDTO;
import com.dirks.cool.service.dto.StatusStateStatsDTO;
import com.dirks.cool.service.dto.StatusStatsDTO;
import com.dirks.cool.service.dto.TimelineDTO;
import com.dirks.cool.service.dto.TimelineEventDTO;
import com.dirks.cool.service.mapper.MantisMapper;
import com.dirks.cool.service.mapper.UserMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Mantis.
 */
@Service
@Transactional
public class MantisServiceImpl implements MantisService {

	private final Logger log = LoggerFactory.getLogger(MantisServiceImpl.class);

	private final MantisRepository mantisRepository;

	private final MantisConsumptionService mantisConsumptionService;

	private final UserService userService;
	
	private final StatusService statusService;
	
	private final MantisStatusService mantisStatusService;

	private final StateService stateService;

	private final UserMapper userMapper;
	
	private final MantisMapper mantisMapper;

	public MantisServiceImpl(MantisRepository mantisRepository, MantisMapper mantisMapper, MantisStatusService mantisStatusService,
			MantisConsumptionService mantisConsumptionService, StatusService statusService, StateService stateService, UserService userService, UserMapper userMapper) {
		this.mantisRepository = mantisRepository;
		this.mantisMapper = mantisMapper;
		this.userMapper = userMapper;
		this.mantisConsumptionService = mantisConsumptionService;
		this.statusService = statusService;
		this.stateService = stateService;
		this.mantisStatusService = mantisStatusService;
		this.userService = userService;
	}

	/**
	 * Save a mantis.
	 *
	 * @param mantisDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public MantisDTO save(MantisDTO mantisDTO) {
		log.debug("Request to save Mantis : {}", mantisDTO);
		Mantis mantis = mantisMapper.toEntity(mantisDTO);
		mantis = mantisRepository.save(mantis);
		return mantisMapper.toDto(mantis);
	}

	/**
	 * Get all the mantis.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MantisDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Mantis");
		return mantisRepository.findAll(pageable).map(mantisMapper::toDto);
	}

	/**
	 * Get one mantis by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public MantisDTO findOne(Long id) {
		log.debug("Request to get Mantis : {}", id);
		Mantis mantis = mantisRepository.findOne(id);
		Double alreadyConsumed = mantisConsumptionService.calculateTotalConsumptionToBill(mantis.getId());
		mantis.setChargeAlreadyConsumed(alreadyConsumed == null ? 0.0 : alreadyConsumed);
		mantis.setRemainingCharge((mantis.getTotalCharge() == null ? 0.0 : mantis.getTotalCharge())
				- (mantis.getChargeAlreadyConsumed() == null ? 0.0 : mantis.getChargeAlreadyConsumed()));
		return mantisMapper.toDto(mantis);
	}

	/**
	 * Delete the mantis by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Mantis : {}", id);
		mantisRepository.delete(id);
	}

	public List<StatusStatsDTO> getStatsForStatus() {
		return getStatsForStatus(null);
	}

	public List<StatusStatsDTO> getStatsForStatus(StateDTO state) {
		List<StatusStatsDTO> stats = new ArrayList<>();
		for (StatusDTO status : statusService.findAll()) {
			StatusStatsDTO stat = new StatusStatsDTO();
			stat.setStatus(status);
			if (state == null) {
				stat.setCount(mantisRepository.countMantisStatus(status.getId()));
			} else {
				stat.setCount(mantisRepository.countMantis(status.getId(), state.getId()));
			}
			Double totalMantis = (double) mantisRepository.count();
			Double percentage = (double) (totalMantis == 0D ? 0L : (double) stat.getCount() * 100D / totalMantis);
			stat.setPercentage(percentage);
			stats.add(stat);
		}
		return stats;
	}

	public List<StatusStateStatsDTO> getStatsForStatusAndState() {
		
		List<StatusStateStatsDTO> stats = new ArrayList<>();

		for (StateDTO state : stateService.findAll()) {
			StatusStateStatsDTO stat = new StatusStateStatsDTO();
			stat.setState(state);
			stat.setStatusesStats(this.getStatsForStatus(state));
			
			stat.setCount(mantisRepository.countMantisState(state.getId()));
			Double totalMantis = (double) mantisRepository.count();
			Double percentage = (double) (totalMantis == 0D ? 0L : (double) stat.getCount() * 100D / totalMantis);
			stat.setPercentage(percentage);
			stats.add(stat);
		}
		return stats;
	}
	

	public List<TimelineDTO> getTimelineEvents(Long mantisId) {
		log.debug("Request to get timeline events for a mantis {}", mantisId);
		List<TimelineDTO> timelines =  new ArrayList<>();
		List<TimelineEventDTO> events = new ArrayList<>();
		for(MantisStatusDTO status : mantisStatusService.findByMantisId(mantisId)) {
			TimelineEventDTO event = new TimelineEventDTO();
			event.setEventDate(status.getChangeDate());
			event.setType("status");
			event.setUser(userMapper.userToUserDTO(userService.getUserWithAuthorities(status.getUserId()).get()));
			event.setTitle(status.getStatus().getName());
			event.setDetails(status.getComments());
			events.add(event);
		}
		for(MantisConsumptionDTO status : mantisConsumptionService.findByMantisId(mantisId)) {
			TimelineEventDTO event = new TimelineEventDTO();
			event.setEventDate(status.getSubmissionDate());
			event.setType("consumption");
			event.setUser(userMapper.userToUserDTO(userService.getUserWithAuthorities(status.getUserId()).get()));
			event.setTitle(String.valueOf(status.getConsumed()));
			event.setDetails(String.valueOf(status.getConsumed()) + "|" + String.valueOf(status.getToBill()));
			events.add(event);
		}
		events.sort((TimelineEventDTO o1, TimelineEventDTO o2)->o1.getEventDate().compareTo(o2.getEventDate()));
		LocalDate curentDate = null;
		
		TimelineDTO timeline = new TimelineDTO();
		List<TimelineEventDTO> waitingEvents = new ArrayList<>();
		
		for(int i = 0; i<events.size();i++) {
			TimelineEventDTO event = events.get(i);
			if(i == 0) {
				curentDate = event.getEventDate();
			}
			if(event.getEventDate().isEqual(curentDate)) {
				waitingEvents.add(event);
			}else {
				timeline.setTimelineDate(curentDate);
				timeline.setEvents(waitingEvents);
				timelines.add(timeline);
				
				waitingEvents = new ArrayList<>();
				waitingEvents.add(event);
				timeline = new TimelineDTO();
				
				curentDate = event.getEventDate();
				timeline.setTimelineDate(curentDate);	
			}			
		}
		timeline.setTimelineDate(curentDate);	
		timeline.setEvents(waitingEvents);
		timelines.add(timeline);
		
		return timelines;
	}
}
