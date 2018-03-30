package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.Status;
import com.dirks.cool.domain.User;
import com.dirks.cool.domain.MantisApprover;
import com.dirks.cool.repository.MantisStatusRepository;
import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.mapper.MantisStatusMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;
import com.dirks.cool.service.dto.MantisStatusCriteria;
import com.dirks.cool.service.MantisStatusQueryService;

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
 * Test class for the MantisStatusResource REST controller.
 *
 * @see MantisStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisStatusResourceIntTest {

    private static final LocalDate DEFAULT_CHANGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHANGE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MantisStatusRepository mantisStatusRepository;

    @Autowired
    private MantisStatusMapper mantisStatusMapper;

    @Autowired
    private MantisStatusService mantisStatusService;

    @Autowired
    private MantisStatusQueryService mantisStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisStatusMockMvc;

    private MantisStatus mantisStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisStatusResource mantisStatusResource = new MantisStatusResource(mantisStatusService, mantisStatusQueryService);
        this.restMantisStatusMockMvc = MockMvcBuilders.standaloneSetup(mantisStatusResource)
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
    public static MantisStatus createEntity(EntityManager em) {
        MantisStatus mantisStatus = new MantisStatus()
            .changeDate(DEFAULT_CHANGE_DATE);
        return mantisStatus;
    }

    @Before
    public void initTest() {
        mantisStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantisStatus() throws Exception {
        int databaseSizeBeforeCreate = mantisStatusRepository.findAll().size();

        // Create the MantisStatus
        MantisStatusDTO mantisStatusDTO = mantisStatusMapper.toDto(mantisStatus);
        restMantisStatusMockMvc.perform(post("/api/mantis-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisStatus in the database
        List<MantisStatus> mantisStatusList = mantisStatusRepository.findAll();
        assertThat(mantisStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MantisStatus testMantisStatus = mantisStatusList.get(mantisStatusList.size() - 1);
        assertThat(testMantisStatus.getChangeDate()).isEqualTo(DEFAULT_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void createMantisStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisStatusRepository.findAll().size();

        // Create the MantisStatus with an existing ID
        mantisStatus.setId(1L);
        MantisStatusDTO mantisStatusDTO = mantisStatusMapper.toDto(mantisStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisStatusMockMvc.perform(post("/api/mantis-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MantisStatus in the database
        List<MantisStatus> mantisStatusList = mantisStatusRepository.findAll();
        assertThat(mantisStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMantisStatuses() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList
        restMantisStatusMockMvc.perform(get("/api/mantis-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeDate").value(hasItem(DEFAULT_CHANGE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMantisStatus() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get the mantisStatus
        restMantisStatusMockMvc.perform(get("/api/mantis-statuses/{id}", mantisStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantisStatus.getId().intValue()))
            .andExpect(jsonPath("$.changeDate").value(DEFAULT_CHANGE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllMantisStatusesByChangeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList where changeDate equals to DEFAULT_CHANGE_DATE
        defaultMantisStatusShouldBeFound("changeDate.equals=" + DEFAULT_CHANGE_DATE);

        // Get all the mantisStatusList where changeDate equals to UPDATED_CHANGE_DATE
        defaultMantisStatusShouldNotBeFound("changeDate.equals=" + UPDATED_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisStatusesByChangeDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList where changeDate in DEFAULT_CHANGE_DATE or UPDATED_CHANGE_DATE
        defaultMantisStatusShouldBeFound("changeDate.in=" + DEFAULT_CHANGE_DATE + "," + UPDATED_CHANGE_DATE);

        // Get all the mantisStatusList where changeDate equals to UPDATED_CHANGE_DATE
        defaultMantisStatusShouldNotBeFound("changeDate.in=" + UPDATED_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisStatusesByChangeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList where changeDate is not null
        defaultMantisStatusShouldBeFound("changeDate.specified=true");

        // Get all the mantisStatusList where changeDate is null
        defaultMantisStatusShouldNotBeFound("changeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisStatusesByChangeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList where changeDate greater than or equals to DEFAULT_CHANGE_DATE
        defaultMantisStatusShouldBeFound("changeDate.greaterOrEqualThan=" + DEFAULT_CHANGE_DATE);

        // Get all the mantisStatusList where changeDate greater than or equals to UPDATED_CHANGE_DATE
        defaultMantisStatusShouldNotBeFound("changeDate.greaterOrEqualThan=" + UPDATED_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisStatusesByChangeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);

        // Get all the mantisStatusList where changeDate less than or equals to DEFAULT_CHANGE_DATE
        defaultMantisStatusShouldNotBeFound("changeDate.lessThan=" + DEFAULT_CHANGE_DATE);

        // Get all the mantisStatusList where changeDate less than or equals to UPDATED_CHANGE_DATE
        defaultMantisStatusShouldBeFound("changeDate.lessThan=" + UPDATED_CHANGE_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisStatusesByMantisIsEqualToSomething() throws Exception {
        // Initialize the database
        Mantis mantis = MantisResourceIntTest.createEntity(em);
        em.persist(mantis);
        em.flush();
        mantisStatus.setMantis(mantis);
        mantisStatusRepository.saveAndFlush(mantisStatus);
        Long mantisId = mantis.getId();

        // Get all the mantisStatusList where mantis equals to mantisId
        defaultMantisStatusShouldBeFound("mantisId.equals=" + mantisId);

        // Get all the mantisStatusList where mantis equals to mantisId + 1
        defaultMantisStatusShouldNotBeFound("mantisId.equals=" + (mantisId + 1));
    }


    @Test
    @Transactional
    public void getAllMantisStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        Status status = StatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        mantisStatus.setStatus(status);
        mantisStatusRepository.saveAndFlush(mantisStatus);
        Long statusId = status.getId();

        // Get all the mantisStatusList where status equals to statusId
        defaultMantisStatusShouldBeFound("statusId.equals=" + statusId);

        // Get all the mantisStatusList where status equals to statusId + 1
        defaultMantisStatusShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllMantisStatusesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        mantisStatus.setUser(user);
        mantisStatusRepository.saveAndFlush(mantisStatus);
        Long userId = user.getId();

        // Get all the mantisStatusList where user equals to userId
        defaultMantisStatusShouldBeFound("userId.equals=" + userId);

        // Get all the mantisStatusList where user equals to userId + 1
        defaultMantisStatusShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMantisStatusesByApproverIsEqualToSomething() throws Exception {
        // Initialize the database
        MantisApprover approver = MantisApproverResourceIntTest.createEntity(em);
        em.persist(approver);
        em.flush();
        mantisStatus.setApprover(approver);
        mantisStatusRepository.saveAndFlush(mantisStatus);
        Long approverId = approver.getId();

        // Get all the mantisStatusList where approver equals to approverId
        defaultMantisStatusShouldBeFound("approverId.equals=" + approverId);

        // Get all the mantisStatusList where approver equals to approverId + 1
        defaultMantisStatusShouldNotBeFound("approverId.equals=" + (approverId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMantisStatusShouldBeFound(String filter) throws Exception {
        restMantisStatusMockMvc.perform(get("/api/mantis-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeDate").value(hasItem(DEFAULT_CHANGE_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMantisStatusShouldNotBeFound(String filter) throws Exception {
        restMantisStatusMockMvc.perform(get("/api/mantis-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMantisStatus() throws Exception {
        // Get the mantisStatus
        restMantisStatusMockMvc.perform(get("/api/mantis-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantisStatus() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);
        int databaseSizeBeforeUpdate = mantisStatusRepository.findAll().size();

        // Update the mantisStatus
        MantisStatus updatedMantisStatus = mantisStatusRepository.findOne(mantisStatus.getId());
        // Disconnect from session so that the updates on updatedMantisStatus are not directly saved in db
        em.detach(updatedMantisStatus);
        updatedMantisStatus
            .changeDate(UPDATED_CHANGE_DATE);
        MantisStatusDTO mantisStatusDTO = mantisStatusMapper.toDto(updatedMantisStatus);

        restMantisStatusMockMvc.perform(put("/api/mantis-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisStatusDTO)))
            .andExpect(status().isOk());

        // Validate the MantisStatus in the database
        List<MantisStatus> mantisStatusList = mantisStatusRepository.findAll();
        assertThat(mantisStatusList).hasSize(databaseSizeBeforeUpdate);
        MantisStatus testMantisStatus = mantisStatusList.get(mantisStatusList.size() - 1);
        assertThat(testMantisStatus.getChangeDate()).isEqualTo(UPDATED_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMantisStatus() throws Exception {
        int databaseSizeBeforeUpdate = mantisStatusRepository.findAll().size();

        // Create the MantisStatus
        MantisStatusDTO mantisStatusDTO = mantisStatusMapper.toDto(mantisStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisStatusMockMvc.perform(put("/api/mantis-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisStatus in the database
        List<MantisStatus> mantisStatusList = mantisStatusRepository.findAll();
        assertThat(mantisStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantisStatus() throws Exception {
        // Initialize the database
        mantisStatusRepository.saveAndFlush(mantisStatus);
        int databaseSizeBeforeDelete = mantisStatusRepository.findAll().size();

        // Get the mantisStatus
        restMantisStatusMockMvc.perform(delete("/api/mantis-statuses/{id}", mantisStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MantisStatus> mantisStatusList = mantisStatusRepository.findAll();
        assertThat(mantisStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisStatus.class);
        MantisStatus mantisStatus1 = new MantisStatus();
        mantisStatus1.setId(1L);
        MantisStatus mantisStatus2 = new MantisStatus();
        mantisStatus2.setId(mantisStatus1.getId());
        assertThat(mantisStatus1).isEqualTo(mantisStatus2);
        mantisStatus2.setId(2L);
        assertThat(mantisStatus1).isNotEqualTo(mantisStatus2);
        mantisStatus1.setId(null);
        assertThat(mantisStatus1).isNotEqualTo(mantisStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisStatusDTO.class);
        MantisStatusDTO mantisStatusDTO1 = new MantisStatusDTO();
        mantisStatusDTO1.setId(1L);
        MantisStatusDTO mantisStatusDTO2 = new MantisStatusDTO();
        assertThat(mantisStatusDTO1).isNotEqualTo(mantisStatusDTO2);
        mantisStatusDTO2.setId(mantisStatusDTO1.getId());
        assertThat(mantisStatusDTO1).isEqualTo(mantisStatusDTO2);
        mantisStatusDTO2.setId(2L);
        assertThat(mantisStatusDTO1).isNotEqualTo(mantisStatusDTO2);
        mantisStatusDTO1.setId(null);
        assertThat(mantisStatusDTO1).isNotEqualTo(mantisStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisStatusMapper.fromId(null)).isNull();
    }
}
