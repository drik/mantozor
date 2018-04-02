package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.Project;
import com.dirks.cool.repository.MantisRepository;
import com.dirks.cool.service.MantisService;
import com.dirks.cool.service.dto.MantisDTO;
import com.dirks.cool.service.mapper.MantisMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;
import com.dirks.cool.service.dto.MantisCriteria;
import com.dirks.cool.service.MantisQueryService;

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
 * Test class for the MantisResource REST controller.
 *
 * @see MantisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisResourceIntTest {

    private static final String DEFAULT_MANTIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MANTIS_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MantisRepository mantisRepository;

    @Autowired
    private MantisMapper mantisMapper;

    @Autowired
    private MantisService mantisService;

    @Autowired
    private MantisQueryService mantisQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisMockMvc;

    private Mantis mantis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisResource mantisResource = new MantisResource(mantisService, mantisQueryService);
        this.restMantisMockMvc = MockMvcBuilders.standaloneSetup(mantisResource)
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
    public static Mantis createEntity(EntityManager em) {
        Mantis mantis = new Mantis()
            .mantisNumber(DEFAULT_MANTIS_NUMBER)
            .submissionDate(DEFAULT_SUBMISSION_DATE);
        return mantis;
    }

    @Before
    public void initTest() {
        mantis = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantis() throws Exception {
        int databaseSizeBeforeCreate = mantisRepository.findAll().size();

        // Create the Mantis
        MantisDTO mantisDTO = mantisMapper.toDto(mantis);
        restMantisMockMvc.perform(post("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isCreated());

        // Validate the Mantis in the database
        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeCreate + 1);
        Mantis testMantis = mantisList.get(mantisList.size() - 1);
        assertThat(testMantis.getMantisNumber()).isEqualTo(DEFAULT_MANTIS_NUMBER);
        assertThat(testMantis.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void createMantisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisRepository.findAll().size();

        // Create the Mantis with an existing ID
        mantis.setId(1L);
        MantisDTO mantisDTO = mantisMapper.toDto(mantis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisMockMvc.perform(post("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mantis in the database
        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMantisNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisRepository.findAll().size();
        // set the field null
        mantis.setMantisNumber(null);

        // Create the Mantis, which fails.
        MantisDTO mantisDTO = mantisMapper.toDto(mantis);

        restMantisMockMvc.perform(post("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isBadRequest());

        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubmissionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisRepository.findAll().size();
        // set the field null
        mantis.setSubmissionDate(null);

        // Create the Mantis, which fails.
        MantisDTO mantisDTO = mantisMapper.toDto(mantis);

        restMantisMockMvc.perform(post("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isBadRequest());

        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMantis() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList
        restMantisMockMvc.perform(get("/api/mantis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantis.getId().intValue())))
            .andExpect(jsonPath("$.[*].mantisNumber").value(hasItem(DEFAULT_MANTIS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMantis() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get the mantis
        restMantisMockMvc.perform(get("/api/mantis/{id}", mantis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantis.getId().intValue()))
            .andExpect(jsonPath("$.mantisNumber").value(DEFAULT_MANTIS_NUMBER.toString()))
            .andExpect(jsonPath("$.submissionDate").value(DEFAULT_SUBMISSION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllMantisByMantisNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where mantisNumber equals to DEFAULT_MANTIS_NUMBER
        defaultMantisShouldBeFound("mantisNumber.equals=" + DEFAULT_MANTIS_NUMBER);

        // Get all the mantisList where mantisNumber equals to UPDATED_MANTIS_NUMBER
        defaultMantisShouldNotBeFound("mantisNumber.equals=" + UPDATED_MANTIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMantisByMantisNumberIsInShouldWork() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where mantisNumber in DEFAULT_MANTIS_NUMBER or UPDATED_MANTIS_NUMBER
        defaultMantisShouldBeFound("mantisNumber.in=" + DEFAULT_MANTIS_NUMBER + "," + UPDATED_MANTIS_NUMBER);

        // Get all the mantisList where mantisNumber equals to UPDATED_MANTIS_NUMBER
        defaultMantisShouldNotBeFound("mantisNumber.in=" + UPDATED_MANTIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMantisByMantisNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where mantisNumber is not null
        defaultMantisShouldBeFound("mantisNumber.specified=true");

        // Get all the mantisList where mantisNumber is null
        defaultMantisShouldNotBeFound("mantisNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisBySubmissionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where submissionDate equals to DEFAULT_SUBMISSION_DATE
        defaultMantisShouldBeFound("submissionDate.equals=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultMantisShouldNotBeFound("submissionDate.equals=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisBySubmissionDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where submissionDate in DEFAULT_SUBMISSION_DATE or UPDATED_SUBMISSION_DATE
        defaultMantisShouldBeFound("submissionDate.in=" + DEFAULT_SUBMISSION_DATE + "," + UPDATED_SUBMISSION_DATE);

        // Get all the mantisList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultMantisShouldNotBeFound("submissionDate.in=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisBySubmissionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where submissionDate is not null
        defaultMantisShouldBeFound("submissionDate.specified=true");

        // Get all the mantisList where submissionDate is null
        defaultMantisShouldNotBeFound("submissionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisBySubmissionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where submissionDate greater than or equals to DEFAULT_SUBMISSION_DATE
        defaultMantisShouldBeFound("submissionDate.greaterOrEqualThan=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisList where submissionDate greater than or equals to UPDATED_SUBMISSION_DATE
        defaultMantisShouldNotBeFound("submissionDate.greaterOrEqualThan=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisBySubmissionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);

        // Get all the mantisList where submissionDate less than or equals to DEFAULT_SUBMISSION_DATE
        defaultMantisShouldNotBeFound("submissionDate.lessThan=" + DEFAULT_SUBMISSION_DATE);

        // Get all the mantisList where submissionDate less than or equals to UPDATED_SUBMISSION_DATE
        defaultMantisShouldBeFound("submissionDate.lessThan=" + UPDATED_SUBMISSION_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        mantis.setProject(project);
        mantisRepository.saveAndFlush(mantis);
        Long projectId = project.getId();

        // Get all the mantisList where project equals to projectId
        defaultMantisShouldBeFound("projectId.equals=" + projectId);

        // Get all the mantisList where project equals to projectId + 1
        defaultMantisShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMantisShouldBeFound(String filter) throws Exception {
        restMantisMockMvc.perform(get("/api/mantis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantis.getId().intValue())))
            .andExpect(jsonPath("$.[*].mantisNumber").value(hasItem(DEFAULT_MANTIS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMantisShouldNotBeFound(String filter) throws Exception {
        restMantisMockMvc.perform(get("/api/mantis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMantis() throws Exception {
        // Get the mantis
        restMantisMockMvc.perform(get("/api/mantis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantis() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);
        int databaseSizeBeforeUpdate = mantisRepository.findAll().size();

        // Update the mantis
        Mantis updatedMantis = mantisRepository.findOne(mantis.getId());
        // Disconnect from session so that the updates on updatedMantis are not directly saved in db
        em.detach(updatedMantis);
        updatedMantis
            .mantisNumber(UPDATED_MANTIS_NUMBER)
            .submissionDate(UPDATED_SUBMISSION_DATE);
        MantisDTO mantisDTO = mantisMapper.toDto(updatedMantis);

        restMantisMockMvc.perform(put("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isOk());

        // Validate the Mantis in the database
        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeUpdate);
        Mantis testMantis = mantisList.get(mantisList.size() - 1);
        assertThat(testMantis.getMantisNumber()).isEqualTo(UPDATED_MANTIS_NUMBER);
        assertThat(testMantis.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMantis() throws Exception {
        int databaseSizeBeforeUpdate = mantisRepository.findAll().size();

        // Create the Mantis
        MantisDTO mantisDTO = mantisMapper.toDto(mantis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisMockMvc.perform(put("/api/mantis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisDTO)))
            .andExpect(status().isCreated());

        // Validate the Mantis in the database
        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantis() throws Exception {
        // Initialize the database
        mantisRepository.saveAndFlush(mantis);
        int databaseSizeBeforeDelete = mantisRepository.findAll().size();

        // Get the mantis
        restMantisMockMvc.perform(delete("/api/mantis/{id}", mantis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mantis> mantisList = mantisRepository.findAll();
        assertThat(mantisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mantis.class);
        Mantis mantis1 = new Mantis();
        mantis1.setId(1L);
        Mantis mantis2 = new Mantis();
        mantis2.setId(mantis1.getId());
        assertThat(mantis1).isEqualTo(mantis2);
        mantis2.setId(2L);
        assertThat(mantis1).isNotEqualTo(mantis2);
        mantis1.setId(null);
        assertThat(mantis1).isNotEqualTo(mantis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisDTO.class);
        MantisDTO mantisDTO1 = new MantisDTO();
        mantisDTO1.setId(1L);
        MantisDTO mantisDTO2 = new MantisDTO();
        assertThat(mantisDTO1).isNotEqualTo(mantisDTO2);
        mantisDTO2.setId(mantisDTO1.getId());
        assertThat(mantisDTO1).isEqualTo(mantisDTO2);
        mantisDTO2.setId(2L);
        assertThat(mantisDTO1).isNotEqualTo(mantisDTO2);
        mantisDTO1.setId(null);
        assertThat(mantisDTO1).isNotEqualTo(mantisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisMapper.fromId(null)).isNull();
    }
}
