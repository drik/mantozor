package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisApprover;
import com.dirks.cool.repository.MantisApproverRepository;
import com.dirks.cool.service.dto.MantisApproverDTO;
import com.dirks.cool.service.mapper.MantisApproverMapper;
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
import java.util.List;

import static com.dirks.cool.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MantisApproverResource REST controller.
 *
 * @see MantisApproverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisApproverResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    @Autowired
    private MantisApproverRepository mantisApproverRepository;

    @Autowired
    private MantisApproverMapper mantisApproverMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisApproverMockMvc;

    private MantisApprover mantisApprover;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisApproverResource mantisApproverResource = new MantisApproverResource(mantisApproverRepository, mantisApproverMapper);
        this.restMantisApproverMockMvc = MockMvcBuilders.standaloneSetup(mantisApproverResource)
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
    public static MantisApprover createEntity(EntityManager em) {
        MantisApprover mantisApprover = new MantisApprover()
            .fullName(DEFAULT_FULL_NAME);
        return mantisApprover;
    }

    @Before
    public void initTest() {
        mantisApprover = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantisApprover() throws Exception {
        int databaseSizeBeforeCreate = mantisApproverRepository.findAll().size();

        // Create the MantisApprover
        MantisApproverDTO mantisApproverDTO = mantisApproverMapper.toDto(mantisApprover);
        restMantisApproverMockMvc.perform(post("/api/mantis-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisApproverDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisApprover in the database
        List<MantisApprover> mantisApproverList = mantisApproverRepository.findAll();
        assertThat(mantisApproverList).hasSize(databaseSizeBeforeCreate + 1);
        MantisApprover testMantisApprover = mantisApproverList.get(mantisApproverList.size() - 1);
        assertThat(testMantisApprover.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
    }

    @Test
    @Transactional
    public void createMantisApproverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisApproverRepository.findAll().size();

        // Create the MantisApprover with an existing ID
        mantisApprover.setId(1L);
        MantisApproverDTO mantisApproverDTO = mantisApproverMapper.toDto(mantisApprover);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisApproverMockMvc.perform(post("/api/mantis-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisApproverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MantisApprover in the database
        List<MantisApprover> mantisApproverList = mantisApproverRepository.findAll();
        assertThat(mantisApproverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMantisApprovers() throws Exception {
        // Initialize the database
        mantisApproverRepository.saveAndFlush(mantisApprover);

        // Get all the mantisApproverList
        restMantisApproverMockMvc.perform(get("/api/mantis-approvers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisApprover.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMantisApprover() throws Exception {
        // Initialize the database
        mantisApproverRepository.saveAndFlush(mantisApprover);

        // Get the mantisApprover
        restMantisApproverMockMvc.perform(get("/api/mantis-approvers/{id}", mantisApprover.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantisApprover.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMantisApprover() throws Exception {
        // Get the mantisApprover
        restMantisApproverMockMvc.perform(get("/api/mantis-approvers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantisApprover() throws Exception {
        // Initialize the database
        mantisApproverRepository.saveAndFlush(mantisApprover);
        int databaseSizeBeforeUpdate = mantisApproverRepository.findAll().size();

        // Update the mantisApprover
        MantisApprover updatedMantisApprover = mantisApproverRepository.findOne(mantisApprover.getId());
        // Disconnect from session so that the updates on updatedMantisApprover are not directly saved in db
        em.detach(updatedMantisApprover);
        updatedMantisApprover
            .fullName(UPDATED_FULL_NAME);
        MantisApproverDTO mantisApproverDTO = mantisApproverMapper.toDto(updatedMantisApprover);

        restMantisApproverMockMvc.perform(put("/api/mantis-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisApproverDTO)))
            .andExpect(status().isOk());

        // Validate the MantisApprover in the database
        List<MantisApprover> mantisApproverList = mantisApproverRepository.findAll();
        assertThat(mantisApproverList).hasSize(databaseSizeBeforeUpdate);
        MantisApprover testMantisApprover = mantisApproverList.get(mantisApproverList.size() - 1);
        assertThat(testMantisApprover.getFullName()).isEqualTo(UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMantisApprover() throws Exception {
        int databaseSizeBeforeUpdate = mantisApproverRepository.findAll().size();

        // Create the MantisApprover
        MantisApproverDTO mantisApproverDTO = mantisApproverMapper.toDto(mantisApprover);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisApproverMockMvc.perform(put("/api/mantis-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisApproverDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisApprover in the database
        List<MantisApprover> mantisApproverList = mantisApproverRepository.findAll();
        assertThat(mantisApproverList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantisApprover() throws Exception {
        // Initialize the database
        mantisApproverRepository.saveAndFlush(mantisApprover);
        int databaseSizeBeforeDelete = mantisApproverRepository.findAll().size();

        // Get the mantisApprover
        restMantisApproverMockMvc.perform(delete("/api/mantis-approvers/{id}", mantisApprover.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MantisApprover> mantisApproverList = mantisApproverRepository.findAll();
        assertThat(mantisApproverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisApprover.class);
        MantisApprover mantisApprover1 = new MantisApprover();
        mantisApprover1.setId(1L);
        MantisApprover mantisApprover2 = new MantisApprover();
        mantisApprover2.setId(mantisApprover1.getId());
        assertThat(mantisApprover1).isEqualTo(mantisApprover2);
        mantisApprover2.setId(2L);
        assertThat(mantisApprover1).isNotEqualTo(mantisApprover2);
        mantisApprover1.setId(null);
        assertThat(mantisApprover1).isNotEqualTo(mantisApprover2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisApproverDTO.class);
        MantisApproverDTO mantisApproverDTO1 = new MantisApproverDTO();
        mantisApproverDTO1.setId(1L);
        MantisApproverDTO mantisApproverDTO2 = new MantisApproverDTO();
        assertThat(mantisApproverDTO1).isNotEqualTo(mantisApproverDTO2);
        mantisApproverDTO2.setId(mantisApproverDTO1.getId());
        assertThat(mantisApproverDTO1).isEqualTo(mantisApproverDTO2);
        mantisApproverDTO2.setId(2L);
        assertThat(mantisApproverDTO1).isNotEqualTo(mantisApproverDTO2);
        mantisApproverDTO1.setId(null);
        assertThat(mantisApproverDTO1).isNotEqualTo(mantisApproverDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisApproverMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisApproverMapper.fromId(null)).isNull();
    }
}
