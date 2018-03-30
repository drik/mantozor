package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.service.MantisImportLineService;
import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.mapper.MantisImportLineMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.dirks.cool.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MantisImportLineResource REST controller.
 *
 * @see MantisImportLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisImportLineResourceIntTest {

    private static final String DEFAULT_MANTIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MANTIS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_GRAVITY = "AAAAAAAAAA";
    private static final String UPDATED_GRAVITY = "BBBBBBBBBB";

    private static final String DEFAULT_AUGEO_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_AUGEO_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DESIRED_COMMITMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DESIRED_COMMITMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ESTIMATED_CHARGE_CACF = 1D;
    private static final Double UPDATED_ESTIMATED_CHARGE_CACF = 2D;

    private static final LocalDate DEFAULT_COMMITMENT_DATE_CDS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMITMENT_DATE_CDS = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ESTIMATED_CHARGE_CDS = 1D;
    private static final Double UPDATED_ESTIMATED_CHARGE_CDS = 2D;

    private static final LocalDate DEFAULT_ESTIMATED_DST_DELIVRERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_DST_DELIVRERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECIPE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECIPE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRODUCTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_OVERALL_DEADLINE_RESPECT_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT = "BBBBBBBBBB";

    @Autowired
    private MantisImportLineRepository mantisImportLineRepository;

    @Autowired
    private MantisImportLineMapper mantisImportLineMapper;

    @Autowired
    private MantisImportLineService mantisImportLineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisImportLineMockMvc;

    private MantisImportLine mantisImportLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisImportLineResource mantisImportLineResource = new MantisImportLineResource(mantisImportLineService);
        this.restMantisImportLineMockMvc = MockMvcBuilders.standaloneSetup(mantisImportLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MantisImportLine createEntity(EntityManager em) {
        MantisImportLine mantisImportLine = new MantisImportLine()
            .mantisNumber(DEFAULT_MANTIS_NUMBER)
            .validationStatus(DEFAULT_VALIDATION_STATUS)
            .project(DEFAULT_PROJECT)
            .updateDate(DEFAULT_UPDATE_DATE)
            .category(DEFAULT_CATEGORY)
            .gravity(DEFAULT_GRAVITY)
            .augeoReference(DEFAULT_AUGEO_REFERENCE)
            .technicalReference(DEFAULT_TECHNICAL_REFERENCE)
            .state(DEFAULT_STATE)
            .description(DEFAULT_DESCRIPTION)
            .submissionDate(DEFAULT_SUBMISSION_DATE)
            .desiredCommitmentDate(DEFAULT_DESIRED_COMMITMENT_DATE)
            .estimatedChargeCACF(DEFAULT_ESTIMATED_CHARGE_CACF)
            .commitmentDateCDS(DEFAULT_COMMITMENT_DATE_CDS)
            .estimatedChargeCDS(DEFAULT_ESTIMATED_CHARGE_CDS)
            .estimatedDSTDelivreryDate(DEFAULT_ESTIMATED_DST_DELIVRERY_DATE)
            .recipeDate(DEFAULT_RECIPE_DATE)
            .productionDate(DEFAULT_PRODUCTION_DATE)
            .devStandardsComplianceScore(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE)
            .devStandardsComplianceScoreComment(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT)
            .expressedNeedsComplianceScore(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE)
            .expressedNeedsComplianceScoreComment(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT)
            .overallDeadlineRespectScore(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE)
            .overallDeadlineRespectScoreComment(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
        return mantisImportLine;
    }

    @Before
    public void initTest() {
        mantisImportLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantisImportLine() throws Exception {
        int databaseSizeBeforeCreate = mantisImportLineRepository.findAll().size();

        // Create the MantisImportLine
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineMapper.toDto(mantisImportLine);
        restMantisImportLineMockMvc.perform(post("/api/mantis-import-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisImportLine in the database
        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeCreate + 1);
        MantisImportLine testMantisImportLine = mantisImportLineList.get(mantisImportLineList.size() - 1);
        assertThat(testMantisImportLine.getMantisNumber()).isEqualTo(DEFAULT_MANTIS_NUMBER);
        assertThat(testMantisImportLine.getValidationStatus()).isEqualTo(DEFAULT_VALIDATION_STATUS);
        assertThat(testMantisImportLine.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testMantisImportLine.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMantisImportLine.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMantisImportLine.getGravity()).isEqualTo(DEFAULT_GRAVITY);
        assertThat(testMantisImportLine.getAugeoReference()).isEqualTo(DEFAULT_AUGEO_REFERENCE);
        assertThat(testMantisImportLine.getTechnicalReference()).isEqualTo(DEFAULT_TECHNICAL_REFERENCE);
        assertThat(testMantisImportLine.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testMantisImportLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMantisImportLine.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testMantisImportLine.getDesiredCommitmentDate()).isEqualTo(DEFAULT_DESIRED_COMMITMENT_DATE);
        assertThat(testMantisImportLine.getEstimatedChargeCACF()).isEqualTo(DEFAULT_ESTIMATED_CHARGE_CACF);
        assertThat(testMantisImportLine.getCommitmentDateCDS()).isEqualTo(DEFAULT_COMMITMENT_DATE_CDS);
        assertThat(testMantisImportLine.getEstimatedChargeCDS()).isEqualTo(DEFAULT_ESTIMATED_CHARGE_CDS);
        assertThat(testMantisImportLine.getEstimatedDSTDelivreryDate()).isEqualTo(DEFAULT_ESTIMATED_DST_DELIVRERY_DATE);
        assertThat(testMantisImportLine.getRecipeDate()).isEqualTo(DEFAULT_RECIPE_DATE);
        assertThat(testMantisImportLine.getProductionDate()).isEqualTo(DEFAULT_PRODUCTION_DATE);
        assertThat(testMantisImportLine.getDevStandardsComplianceScore()).isEqualTo(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE);
        assertThat(testMantisImportLine.getDevStandardsComplianceScoreComment()).isEqualTo(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);
        assertThat(testMantisImportLine.getExpressedNeedsComplianceScore()).isEqualTo(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE);
        assertThat(testMantisImportLine.getExpressedNeedsComplianceScoreComment()).isEqualTo(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);
        assertThat(testMantisImportLine.getOverallDeadlineRespectScore()).isEqualTo(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE);
        assertThat(testMantisImportLine.getOverallDeadlineRespectScoreComment()).isEqualTo(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void createMantisImportLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisImportLineRepository.findAll().size();

        // Create the MantisImportLine with an existing ID
        mantisImportLine.setId(1L);
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineMapper.toDto(mantisImportLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisImportLineMockMvc.perform(post("/api/mantis-import-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MantisImportLine in the database
        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMantisNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisImportLineRepository.findAll().size();
        // set the field null
        mantisImportLine.setMantisNumber(null);

        // Create the MantisImportLine, which fails.
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineMapper.toDto(mantisImportLine);

        restMantisImportLineMockMvc.perform(post("/api/mantis-import-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportLineDTO)))
            .andExpect(status().isBadRequest());

        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMantisImportLines() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList
        restMantisImportLineMockMvc.perform(get("/api/mantis-import-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisImportLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].mantisNumber").value(hasItem(DEFAULT_MANTIS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].validationStatus").value(hasItem(DEFAULT_VALIDATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].gravity").value(hasItem(DEFAULT_GRAVITY.toString())))
            .andExpect(jsonPath("$.[*].augeoReference").value(hasItem(DEFAULT_AUGEO_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].technicalReference").value(hasItem(DEFAULT_TECHNICAL_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].desiredCommitmentDate").value(hasItem(DEFAULT_DESIRED_COMMITMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedChargeCACF").value(hasItem(DEFAULT_ESTIMATED_CHARGE_CACF.doubleValue())))
            .andExpect(jsonPath("$.[*].commitmentDateCDS").value(hasItem(DEFAULT_COMMITMENT_DATE_CDS.toString())))
            .andExpect(jsonPath("$.[*].estimatedChargeCDS").value(hasItem(DEFAULT_ESTIMATED_CHARGE_CDS.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatedDSTDelivreryDate").value(hasItem(DEFAULT_ESTIMATED_DST_DELIVRERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipeDate").value(hasItem(DEFAULT_RECIPE_DATE.toString())))
            .andExpect(jsonPath("$.[*].productionDate").value(hasItem(DEFAULT_PRODUCTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].devStandardsComplianceScore").value(hasItem(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE.toString())))
            .andExpect(jsonPath("$.[*].devStandardsComplianceScoreComment").value(hasItem(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].expressedNeedsComplianceScore").value(hasItem(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE.toString())))
            .andExpect(jsonPath("$.[*].expressedNeedsComplianceScoreComment").value(hasItem(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].overallDeadlineRespectScore").value(hasItem(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE.toString())))
            .andExpect(jsonPath("$.[*].overallDeadlineRespectScoreComment").value(hasItem(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getMantisImportLine() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get the mantisImportLine
        restMantisImportLineMockMvc.perform(get("/api/mantis-import-lines/{id}", mantisImportLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantisImportLine.getId().intValue()))
            .andExpect(jsonPath("$.mantisNumber").value(DEFAULT_MANTIS_NUMBER.toString()))
            .andExpect(jsonPath("$.validationStatus").value(DEFAULT_VALIDATION_STATUS.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.gravity").value(DEFAULT_GRAVITY.toString()))
            .andExpect(jsonPath("$.augeoReference").value(DEFAULT_AUGEO_REFERENCE.toString()))
            .andExpect(jsonPath("$.technicalReference").value(DEFAULT_TECHNICAL_REFERENCE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.submissionDate").value(DEFAULT_SUBMISSION_DATE.toString()))
            .andExpect(jsonPath("$.desiredCommitmentDate").value(DEFAULT_DESIRED_COMMITMENT_DATE.toString()))
            .andExpect(jsonPath("$.estimatedChargeCACF").value(DEFAULT_ESTIMATED_CHARGE_CACF.doubleValue()))
            .andExpect(jsonPath("$.commitmentDateCDS").value(DEFAULT_COMMITMENT_DATE_CDS.toString()))
            .andExpect(jsonPath("$.estimatedChargeCDS").value(DEFAULT_ESTIMATED_CHARGE_CDS.doubleValue()))
            .andExpect(jsonPath("$.estimatedDSTDelivreryDate").value(DEFAULT_ESTIMATED_DST_DELIVRERY_DATE.toString()))
            .andExpect(jsonPath("$.recipeDate").value(DEFAULT_RECIPE_DATE.toString()))
            .andExpect(jsonPath("$.productionDate").value(DEFAULT_PRODUCTION_DATE.toString()))
            .andExpect(jsonPath("$.devStandardsComplianceScore").value(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE.toString()))
            .andExpect(jsonPath("$.devStandardsComplianceScoreComment").value(DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT.toString()))
            .andExpect(jsonPath("$.expressedNeedsComplianceScore").value(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE.toString()))
            .andExpect(jsonPath("$.expressedNeedsComplianceScoreComment").value(DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT.toString()))
            .andExpect(jsonPath("$.overallDeadlineRespectScore").value(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE.toString()))
            .andExpect(jsonPath("$.overallDeadlineRespectScoreComment").value(DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMantisImportLine() throws Exception {
        // Get the mantisImportLine
        restMantisImportLineMockMvc.perform(get("/api/mantis-import-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantisImportLine() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);
        int databaseSizeBeforeUpdate = mantisImportLineRepository.findAll().size();

        // Update the mantisImportLine
        MantisImportLine updatedMantisImportLine = mantisImportLineRepository.findOne(mantisImportLine.getId());
        // Disconnect from session so that the updates on updatedMantisImportLine are not directly saved in db
        em.detach(updatedMantisImportLine);
        updatedMantisImportLine
            .mantisNumber(UPDATED_MANTIS_NUMBER)
            .validationStatus(UPDATED_VALIDATION_STATUS)
            .project(UPDATED_PROJECT)
            .updateDate(UPDATED_UPDATE_DATE)
            .category(UPDATED_CATEGORY)
            .gravity(UPDATED_GRAVITY)
            .augeoReference(UPDATED_AUGEO_REFERENCE)
            .technicalReference(UPDATED_TECHNICAL_REFERENCE)
            .state(UPDATED_STATE)
            .description(UPDATED_DESCRIPTION)
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .desiredCommitmentDate(UPDATED_DESIRED_COMMITMENT_DATE)
            .estimatedChargeCACF(UPDATED_ESTIMATED_CHARGE_CACF)
            .commitmentDateCDS(UPDATED_COMMITMENT_DATE_CDS)
            .estimatedChargeCDS(UPDATED_ESTIMATED_CHARGE_CDS)
            .estimatedDSTDelivreryDate(UPDATED_ESTIMATED_DST_DELIVRERY_DATE)
            .recipeDate(UPDATED_RECIPE_DATE)
            .productionDate(UPDATED_PRODUCTION_DATE)
            .devStandardsComplianceScore(UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE)
            .devStandardsComplianceScoreComment(UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT)
            .expressedNeedsComplianceScore(UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE)
            .expressedNeedsComplianceScoreComment(UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT)
            .overallDeadlineRespectScore(UPDATED_OVERALL_DEADLINE_RESPECT_SCORE)
            .overallDeadlineRespectScoreComment(UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineMapper.toDto(updatedMantisImportLine);

        restMantisImportLineMockMvc.perform(put("/api/mantis-import-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportLineDTO)))
            .andExpect(status().isOk());

        // Validate the MantisImportLine in the database
        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeUpdate);
        MantisImportLine testMantisImportLine = mantisImportLineList.get(mantisImportLineList.size() - 1);
        assertThat(testMantisImportLine.getMantisNumber()).isEqualTo(UPDATED_MANTIS_NUMBER);
        assertThat(testMantisImportLine.getValidationStatus()).isEqualTo(UPDATED_VALIDATION_STATUS);
        assertThat(testMantisImportLine.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testMantisImportLine.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMantisImportLine.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMantisImportLine.getGravity()).isEqualTo(UPDATED_GRAVITY);
        assertThat(testMantisImportLine.getAugeoReference()).isEqualTo(UPDATED_AUGEO_REFERENCE);
        assertThat(testMantisImportLine.getTechnicalReference()).isEqualTo(UPDATED_TECHNICAL_REFERENCE);
        assertThat(testMantisImportLine.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testMantisImportLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMantisImportLine.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testMantisImportLine.getDesiredCommitmentDate()).isEqualTo(UPDATED_DESIRED_COMMITMENT_DATE);
        assertThat(testMantisImportLine.getEstimatedChargeCACF()).isEqualTo(UPDATED_ESTIMATED_CHARGE_CACF);
        assertThat(testMantisImportLine.getCommitmentDateCDS()).isEqualTo(UPDATED_COMMITMENT_DATE_CDS);
        assertThat(testMantisImportLine.getEstimatedChargeCDS()).isEqualTo(UPDATED_ESTIMATED_CHARGE_CDS);
        assertThat(testMantisImportLine.getEstimatedDSTDelivreryDate()).isEqualTo(UPDATED_ESTIMATED_DST_DELIVRERY_DATE);
        assertThat(testMantisImportLine.getRecipeDate()).isEqualTo(UPDATED_RECIPE_DATE);
        assertThat(testMantisImportLine.getProductionDate()).isEqualTo(UPDATED_PRODUCTION_DATE);
        assertThat(testMantisImportLine.getDevStandardsComplianceScore()).isEqualTo(UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE);
        assertThat(testMantisImportLine.getDevStandardsComplianceScoreComment()).isEqualTo(UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);
        assertThat(testMantisImportLine.getExpressedNeedsComplianceScore()).isEqualTo(UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE);
        assertThat(testMantisImportLine.getExpressedNeedsComplianceScoreComment()).isEqualTo(UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);
        assertThat(testMantisImportLine.getOverallDeadlineRespectScore()).isEqualTo(UPDATED_OVERALL_DEADLINE_RESPECT_SCORE);
        assertThat(testMantisImportLine.getOverallDeadlineRespectScoreComment()).isEqualTo(UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMantisImportLine() throws Exception {
        int databaseSizeBeforeUpdate = mantisImportLineRepository.findAll().size();

        // Create the MantisImportLine
        MantisImportLineDTO mantisImportLineDTO = mantisImportLineMapper.toDto(mantisImportLine);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisImportLineMockMvc.perform(put("/api/mantis-import-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisImportLine in the database
        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantisImportLine() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);
        int databaseSizeBeforeDelete = mantisImportLineRepository.findAll().size();

        // Get the mantisImportLine
        restMantisImportLineMockMvc.perform(delete("/api/mantis-import-lines/{id}", mantisImportLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MantisImportLine> mantisImportLineList = mantisImportLineRepository.findAll();
        assertThat(mantisImportLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisImportLine.class);
        MantisImportLine mantisImportLine1 = new MantisImportLine();
        mantisImportLine1.setId(1L);
        MantisImportLine mantisImportLine2 = new MantisImportLine();
        mantisImportLine2.setId(mantisImportLine1.getId());
        assertThat(mantisImportLine1).isEqualTo(mantisImportLine2);
        mantisImportLine2.setId(2L);
        assertThat(mantisImportLine1).isNotEqualTo(mantisImportLine2);
        mantisImportLine1.setId(null);
        assertThat(mantisImportLine1).isNotEqualTo(mantisImportLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisImportLineDTO.class);
        MantisImportLineDTO mantisImportLineDTO1 = new MantisImportLineDTO();
        mantisImportLineDTO1.setId(1L);
        MantisImportLineDTO mantisImportLineDTO2 = new MantisImportLineDTO();
        assertThat(mantisImportLineDTO1).isNotEqualTo(mantisImportLineDTO2);
        mantisImportLineDTO2.setId(mantisImportLineDTO1.getId());
        assertThat(mantisImportLineDTO1).isEqualTo(mantisImportLineDTO2);
        mantisImportLineDTO2.setId(2L);
        assertThat(mantisImportLineDTO1).isNotEqualTo(mantisImportLineDTO2);
        mantisImportLineDTO1.setId(null);
        assertThat(mantisImportLineDTO1).isNotEqualTo(mantisImportLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisImportLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisImportLineMapper.fromId(null)).isNull();
    }
}
