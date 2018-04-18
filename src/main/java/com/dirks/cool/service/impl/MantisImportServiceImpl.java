package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisImportService;
import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.service.UserService;
import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.MantisImport;
import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.domain.Project;
import com.dirks.cool.domain.State;
import com.dirks.cool.domain.Status;
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.repository.MantisImportRepository;
import com.dirks.cool.repository.MantisRepository;
import com.dirks.cool.repository.MantisStatusRepository;
import com.dirks.cool.repository.ProjectRepository;
import com.dirks.cool.repository.ReferentRepository;
import com.dirks.cool.repository.StateRepository;
import com.dirks.cool.service.dto.MantisImportDTO;
import com.dirks.cool.service.mapper.MantisImportMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MantisImport.
 */
@Service
@Transactional
public class MantisImportServiceImpl implements MantisImportService {

	private final Logger log = LoggerFactory.getLogger(MantisImportServiceImpl.class);

	private final MantisImportRepository mantisImportRepository;
	private final MantisImportLineRepository mantisImportLineRepository;
	private final MantisRepository mantisRepository;
	private final ProjectRepository projectRepository;
	private final MantisStatusRepository mantisStatusRepository;
	private final StateRepository stateRepository;
	
	private final UserService userService;

	private final MantisImportMapper mantisImportMapper;


	

	/**
	 * @param mantisImportRepository
	 * @param mantisImportLineRepository
	 * @param mantisRepository
	 * @param projectRepository
	 * @param referentRepository
	 * @param stateRepository
	 * @param mantisImportMapper
	 */
	public MantisImportServiceImpl(MantisImportRepository mantisImportRepository,
			MantisImportLineRepository mantisImportLineRepository, MantisRepository mantisRepository,
			ProjectRepository projectRepository, MantisStatusRepository mantisStatusRepository, StateRepository stateRepository,
			UserService userService, MantisImportMapper mantisImportMapper) {
		super();
		this.mantisImportRepository = mantisImportRepository;
		this.mantisImportLineRepository = mantisImportLineRepository;
		this.mantisRepository = mantisRepository;
		this.projectRepository = projectRepository;
		this.mantisStatusRepository = mantisStatusRepository;
		this.stateRepository = stateRepository;
		this.userService = userService;
		this.mantisImportMapper = mantisImportMapper;
	}

	/**
	 * Save a mantisImport.
	 *
	 * @param mantisImportDTO
	 *            the entity to save
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
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MantisImportDTO> findAll(Pageable pageable) {
		log.debug("Request to get all MantisImports");
		return mantisImportRepository.findAll(pageable).map(mantisImportMapper::toDto);
	}

	/**
	 * Get one mantisImport by id.
	 *
	 * @param id
	 *            the id of the entity
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
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete MantisImport : {}", id);
		mantisImportRepository.delete(id);
	}

	@Override
	public MantisImportDTO importMantis(MantisImportDTO mantisImportDTO) {
		CsvToBean<MantisImportLine> csvToBean = new CsvToBean<MantisImportLine>();
		// Id,Projet,Catégorie,Gravité,Référence d'imputation AUGEO,Interlocuteur
		// technique,État,Résumé,
		// Date de soumission,Date d'engagement souhaitée,Charge estimée CACF,Date
		// d'engagement CDS,
		// Charge estimée CDS,Date estimée de livraison du DST,Date de recette,Date de
		// production,
		// Note Respect des normes de développements,Commentaire Note Respect des normes
		// de développements,
		// Note Conformité aux besoins exprimés,Commentaire Note Conformité aux besoins
		// exprimés,
		// Note Respect du délai global du projet,Commentaire Note Respect du délai
		// global du projet
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Id", "mantisNumber");
		columnMapping.put("Projet", "project");
		columnMapping.put("Catégorie", "category");
		columnMapping.put("Gravité", "garvity");
		columnMapping.put("Référence d'imputation AUGEO", "augeoReference");
		columnMapping.put("Interlocuteur technique", "technicalReference");
		columnMapping.put("État", "stateString");
		columnMapping.put("Résumé", "description");
		columnMapping.put("Date de soumission", "submissionDateString");
		columnMapping.put("Date d'engagement souhaitée", "desiredCommitmentDateString");
		columnMapping.put("Charge estimée CACF", "estimatedChargeCACFString");
		columnMapping.put("Date d'engagement CDS", "commitmentDateCDSString");
		columnMapping.put("Charge estimée CDS", "estimatedChargeCDSString");
		columnMapping.put("Date estimée de livraison du DST", "estimatedDSTDelivreryDateString");
		columnMapping.put("Date de recette", "recipeDateString");
		columnMapping.put("Date de production", "productionDateString");
		columnMapping.put("Note Respect des normes de développements", "devStandardsComplianceScore");
		columnMapping.put("Commentaire Note Respect des normes de développements",
				"devStandardsComplianceScoreComment");
		columnMapping.put("Note Conformité aux besoins exprimés", "expressedNeedsComplianceScore");
		columnMapping.put("Commentaire Note Conformité aux besoins exprimés", "expressedNeedsComplianceScoreComment");
		columnMapping.put("Note Respect du délai global du projet", "overallDeadlineRespectScore");
		columnMapping.put("Commentaire Note Respect du délai global du projet", "overallDeadlineRespectScoreComment");

		HeaderColumnNameTranslateMappingStrategy<MantisImportLine> strategy = new HeaderColumnNameTranslateMappingStrategy<MantisImportLine>();
		strategy.setType(MantisImportLine.class);
		strategy.setColumnMapping(columnMapping);
		// strategy.

		// CSVParserBuilder parser = new CSVParserBuilder().;

		List<MantisImportLine> list = null;
		// CSVReader reader = new CSVReader(new InputStreamReader(new
		// ByteArrayInputStream(mantisImportDTO.getFile())));
		CsvToBeanBuilder<MantisImportLine> builder = new CsvToBeanBuilder<MantisImportLine>(
				new InputStreamReader(new ByteArrayInputStream(mantisImportDTO.getFile())));
		builder.withMappingStrategy(strategy);
		list = builder.build().parse();
		
		MantisImport mantisImport = mantisImportMapper.toEntity(mantisImportDTO);
		mantisImport.setUser(userService.getUserWithAuthorities().get());
		MantisImport mantisImportSaved = mantisImportRepository.save(mantisImport);
		//Traitement mantis import 
		list.stream().forEach(line -> {
			// Traitemant des dates
			line = parseDates(line);
			// Traitement des Doubles
			line = parseDoubles(line);
			
			//traitement de mantis 
			
			Mantis mantis = mantisRepository.findByMantisNumber(line.getMantisNumber());
			if(mantis == null) {
				mantis = new Mantis();
				mantis.setMantisNumber(line.getMantisNumber());
				mantis.setSubmissionDate(line.getSubmissionDate());
								
				if(!StringUtils.isBlank(line.getProject())) {
					Project project = projectRepository.findByName(line.getProject());
					if(project == null) {
						project = new Project();
						project.setName(line.getProject()); 
						project = projectRepository.save(project);
						//line.get
					}
					mantis.setProject(project);
				}	
				mantis = mantisRepository.save(mantis);
				//Gestion status nouvelle
				if(mantisStatusRepository.findByMantisId(mantis.getId()).isEmpty()) {
					MantisStatus status = new MantisStatus();
					status.setMantis(mantis);
					status.setStatus(new Status(1L));
					status.setChangeDate(mantisImportSaved.getImportDate());
					status.setUser(mantisImportSaved.getUser());
					mantisStatusRepository.save(status);
				}
			}
			
			line.setMantis(mantis);
			line.setMantisImport(mantisImportSaved);
			line.setReferent(mantis.getProject().getReferent());
			//Traitement de state
			State state = stateRepository.findByName(line.getStateString());
			if(state == null) {
				state = new State();
				state.setName(line.getStateString());
				state = stateRepository.save(state);
			}
			line.setState(state);
			mantisImportLineRepository.save(line);
		});
		
		// list = csvToBean.parse(strategy, reader);
		return mantisImportMapper.toDto(mantisImport);
	}

	private MantisImportLine parseDoubles(MantisImportLine line) {

		if (!StringUtils.isBlank(line.getEstimatedChargeCACFString())) {
			line.setEstimatedChargeCACF(NumberUtils.toDouble(line.getEstimatedChargeCACFString()));
		}

		if (!StringUtils.isBlank(line.getEstimatedChargeCDSString())) {
			line.setEstimatedChargeCDS(NumberUtils.toDouble(line.getEstimatedChargeCDSString()));
		}
		return line;
	}

	private MantisImportLine parseDates(MantisImportLine line) {
		String dateFormat = "yyyy-MM-dd";
		try {
			if (!StringUtils.isBlank(line.getDesiredCommitmentDateString())) {
				line.setDesiredCommitmentDate(DateUtils.parseDate(line.getDesiredCommitmentDateString(), dateFormat)
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing desiredCommitmentDateString");
		}
		try {
			if (!StringUtils.isBlank(line.getCommitmentDateCDSString())) {
				line.setCommitmentDateCDS(DateUtils.parseDate(line.getCommitmentDateCDSString(), dateFormat).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing getCommitmentDateCDSString");
		}
		try {
			if (!StringUtils.isBlank(line.getEstimatedDSTDelivreryDateString())) {
				line.setEstimatedDSTDelivreryDate(
						DateUtils.parseDate(line.getEstimatedDSTDelivreryDateString(), dateFormat).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing EstimatedDSTDelivreryDateString");
		}
		try {
			if (!StringUtils.isBlank(line.getSubmissionDateString())) {
				line.setSubmissionDate(DateUtils.parseDate(line.getSubmissionDateString(), dateFormat).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing SubmissionDate");
		}
		try {
			if (!StringUtils.isBlank(line.getRecipeDateString())) {
				line.setRecipeDate(DateUtils.parseDate(line.getRecipeDateString(), dateFormat).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing RecipeDate");
		}
		try {
			if (!StringUtils.isBlank(line.getProductionDateString())) {
				line.setProductionDate(DateUtils.parseDate(line.getProductionDateString(), dateFormat).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate());
			}
		} catch (ParseException e) {
			log.debug("Error parsing ProductionDate");
		}
		return line;
	}
}
