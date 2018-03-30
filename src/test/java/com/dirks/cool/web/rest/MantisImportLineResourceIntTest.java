package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.domain.State;
import com.dirks.cool.domain.MantisImport;
import com.dirks.cool.domain.Mantis;
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.service.MantisImportLineService;
import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.mapper.MantisImportLineMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;
import com.dirks.cool.service.dto.MantisImportLineCriteria;
import com.dirks.cool.service.MantisImportLineQueryService;

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
    private MantisImportLineQueryService mantisImportLineQueryService;

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
        final MantisImportLineResource mantisImportLineResource = new MantisImportLineResource(mantisImportLineService, mantisImportLineQueryService);
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
    public void getAllMantisImportLinesByMantisNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where mantisNumber equals to DEFAULT_MANTIS_NUMBER
        defaultMantisImportLineShouldBeFound("mantisNumber.equals=" + DEFAULT_MANTIS_NUMBER);

        // Get all the mantisImportLineList where mantisNumber equals to UPDATED_MANTIS_NUMBER
        defaultMantisImportLineShouldNotBeFound("mantisNumber.equals=" + UPDATED_MANTIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByMantisNumberIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where mantisNumber in DEFAULT_MANTIS_NUMBER or UPDATED_MANTIS_NUMBER
        defaultMantisImportLineShouldBeFound("mantisNumber.in=" + DEFAULT_MANTIS_NUMBER + "," + UPDATED_MANTIS_NUMBER);

        // Get all the mantisImportLineList where mantisNumber equals to UPDATED_MANTIS_NUMBER
        defaultMantisImportLineShouldNotBeFound("mantisNumber.in=" + UPDATED_MANTIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByMantisNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where mantisNumber is not null
        defaultMantisImportLineShouldBeFound("mantisNumber.specified=true");

        // Get all the mantisImportLineList where mantisNumber is null
        defaultMantisImportLineShouldNotBeFound("mantisNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByValidationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where validationStatus equals to DEFAULT_VALIDATION_STATUS
        defaultMantisImportLineShouldBeFound("validationStatus.equals=" + DEFAULT_VALIDATION_STATUS);

        // Get all the mantisImportLineList where validationStatus equals to UPDATED_VALIDATION_STATUS
        defaultMantisImportLineShouldNotBeFound("validationStatus.equals=" + UPDATED_VALIDATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByValidationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where validationStatus in DEFAULT_VALIDATION_STATUS or UPDATED_VALIDATION_STATUS
        defaultMantisImportLineShouldBeFound("validationStatus.in=" + DEFAULT_VALIDATION_STATUS + "," + UPDATED_VALIDATION_STATUS);

        // Get all the mantisImportLineList where validationStatus equals to UPDATED_VALIDATION_STATUS
        defaultMantisImportLineShouldNotBeFound("validationStatus.in=" + UPDATED_VALIDATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByValidationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where validationStatus is not null
        defaultMantisImportLineShouldBeFound("validationStatus.specified=true");

        // Get all the mantisImportLineList where validationStatus is null
        defaultMantisImportLineShouldNotBeFound("validationStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where project equals to DEFAULT_PROJECT
        defaultMantisImportLineShouldBeFound("project.equals=" + DEFAULT_PROJECT);

        // Get all the mantisImportLineList where project equals to UPDATED_PROJECT
        defaultMantisImportLineShouldNotBeFound("project.equals=" + UPDATED_PROJECT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProjectIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where project in DEFAULT_PROJECT or UPDATED_PROJECT
        defaultMantisImportLineShouldBeFound("project.in=" + DEFAULT_PROJECT + "," + UPDATED_PROJECT);

        // Get all the mantisImportLineList where project equals to UPDATED_PROJECT
        defaultMantisImportLineShouldNotBeFound("project.in=" + UPDATED_PROJECT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where project is not null
        defaultMantisImportLineShouldBeFound("project.specified=true");

        // Get all the mantisImportLineList where project is null
        defaultMantisImportLineShouldNotBeFound("project.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultMantisImportLineShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the mantisImportLineList where updateDate equals to UPDATED_UPDATE_DATE
        defaultMantisImportLineShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultMantisImportLineShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the mantisImportLineList where updateDate equals to UPDATED_UPDATE_DATE
        defaultMantisImportLineShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where updateDate is not null
        defaultMantisImportLineShouldBeFound("updateDate.specified=true");

        // Get all the mantisImportLineList where updateDate is null
        defaultMantisImportLineShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByUpdateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where updateDate greater than or equals to DEFAULT_UPDATE_DATE
        defaultMantisImportLineShouldBeFound("updateDate.greaterOrEqualThan=" + DEFAULT_UPDATE_DATE);

        // Get all the mantisImportLineList where updateDate greater than or equals to UPDATED_UPDATE_DATE
        defaultMantisImportLineShouldNotBeFound("updateDate.greaterOrEqualThan=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByUpdateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where updateDate less than or equals to DEFAULT_UPDATE_DATE
        defaultMantisImportLineShouldNotBeFound("updateDate.lessThan=" + DEFAULT_UPDATE_DATE);

        // Get all the mantisImportLineList where updateDate less than or equals to UPDATED_UPDATE_DATE
        defaultMantisImportLineShouldBeFound("updateDate.lessThan=" + UPDATED_UPDATE_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where category equals to DEFAULT_CATEGORY
        defaultMantisImportLineShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the mantisImportLineList where category equals to UPDATED_CATEGORY
        defaultMantisImportLineShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultMantisImportLineShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the mantisImportLineList where category equals to UPDATED_CATEGORY
        defaultMantisImportLineShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where category is not null
        defaultMantisImportLineShouldBeFound("category.specified=true");

        // Get all the mantisImportLineList where category is null
        defaultMantisImportLineShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByGravityIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where gravity equals to DEFAULT_GRAVITY
        defaultMantisImportLineShouldBeFound("gravity.equals=" + DEFAULT_GRAVITY);

        // Get all the mantisImportLineList where gravity equals to UPDATED_GRAVITY
        defaultMantisImportLineShouldNotBeFound("gravity.equals=" + UPDATED_GRAVITY);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByGravityIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where gravity in DEFAULT_GRAVITY or UPDATED_GRAVITY
        defaultMantisImportLineShouldBeFound("gravity.in=" + DEFAULT_GRAVITY + "," + UPDATED_GRAVITY);

        // Get all the mantisImportLineList where gravity equals to UPDATED_GRAVITY
        defaultMantisImportLineShouldNotBeFound("gravity.in=" + UPDATED_GRAVITY);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByGravityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where gravity is not null
        defaultMantisImportLineShouldBeFound("gravity.specified=true");

        // Get all the mantisImportLineList where gravity is null
        defaultMantisImportLineShouldNotBeFound("gravity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByAugeoReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where augeoReference equals to DEFAULT_AUGEO_REFERENCE
        defaultMantisImportLineShouldBeFound("augeoReference.equals=" + DEFAULT_AUGEO_REFERENCE);

        // Get all the mantisImportLineList where augeoReference equals to UPDATED_AUGEO_REFERENCE
        defaultMantisImportLineShouldNotBeFound("augeoReference.equals=" + UPDATED_AUGEO_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByAugeoReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where augeoReference in DEFAULT_AUGEO_REFERENCE or UPDATED_AUGEO_REFERENCE
        defaultMantisImportLineShouldBeFound("augeoReference.in=" + DEFAULT_AUGEO_REFERENCE + "," + UPDATED_AUGEO_REFERENCE);

        // Get all the mantisImportLineList where augeoReference equals to UPDATED_AUGEO_REFERENCE
        defaultMantisImportLineShouldNotBeFound("augeoReference.in=" + UPDATED_AUGEO_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByAugeoReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where augeoReference is not null
        defaultMantisImportLineShouldBeFound("augeoReference.specified=true");

        // Get all the mantisImportLineList where augeoReference is null
        defaultMantisImportLineShouldNotBeFound("augeoReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByTechnicalReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where technicalReference equals to DEFAULT_TECHNICAL_REFERENCE
        defaultMantisImportLineShouldBeFound("technicalReference.equals=" + DEFAULT_TECHNICAL_REFERENCE);

        // Get all the mantisImportLineList where technicalReference equals to UPDATED_TECHNICAL_REFERENCE
        defaultMantisImportLineShouldNotBeFound("technicalReference.equals=" + UPDATED_TECHNICAL_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByTechnicalReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where technicalReference in DEFAULT_TECHNICAL_REFERENCE or UPDATED_TECHNICAL_REFERENCE
        defaultMantisImportLineShouldBeFound("technicalReference.in=" + DEFAULT_TECHNICAL_REFERENCE + "," + UPDATED_TECHNICAL_REFERENCE);

        // Get all the mantisImportLineList where technicalReference equals to UPDATED_TECHNICAL_REFERENCE
        defaultMantisImportLineShouldNotBeFound("technicalReference.in=" + UPDATED_TECHNICAL_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByTechnicalReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where technicalReference is not null
        defaultMantisImportLineShouldBeFound("technicalReference.specified=true");

        // Get all the mantisImportLineList where technicalReference is null
        defaultMantisImportLineShouldNotBeFound("technicalReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where description equals to DEFAULT_DESCRIPTION
        defaultMantisImportLineShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mantisImportLineList where description equals to UPDATED_DESCRIPTION
        defaultMantisImportLineShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMantisImportLineShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mantisImportLineList where description equals to UPDATED_DESCRIPTION
        defaultMantisImportLineShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where description is not null
        defaultMantisImportLineShouldBeFound("description.specified=true");

        // Get all the mantisImportLineList where description is null
        defaultMantisImportLineShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesBySubmissionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where submissionDate equals to DEFAULT_SUBMISSION_DATE
        defaultMantisImportLineShouldBeFound("submissionDate.equals=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisImportLineList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultMantisImportLineShouldNotBeFound("submissionDate.equals=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesBySubmissionDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where submissionDate in DEFAULT_SUBMISSION_DATE or UPDATED_SUBMISSION_DATE
        defaultMantisImportLineShouldBeFound("submissionDate.in=" + DEFAULT_SUBMISSION_DATE + "," + UPDATED_SUBMISSION_DATE);

        // Get all the mantisImportLineList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultMantisImportLineShouldNotBeFound("submissionDate.in=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesBySubmissionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where submissionDate is not null
        defaultMantisImportLineShouldBeFound("submissionDate.specified=true");

        // Get all the mantisImportLineList where submissionDate is null
        defaultMantisImportLineShouldNotBeFound("submissionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesBySubmissionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where submissionDate greater than or equals to DEFAULT_SUBMISSION_DATE
        defaultMantisImportLineShouldBeFound("submissionDate.greaterOrEqualThan=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisImportLineList where submissionDate greater than or equals to UPDATED_SUBMISSION_DATE
        defaultMantisImportLineShouldNotBeFound("submissionDate.greaterOrEqualThan=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesBySubmissionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where submissionDate less than or equals to DEFAULT_SUBMISSION_DATE
        defaultMantisImportLineShouldNotBeFound("submissionDate.lessThan=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisImportLineList where submissionDate less than or equals to UPDATED_SUBMISSION_DATE
        defaultMantisImportLineShouldBeFound("submissionDate.lessThan=" + UPDATED_SUBMISSION_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByDesiredCommitmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where desiredCommitmentDate equals to DEFAULT_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldBeFound("desiredCommitmentDate.equals=" + DEFAULT_DESIRED_COMMITMENT_DATE);

        // Get all the mantisImportLineList where desiredCommitmentDate equals to UPDATED_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldNotBeFound("desiredCommitmentDate.equals=" + UPDATED_DESIRED_COMMITMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDesiredCommitmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where desiredCommitmentDate in DEFAULT_DESIRED_COMMITMENT_DATE or UPDATED_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldBeFound("desiredCommitmentDate.in=" + DEFAULT_DESIRED_COMMITMENT_DATE + "," + UPDATED_DESIRED_COMMITMENT_DATE);

        // Get all the mantisImportLineList where desiredCommitmentDate equals to UPDATED_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldNotBeFound("desiredCommitmentDate.in=" + UPDATED_DESIRED_COMMITMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDesiredCommitmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where desiredCommitmentDate is not null
        defaultMantisImportLineShouldBeFound("desiredCommitmentDate.specified=true");

        // Get all the mantisImportLineList where desiredCommitmentDate is null
        defaultMantisImportLineShouldNotBeFound("desiredCommitmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDesiredCommitmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where desiredCommitmentDate greater than or equals to DEFAULT_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldBeFound("desiredCommitmentDate.greaterOrEqualThan=" + DEFAULT_DESIRED_COMMITMENT_DATE);

        // Get all the mantisImportLineList where desiredCommitmentDate greater than or equals to UPDATED_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldNotBeFound("desiredCommitmentDate.greaterOrEqualThan=" + UPDATED_DESIRED_COMMITMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDesiredCommitmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where desiredCommitmentDate less than or equals to DEFAULT_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldNotBeFound("desiredCommitmentDate.lessThan=" + DEFAULT_DESIRED_COMMITMENT_DATE);

        // Get all the mantisImportLineList where desiredCommitmentDate less than or equals to UPDATED_DESIRED_COMMITMENT_DATE
        defaultMantisImportLineShouldBeFound("desiredCommitmentDate.lessThan=" + UPDATED_DESIRED_COMMITMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCACFIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCACF equals to DEFAULT_ESTIMATED_CHARGE_CACF
        defaultMantisImportLineShouldBeFound("estimatedChargeCACF.equals=" + DEFAULT_ESTIMATED_CHARGE_CACF);

        // Get all the mantisImportLineList where estimatedChargeCACF equals to UPDATED_ESTIMATED_CHARGE_CACF
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCACF.equals=" + UPDATED_ESTIMATED_CHARGE_CACF);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCACFIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCACF in DEFAULT_ESTIMATED_CHARGE_CACF or UPDATED_ESTIMATED_CHARGE_CACF
        defaultMantisImportLineShouldBeFound("estimatedChargeCACF.in=" + DEFAULT_ESTIMATED_CHARGE_CACF + "," + UPDATED_ESTIMATED_CHARGE_CACF);

        // Get all the mantisImportLineList where estimatedChargeCACF equals to UPDATED_ESTIMATED_CHARGE_CACF
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCACF.in=" + UPDATED_ESTIMATED_CHARGE_CACF);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCACFIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCACF is not null
        defaultMantisImportLineShouldBeFound("estimatedChargeCACF.specified=true");

        // Get all the mantisImportLineList where estimatedChargeCACF is null
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCACF.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCommitmentDateCDSIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where commitmentDateCDS equals to DEFAULT_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldBeFound("commitmentDateCDS.equals=" + DEFAULT_COMMITMENT_DATE_CDS);

        // Get all the mantisImportLineList where commitmentDateCDS equals to UPDATED_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldNotBeFound("commitmentDateCDS.equals=" + UPDATED_COMMITMENT_DATE_CDS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCommitmentDateCDSIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where commitmentDateCDS in DEFAULT_COMMITMENT_DATE_CDS or UPDATED_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldBeFound("commitmentDateCDS.in=" + DEFAULT_COMMITMENT_DATE_CDS + "," + UPDATED_COMMITMENT_DATE_CDS);

        // Get all the mantisImportLineList where commitmentDateCDS equals to UPDATED_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldNotBeFound("commitmentDateCDS.in=" + UPDATED_COMMITMENT_DATE_CDS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCommitmentDateCDSIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where commitmentDateCDS is not null
        defaultMantisImportLineShouldBeFound("commitmentDateCDS.specified=true");

        // Get all the mantisImportLineList where commitmentDateCDS is null
        defaultMantisImportLineShouldNotBeFound("commitmentDateCDS.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCommitmentDateCDSIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where commitmentDateCDS greater than or equals to DEFAULT_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldBeFound("commitmentDateCDS.greaterOrEqualThan=" + DEFAULT_COMMITMENT_DATE_CDS);

        // Get all the mantisImportLineList where commitmentDateCDS greater than or equals to UPDATED_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldNotBeFound("commitmentDateCDS.greaterOrEqualThan=" + UPDATED_COMMITMENT_DATE_CDS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByCommitmentDateCDSIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where commitmentDateCDS less than or equals to DEFAULT_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldNotBeFound("commitmentDateCDS.lessThan=" + DEFAULT_COMMITMENT_DATE_CDS);

        // Get all the mantisImportLineList where commitmentDateCDS less than or equals to UPDATED_COMMITMENT_DATE_CDS
        defaultMantisImportLineShouldBeFound("commitmentDateCDS.lessThan=" + UPDATED_COMMITMENT_DATE_CDS);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCDSIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCDS equals to DEFAULT_ESTIMATED_CHARGE_CDS
        defaultMantisImportLineShouldBeFound("estimatedChargeCDS.equals=" + DEFAULT_ESTIMATED_CHARGE_CDS);

        // Get all the mantisImportLineList where estimatedChargeCDS equals to UPDATED_ESTIMATED_CHARGE_CDS
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCDS.equals=" + UPDATED_ESTIMATED_CHARGE_CDS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCDSIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCDS in DEFAULT_ESTIMATED_CHARGE_CDS or UPDATED_ESTIMATED_CHARGE_CDS
        defaultMantisImportLineShouldBeFound("estimatedChargeCDS.in=" + DEFAULT_ESTIMATED_CHARGE_CDS + "," + UPDATED_ESTIMATED_CHARGE_CDS);

        // Get all the mantisImportLineList where estimatedChargeCDS equals to UPDATED_ESTIMATED_CHARGE_CDS
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCDS.in=" + UPDATED_ESTIMATED_CHARGE_CDS);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedChargeCDSIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedChargeCDS is not null
        defaultMantisImportLineShouldBeFound("estimatedChargeCDS.specified=true");

        // Get all the mantisImportLineList where estimatedChargeCDS is null
        defaultMantisImportLineShouldNotBeFound("estimatedChargeCDS.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedDSTDelivreryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate equals to DEFAULT_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldBeFound("estimatedDSTDelivreryDate.equals=" + DEFAULT_ESTIMATED_DST_DELIVRERY_DATE);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate equals to UPDATED_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldNotBeFound("estimatedDSTDelivreryDate.equals=" + UPDATED_ESTIMATED_DST_DELIVRERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedDSTDelivreryDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate in DEFAULT_ESTIMATED_DST_DELIVRERY_DATE or UPDATED_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldBeFound("estimatedDSTDelivreryDate.in=" + DEFAULT_ESTIMATED_DST_DELIVRERY_DATE + "," + UPDATED_ESTIMATED_DST_DELIVRERY_DATE);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate equals to UPDATED_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldNotBeFound("estimatedDSTDelivreryDate.in=" + UPDATED_ESTIMATED_DST_DELIVRERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedDSTDelivreryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate is not null
        defaultMantisImportLineShouldBeFound("estimatedDSTDelivreryDate.specified=true");

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate is null
        defaultMantisImportLineShouldNotBeFound("estimatedDSTDelivreryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedDSTDelivreryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate greater than or equals to DEFAULT_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldBeFound("estimatedDSTDelivreryDate.greaterOrEqualThan=" + DEFAULT_ESTIMATED_DST_DELIVRERY_DATE);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate greater than or equals to UPDATED_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldNotBeFound("estimatedDSTDelivreryDate.greaterOrEqualThan=" + UPDATED_ESTIMATED_DST_DELIVRERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByEstimatedDSTDelivreryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate less than or equals to DEFAULT_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldNotBeFound("estimatedDSTDelivreryDate.lessThan=" + DEFAULT_ESTIMATED_DST_DELIVRERY_DATE);

        // Get all the mantisImportLineList where estimatedDSTDelivreryDate less than or equals to UPDATED_ESTIMATED_DST_DELIVRERY_DATE
        defaultMantisImportLineShouldBeFound("estimatedDSTDelivreryDate.lessThan=" + UPDATED_ESTIMATED_DST_DELIVRERY_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByRecipeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where recipeDate equals to DEFAULT_RECIPE_DATE
        defaultMantisImportLineShouldBeFound("recipeDate.equals=" + DEFAULT_RECIPE_DATE);

        // Get all the mantisImportLineList where recipeDate equals to UPDATED_RECIPE_DATE
        defaultMantisImportLineShouldNotBeFound("recipeDate.equals=" + UPDATED_RECIPE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByRecipeDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where recipeDate in DEFAULT_RECIPE_DATE or UPDATED_RECIPE_DATE
        defaultMantisImportLineShouldBeFound("recipeDate.in=" + DEFAULT_RECIPE_DATE + "," + UPDATED_RECIPE_DATE);

        // Get all the mantisImportLineList where recipeDate equals to UPDATED_RECIPE_DATE
        defaultMantisImportLineShouldNotBeFound("recipeDate.in=" + UPDATED_RECIPE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByRecipeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where recipeDate is not null
        defaultMantisImportLineShouldBeFound("recipeDate.specified=true");

        // Get all the mantisImportLineList where recipeDate is null
        defaultMantisImportLineShouldNotBeFound("recipeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByRecipeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where recipeDate greater than or equals to DEFAULT_RECIPE_DATE
        defaultMantisImportLineShouldBeFound("recipeDate.greaterOrEqualThan=" + DEFAULT_RECIPE_DATE);

        // Get all the mantisImportLineList where recipeDate greater than or equals to UPDATED_RECIPE_DATE
        defaultMantisImportLineShouldNotBeFound("recipeDate.greaterOrEqualThan=" + UPDATED_RECIPE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByRecipeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where recipeDate less than or equals to DEFAULT_RECIPE_DATE
        defaultMantisImportLineShouldNotBeFound("recipeDate.lessThan=" + DEFAULT_RECIPE_DATE);

        // Get all the mantisImportLineList where recipeDate less than or equals to UPDATED_RECIPE_DATE
        defaultMantisImportLineShouldBeFound("recipeDate.lessThan=" + UPDATED_RECIPE_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByProductionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where productionDate equals to DEFAULT_PRODUCTION_DATE
        defaultMantisImportLineShouldBeFound("productionDate.equals=" + DEFAULT_PRODUCTION_DATE);

        // Get all the mantisImportLineList where productionDate equals to UPDATED_PRODUCTION_DATE
        defaultMantisImportLineShouldNotBeFound("productionDate.equals=" + UPDATED_PRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProductionDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where productionDate in DEFAULT_PRODUCTION_DATE or UPDATED_PRODUCTION_DATE
        defaultMantisImportLineShouldBeFound("productionDate.in=" + DEFAULT_PRODUCTION_DATE + "," + UPDATED_PRODUCTION_DATE);

        // Get all the mantisImportLineList where productionDate equals to UPDATED_PRODUCTION_DATE
        defaultMantisImportLineShouldNotBeFound("productionDate.in=" + UPDATED_PRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProductionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where productionDate is not null
        defaultMantisImportLineShouldBeFound("productionDate.specified=true");

        // Get all the mantisImportLineList where productionDate is null
        defaultMantisImportLineShouldNotBeFound("productionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProductionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where productionDate greater than or equals to DEFAULT_PRODUCTION_DATE
        defaultMantisImportLineShouldBeFound("productionDate.greaterOrEqualThan=" + DEFAULT_PRODUCTION_DATE);

        // Get all the mantisImportLineList where productionDate greater than or equals to UPDATED_PRODUCTION_DATE
        defaultMantisImportLineShouldNotBeFound("productionDate.greaterOrEqualThan=" + UPDATED_PRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByProductionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where productionDate less than or equals to DEFAULT_PRODUCTION_DATE
        defaultMantisImportLineShouldNotBeFound("productionDate.lessThan=" + DEFAULT_PRODUCTION_DATE);

        // Get all the mantisImportLineList where productionDate less than or equals to UPDATED_PRODUCTION_DATE
        defaultMantisImportLineShouldBeFound("productionDate.lessThan=" + UPDATED_PRODUCTION_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScore equals to DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScore.equals=" + DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE);

        // Get all the mantisImportLineList where devStandardsComplianceScore equals to UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScore.equals=" + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScore in DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE or UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScore.in=" + DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE + "," + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE);

        // Get all the mantisImportLineList where devStandardsComplianceScore equals to UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScore.in=" + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScore is not null
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScore.specified=true");

        // Get all the mantisImportLineList where devStandardsComplianceScore is null
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment equals to DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScoreComment.equals=" + DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment equals to UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScoreComment.equals=" + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreCommentIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment in DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT or UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScoreComment.in=" + DEFAULT_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT + "," + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment equals to UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScoreComment.in=" + UPDATED_DEV_STANDARDS_COMPLIANCE_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByDevStandardsComplianceScoreCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment is not null
        defaultMantisImportLineShouldBeFound("devStandardsComplianceScoreComment.specified=true");

        // Get all the mantisImportLineList where devStandardsComplianceScoreComment is null
        defaultMantisImportLineShouldNotBeFound("devStandardsComplianceScoreComment.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScore equals to DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScore.equals=" + DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE);

        // Get all the mantisImportLineList where expressedNeedsComplianceScore equals to UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScore.equals=" + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScore in DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE or UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScore.in=" + DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE + "," + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE);

        // Get all the mantisImportLineList where expressedNeedsComplianceScore equals to UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScore.in=" + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScore is not null
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScore.specified=true");

        // Get all the mantisImportLineList where expressedNeedsComplianceScore is null
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment equals to DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScoreComment.equals=" + DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment equals to UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScoreComment.equals=" + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreCommentIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment in DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT or UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScoreComment.in=" + DEFAULT_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT + "," + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment equals to UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScoreComment.in=" + UPDATED_EXPRESSED_NEEDS_COMPLIANCE_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByExpressedNeedsComplianceScoreCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment is not null
        defaultMantisImportLineShouldBeFound("expressedNeedsComplianceScoreComment.specified=true");

        // Get all the mantisImportLineList where expressedNeedsComplianceScoreComment is null
        defaultMantisImportLineShouldNotBeFound("expressedNeedsComplianceScoreComment.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScore equals to DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScore.equals=" + DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE);

        // Get all the mantisImportLineList where overallDeadlineRespectScore equals to UPDATED_OVERALL_DEADLINE_RESPECT_SCORE
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScore.equals=" + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScore in DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE or UPDATED_OVERALL_DEADLINE_RESPECT_SCORE
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScore.in=" + DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE + "," + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE);

        // Get all the mantisImportLineList where overallDeadlineRespectScore equals to UPDATED_OVERALL_DEADLINE_RESPECT_SCORE
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScore.in=" + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScore is not null
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScore.specified=true");

        // Get all the mantisImportLineList where overallDeadlineRespectScore is null
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment equals to DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScoreComment.equals=" + DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment equals to UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScoreComment.equals=" + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreCommentIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment in DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT or UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScoreComment.in=" + DEFAULT_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT + "," + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment equals to UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScoreComment.in=" + UPDATED_OVERALL_DEADLINE_RESPECT_SCORE_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByOverallDeadlineRespectScoreCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportLineRepository.saveAndFlush(mantisImportLine);

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment is not null
        defaultMantisImportLineShouldBeFound("overallDeadlineRespectScoreComment.specified=true");

        // Get all the mantisImportLineList where overallDeadlineRespectScoreComment is null
        defaultMantisImportLineShouldNotBeFound("overallDeadlineRespectScoreComment.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportLinesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        State state = StateResourceIntTest.createEntity(em);
        em.persist(state);
        em.flush();
        mantisImportLine.setState(state);
        mantisImportLineRepository.saveAndFlush(mantisImportLine);
        Long stateId = state.getId();

        // Get all the mantisImportLineList where state equals to stateId
        defaultMantisImportLineShouldBeFound("stateId.equals=" + stateId);

        // Get all the mantisImportLineList where state equals to stateId + 1
        defaultMantisImportLineShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByMantisImportIsEqualToSomething() throws Exception {
        // Initialize the database
        MantisImport mantisImport = MantisImportResourceIntTest.createEntity(em);
        em.persist(mantisImport);
        em.flush();
        mantisImportLine.setMantisImport(mantisImport);
        mantisImportLineRepository.saveAndFlush(mantisImportLine);
        Long mantisImportId = mantisImport.getId();

        // Get all the mantisImportLineList where mantisImport equals to mantisImportId
        defaultMantisImportLineShouldBeFound("mantisImportId.equals=" + mantisImportId);

        // Get all the mantisImportLineList where mantisImport equals to mantisImportId + 1
        defaultMantisImportLineShouldNotBeFound("mantisImportId.equals=" + (mantisImportId + 1));
    }


    @Test
    @Transactional
    public void getAllMantisImportLinesByMantisIsEqualToSomething() throws Exception {
        // Initialize the database
        Mantis mantis = MantisResourceIntTest.createEntity(em);
        em.persist(mantis);
        em.flush();
        mantisImportLine.setMantis(mantis);
        mantisImportLineRepository.saveAndFlush(mantisImportLine);
        Long mantisId = mantis.getId();

        // Get all the mantisImportLineList where mantis equals to mantisId
        defaultMantisImportLineShouldBeFound("mantisId.equals=" + mantisId);

        // Get all the mantisImportLineList where mantis equals to mantisId + 1
        defaultMantisImportLineShouldNotBeFound("mantisId.equals=" + (mantisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMantisImportLineShouldBeFound(String filter) throws Exception {
        restMantisImportLineMockMvc.perform(get("/api/mantis-import-lines?sort=id,desc&" + filter))
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

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMantisImportLineShouldNotBeFound(String filter) throws Exception {
        restMantisImportLineMockMvc.perform(get("/api/mantis-import-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
