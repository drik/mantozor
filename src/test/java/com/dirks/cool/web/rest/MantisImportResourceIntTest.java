package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisImport;
import com.dirks.cool.domain.User;
import com.dirks.cool.repository.MantisImportRepository;
import com.dirks.cool.service.MantisImportService;
import com.dirks.cool.service.dto.MantisImportDTO;
import com.dirks.cool.service.mapper.MantisImportMapper;
import com.dirks.cool.web.rest.errors.ExceptionTranslator;
import com.dirks.cool.service.dto.MantisImportCriteria;
import com.dirks.cool.service.MantisImportQueryService;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the MantisImportResource REST controller.
 *
 * @see MantisImportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisImportResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IMPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IMPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private MantisImportRepository mantisImportRepository;

    @Autowired
    private MantisImportMapper mantisImportMapper;

    @Autowired
    private MantisImportService mantisImportService;

    @Autowired
    private MantisImportQueryService mantisImportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisImportMockMvc;

    private MantisImport mantisImport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisImportResource mantisImportResource = new MantisImportResource(mantisImportService, mantisImportQueryService);
        this.restMantisImportMockMvc = MockMvcBuilders.standaloneSetup(mantisImportResource)
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
    public static MantisImport createEntity(EntityManager em) {
        MantisImport mantisImport = new MantisImport()
            .name(DEFAULT_NAME)
            .importDate(DEFAULT_IMPORT_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return mantisImport;
    }

    @Before
    public void initTest() {
        mantisImport = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantisImport() throws Exception {
        int databaseSizeBeforeCreate = mantisImportRepository.findAll().size();

        // Create the MantisImport
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);
        restMantisImportMockMvc.perform(post("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisImport in the database
        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeCreate + 1);
        MantisImport testMantisImport = mantisImportList.get(mantisImportList.size() - 1);
        assertThat(testMantisImport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMantisImport.getImportDate()).isEqualTo(DEFAULT_IMPORT_DATE);
        assertThat(testMantisImport.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testMantisImport.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMantisImportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisImportRepository.findAll().size();

        // Create the MantisImport with an existing ID
        mantisImport.setId(1L);
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisImportMockMvc.perform(post("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MantisImport in the database
        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisImportRepository.findAll().size();
        // set the field null
        mantisImport.setName(null);

        // Create the MantisImport, which fails.
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);

        restMantisImportMockMvc.perform(post("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isBadRequest());

        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImportDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisImportRepository.findAll().size();
        // set the field null
        mantisImport.setImportDate(null);

        // Create the MantisImport, which fails.
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);

        restMantisImportMockMvc.perform(post("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isBadRequest());

        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = mantisImportRepository.findAll().size();
        // set the field null
        mantisImport.setFile(null);

        // Create the MantisImport, which fails.
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);

        restMantisImportMockMvc.perform(post("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isBadRequest());

        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMantisImports() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList
        restMantisImportMockMvc.perform(get("/api/mantis-imports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisImport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].importDate").value(hasItem(DEFAULT_IMPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getMantisImport() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get the mantisImport
        restMantisImportMockMvc.perform(get("/api/mantis-imports/{id}", mantisImport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantisImport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.importDate").value(DEFAULT_IMPORT_DATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getAllMantisImportsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where name equals to DEFAULT_NAME
        defaultMantisImportShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mantisImportList where name equals to UPDATED_NAME
        defaultMantisImportShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMantisImportsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMantisImportShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mantisImportList where name equals to UPDATED_NAME
        defaultMantisImportShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMantisImportsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where name is not null
        defaultMantisImportShouldBeFound("name.specified=true");

        // Get all the mantisImportList where name is null
        defaultMantisImportShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportsByImportDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where importDate equals to DEFAULT_IMPORT_DATE
        defaultMantisImportShouldBeFound("importDate.equals=" + DEFAULT_IMPORT_DATE);

        // Get all the mantisImportList where importDate equals to UPDATED_IMPORT_DATE
        defaultMantisImportShouldNotBeFound("importDate.equals=" + UPDATED_IMPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportsByImportDateIsInShouldWork() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where importDate in DEFAULT_IMPORT_DATE or UPDATED_IMPORT_DATE
        defaultMantisImportShouldBeFound("importDate.in=" + DEFAULT_IMPORT_DATE + "," + UPDATED_IMPORT_DATE);

        // Get all the mantisImportList where importDate equals to UPDATED_IMPORT_DATE
        defaultMantisImportShouldNotBeFound("importDate.in=" + UPDATED_IMPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportsByImportDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where importDate is not null
        defaultMantisImportShouldBeFound("importDate.specified=true");

        // Get all the mantisImportList where importDate is null
        defaultMantisImportShouldNotBeFound("importDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMantisImportsByImportDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where importDate greater than or equals to DEFAULT_IMPORT_DATE
        defaultMantisImportShouldBeFound("importDate.greaterOrEqualThan=" + DEFAULT_IMPORT_DATE);

        // Get all the mantisImportList where importDate greater than or equals to UPDATED_IMPORT_DATE
        defaultMantisImportShouldNotBeFound("importDate.greaterOrEqualThan=" + UPDATED_IMPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMantisImportsByImportDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);

        // Get all the mantisImportList where importDate less than or equals to DEFAULT_IMPORT_DATE
        defaultMantisImportShouldNotBeFound("importDate.lessThan=" + DEFAULT_IMPORT_DATE);

        // Get all the mantisImportList where importDate less than or equals to UPDATED_IMPORT_DATE
        defaultMantisImportShouldBeFound("importDate.lessThan=" + UPDATED_IMPORT_DATE);
    }


    @Test
    @Transactional
    public void getAllMantisImportsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        mantisImport.setUser(user);
        mantisImportRepository.saveAndFlush(mantisImport);
        Long userId = user.getId();

        // Get all the mantisImportList where user equals to userId
        defaultMantisImportShouldBeFound("userId.equals=" + userId);

        // Get all the mantisImportList where user equals to userId + 1
        defaultMantisImportShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMantisImportShouldBeFound(String filter) throws Exception {
        restMantisImportMockMvc.perform(get("/api/mantis-imports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisImport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].importDate").value(hasItem(DEFAULT_IMPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMantisImportShouldNotBeFound(String filter) throws Exception {
        restMantisImportMockMvc.perform(get("/api/mantis-imports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMantisImport() throws Exception {
        // Get the mantisImport
        restMantisImportMockMvc.perform(get("/api/mantis-imports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantisImport() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);
        int databaseSizeBeforeUpdate = mantisImportRepository.findAll().size();

        // Update the mantisImport
        MantisImport updatedMantisImport = mantisImportRepository.findOne(mantisImport.getId());
        // Disconnect from session so that the updates on updatedMantisImport are not directly saved in db
        em.detach(updatedMantisImport);
        updatedMantisImport
            .name(UPDATED_NAME)
            .importDate(UPDATED_IMPORT_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(updatedMantisImport);

        restMantisImportMockMvc.perform(put("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isOk());

        // Validate the MantisImport in the database
        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeUpdate);
        MantisImport testMantisImport = mantisImportList.get(mantisImportList.size() - 1);
        assertThat(testMantisImport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMantisImport.getImportDate()).isEqualTo(UPDATED_IMPORT_DATE);
        assertThat(testMantisImport.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testMantisImport.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMantisImport() throws Exception {
        int databaseSizeBeforeUpdate = mantisImportRepository.findAll().size();

        // Create the MantisImport
        MantisImportDTO mantisImportDTO = mantisImportMapper.toDto(mantisImport);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisImportMockMvc.perform(put("/api/mantis-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisImportDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisImport in the database
        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantisImport() throws Exception {
        // Initialize the database
        mantisImportRepository.saveAndFlush(mantisImport);
        int databaseSizeBeforeDelete = mantisImportRepository.findAll().size();

        // Get the mantisImport
        restMantisImportMockMvc.perform(delete("/api/mantis-imports/{id}", mantisImport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MantisImport> mantisImportList = mantisImportRepository.findAll();
        assertThat(mantisImportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisImport.class);
        MantisImport mantisImport1 = new MantisImport();
        mantisImport1.setId(1L);
        MantisImport mantisImport2 = new MantisImport();
        mantisImport2.setId(mantisImport1.getId());
        assertThat(mantisImport1).isEqualTo(mantisImport2);
        mantisImport2.setId(2L);
        assertThat(mantisImport1).isNotEqualTo(mantisImport2);
        mantisImport1.setId(null);
        assertThat(mantisImport1).isNotEqualTo(mantisImport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisImportDTO.class);
        MantisImportDTO mantisImportDTO1 = new MantisImportDTO();
        mantisImportDTO1.setId(1L);
        MantisImportDTO mantisImportDTO2 = new MantisImportDTO();
        assertThat(mantisImportDTO1).isNotEqualTo(mantisImportDTO2);
        mantisImportDTO2.setId(mantisImportDTO1.getId());
        assertThat(mantisImportDTO1).isEqualTo(mantisImportDTO2);
        mantisImportDTO2.setId(2L);
        assertThat(mantisImportDTO1).isNotEqualTo(mantisImportDTO2);
        mantisImportDTO1.setId(null);
        assertThat(mantisImportDTO1).isNotEqualTo(mantisImportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisImportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisImportMapper.fromId(null)).isNull();
    }
}
