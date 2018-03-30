package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.Referent;
import com.dirks.cool.repository.ReferentRepository;
import com.dirks.cool.service.ReferentService;
import com.dirks.cool.service.dto.ReferentDTO;
import com.dirks.cool.service.mapper.ReferentMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;
import com.dirks.cool.service.dto.ReferentCriteria;
import com.dirks.cool.service.ReferentQueryService;

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
 * Test class for the ReferentResource REST controller.
 *
 * @see ReferentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class ReferentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ReferentRepository referentRepository;

    @Autowired
    private ReferentMapper referentMapper;

    @Autowired
    private ReferentService referentService;

    @Autowired
    private ReferentQueryService referentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReferentMockMvc;

    private Referent referent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferentResource referentResource = new ReferentResource(referentService, referentQueryService);
        this.restReferentMockMvc = MockMvcBuilders.standaloneSetup(referentResource)
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
    public static Referent createEntity(EntityManager em) {
        Referent referent = new Referent()
            .name(DEFAULT_NAME);
        return referent;
    }

    @Before
    public void initTest() {
        referent = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferent() throws Exception {
        int databaseSizeBeforeCreate = referentRepository.findAll().size();

        // Create the Referent
        ReferentDTO referentDTO = referentMapper.toDto(referent);
        restReferentMockMvc.perform(post("/api/referents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentDTO)))
            .andExpect(status().isCreated());

        // Validate the Referent in the database
        List<Referent> referentList = referentRepository.findAll();
        assertThat(referentList).hasSize(databaseSizeBeforeCreate + 1);
        Referent testReferent = referentList.get(referentList.size() - 1);
        assertThat(testReferent.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createReferentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referentRepository.findAll().size();

        // Create the Referent with an existing ID
        referent.setId(1L);
        ReferentDTO referentDTO = referentMapper.toDto(referent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferentMockMvc.perform(post("/api/referents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Referent in the database
        List<Referent> referentList = referentRepository.findAll();
        assertThat(referentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReferents() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);

        // Get all the referentList
        restReferentMockMvc.perform(get("/api/referents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getReferent() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);

        // Get the referent
        restReferentMockMvc.perform(get("/api/referents/{id}", referent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllReferentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);

        // Get all the referentList where name equals to DEFAULT_NAME
        defaultReferentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the referentList where name equals to UPDATED_NAME
        defaultReferentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);

        // Get all the referentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReferentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the referentList where name equals to UPDATED_NAME
        defaultReferentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);

        // Get all the referentList where name is not null
        defaultReferentShouldBeFound("name.specified=true");

        // Get all the referentList where name is null
        defaultReferentShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReferentShouldBeFound(String filter) throws Exception {
        restReferentMockMvc.perform(get("/api/referents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReferentShouldNotBeFound(String filter) throws Exception {
        restReferentMockMvc.perform(get("/api/referents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingReferent() throws Exception {
        // Get the referent
        restReferentMockMvc.perform(get("/api/referents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferent() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);
        int databaseSizeBeforeUpdate = referentRepository.findAll().size();

        // Update the referent
        Referent updatedReferent = referentRepository.findOne(referent.getId());
        // Disconnect from session so that the updates on updatedReferent are not directly saved in db
        em.detach(updatedReferent);
        updatedReferent
            .name(UPDATED_NAME);
        ReferentDTO referentDTO = referentMapper.toDto(updatedReferent);

        restReferentMockMvc.perform(put("/api/referents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentDTO)))
            .andExpect(status().isOk());

        // Validate the Referent in the database
        List<Referent> referentList = referentRepository.findAll();
        assertThat(referentList).hasSize(databaseSizeBeforeUpdate);
        Referent testReferent = referentList.get(referentList.size() - 1);
        assertThat(testReferent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingReferent() throws Exception {
        int databaseSizeBeforeUpdate = referentRepository.findAll().size();

        // Create the Referent
        ReferentDTO referentDTO = referentMapper.toDto(referent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReferentMockMvc.perform(put("/api/referents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentDTO)))
            .andExpect(status().isCreated());

        // Validate the Referent in the database
        List<Referent> referentList = referentRepository.findAll();
        assertThat(referentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReferent() throws Exception {
        // Initialize the database
        referentRepository.saveAndFlush(referent);
        int databaseSizeBeforeDelete = referentRepository.findAll().size();

        // Get the referent
        restReferentMockMvc.perform(delete("/api/referents/{id}", referent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Referent> referentList = referentRepository.findAll();
        assertThat(referentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Referent.class);
        Referent referent1 = new Referent();
        referent1.setId(1L);
        Referent referent2 = new Referent();
        referent2.setId(referent1.getId());
        assertThat(referent1).isEqualTo(referent2);
        referent2.setId(2L);
        assertThat(referent1).isNotEqualTo(referent2);
        referent1.setId(null);
        assertThat(referent1).isNotEqualTo(referent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferentDTO.class);
        ReferentDTO referentDTO1 = new ReferentDTO();
        referentDTO1.setId(1L);
        ReferentDTO referentDTO2 = new ReferentDTO();
        assertThat(referentDTO1).isNotEqualTo(referentDTO2);
        referentDTO2.setId(referentDTO1.getId());
        assertThat(referentDTO1).isEqualTo(referentDTO2);
        referentDTO2.setId(2L);
        assertThat(referentDTO1).isNotEqualTo(referentDTO2);
        referentDTO1.setId(null);
        assertThat(referentDTO1).isNotEqualTo(referentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referentMapper.fromId(null)).isNull();
    }
}
