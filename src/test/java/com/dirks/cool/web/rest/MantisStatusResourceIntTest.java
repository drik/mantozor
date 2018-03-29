package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.repository.MantisStatusRepository;
import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.mapper.MantisStatusMapper;
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

import com.dirks.cool.domain.enumeration.Status;
/**
 * Test class for the MantisStatusResource REST controller.
 *
 * @see MantisStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisStatusResourceIntTest {

    private static final Status DEFAULT_STATUS = Status.ACCEPTED;
    private static final Status UPDATED_STATUS = Status.ANALYSED;

    private static final LocalDate DEFAULT_CHANGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHANGE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MantisStatusRepository mantisStatusRepository;

    @Autowired
    private MantisStatusMapper mantisStatusMapper;

    @Autowired
    private MantisStatusService mantisStatusService;

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
        final MantisStatusResource mantisStatusResource = new MantisStatusResource(mantisStatusService);
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
            .status(DEFAULT_STATUS)
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
        assertThat(testMantisStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.changeDate").value(DEFAULT_CHANGE_DATE.toString()));
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
            .status(UPDATED_STATUS)
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
        assertThat(testMantisStatus.getStatus()).isEqualTo(UPDATED_STATUS);
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
