package com.dirks.cool.web.rest;

import com.dirks.cool.MantozorApp;

import com.dirks.cool.domain.MantisFile;
import com.dirks.cool.repository.MantisFileRepository;
import com.dirks.cool.service.MantisFileService;
import com.dirks.cool.service.dto.MantisFileDTO;
import com.dirks.cool.service.mapper.MantisFileMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dirks.cool.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MantisFileResource REST controller.
 *
 * @see MantisFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MantozorApp.class)
public class MantisFileResourceIntTest {

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    @Autowired
    private MantisFileRepository mantisFileRepository;

    @Autowired
    private MantisFileMapper mantisFileMapper;

    @Autowired
    private MantisFileService mantisFileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMantisFileMockMvc;

    private MantisFile mantisFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MantisFileResource mantisFileResource = new MantisFileResource(mantisFileService);
        this.restMantisFileMockMvc = MockMvcBuilders.standaloneSetup(mantisFileResource)
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
    public static MantisFile createEntity(EntityManager em) {
        MantisFile mantisFile = new MantisFile()
            .file(DEFAULT_FILE);
        return mantisFile;
    }

    @Before
    public void initTest() {
        mantisFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMantisFile() throws Exception {
        int databaseSizeBeforeCreate = mantisFileRepository.findAll().size();

        // Create the MantisFile
        MantisFileDTO mantisFileDTO = mantisFileMapper.toDto(mantisFile);
        restMantisFileMockMvc.perform(post("/api/mantis-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisFileDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisFile in the database
        List<MantisFile> mantisFileList = mantisFileRepository.findAll();
        assertThat(mantisFileList).hasSize(databaseSizeBeforeCreate + 1);
        MantisFile testMantisFile = mantisFileList.get(mantisFileList.size() - 1);
        assertThat(testMantisFile.getFile()).isEqualTo(DEFAULT_FILE);
    }

    @Test
    @Transactional
    public void createMantisFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mantisFileRepository.findAll().size();

        // Create the MantisFile with an existing ID
        mantisFile.setId(1L);
        MantisFileDTO mantisFileDTO = mantisFileMapper.toDto(mantisFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantisFileMockMvc.perform(post("/api/mantis-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MantisFile in the database
        List<MantisFile> mantisFileList = mantisFileRepository.findAll();
        assertThat(mantisFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMantisFiles() throws Exception {
        // Initialize the database
        mantisFileRepository.saveAndFlush(mantisFile);

        // Get all the mantisFileList
        restMantisFileMockMvc.perform(get("/api/mantis-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantisFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE.toString())));
    }

    @Test
    @Transactional
    public void getMantisFile() throws Exception {
        // Initialize the database
        mantisFileRepository.saveAndFlush(mantisFile);

        // Get the mantisFile
        restMantisFileMockMvc.perform(get("/api/mantis-files/{id}", mantisFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mantisFile.getId().intValue()))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMantisFile() throws Exception {
        // Get the mantisFile
        restMantisFileMockMvc.perform(get("/api/mantis-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMantisFile() throws Exception {
        // Initialize the database
        mantisFileRepository.saveAndFlush(mantisFile);
        int databaseSizeBeforeUpdate = mantisFileRepository.findAll().size();

        // Update the mantisFile
        MantisFile updatedMantisFile = mantisFileRepository.findOne(mantisFile.getId());
        // Disconnect from session so that the updates on updatedMantisFile are not directly saved in db
        em.detach(updatedMantisFile);
        updatedMantisFile
            .file(UPDATED_FILE);
        MantisFileDTO mantisFileDTO = mantisFileMapper.toDto(updatedMantisFile);

        restMantisFileMockMvc.perform(put("/api/mantis-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisFileDTO)))
            .andExpect(status().isOk());

        // Validate the MantisFile in the database
        List<MantisFile> mantisFileList = mantisFileRepository.findAll();
        assertThat(mantisFileList).hasSize(databaseSizeBeforeUpdate);
        MantisFile testMantisFile = mantisFileList.get(mantisFileList.size() - 1);
        assertThat(testMantisFile.getFile()).isEqualTo(UPDATED_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingMantisFile() throws Exception {
        int databaseSizeBeforeUpdate = mantisFileRepository.findAll().size();

        // Create the MantisFile
        MantisFileDTO mantisFileDTO = mantisFileMapper.toDto(mantisFile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMantisFileMockMvc.perform(put("/api/mantis-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mantisFileDTO)))
            .andExpect(status().isCreated());

        // Validate the MantisFile in the database
        List<MantisFile> mantisFileList = mantisFileRepository.findAll();
        assertThat(mantisFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMantisFile() throws Exception {
        // Initialize the database
        mantisFileRepository.saveAndFlush(mantisFile);
        int databaseSizeBeforeDelete = mantisFileRepository.findAll().size();

        // Get the mantisFile
        restMantisFileMockMvc.perform(delete("/api/mantis-files/{id}", mantisFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MantisFile> mantisFileList = mantisFileRepository.findAll();
        assertThat(mantisFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisFile.class);
        MantisFile mantisFile1 = new MantisFile();
        mantisFile1.setId(1L);
        MantisFile mantisFile2 = new MantisFile();
        mantisFile2.setId(mantisFile1.getId());
        assertThat(mantisFile1).isEqualTo(mantisFile2);
        mantisFile2.setId(2L);
        assertThat(mantisFile1).isNotEqualTo(mantisFile2);
        mantisFile1.setId(null);
        assertThat(mantisFile1).isNotEqualTo(mantisFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantisFileDTO.class);
        MantisFileDTO mantisFileDTO1 = new MantisFileDTO();
        mantisFileDTO1.setId(1L);
        MantisFileDTO mantisFileDTO2 = new MantisFileDTO();
        assertThat(mantisFileDTO1).isNotEqualTo(mantisFileDTO2);
        mantisFileDTO2.setId(mantisFileDTO1.getId());
        assertThat(mantisFileDTO1).isEqualTo(mantisFileDTO2);
        mantisFileDTO2.setId(2L);
        assertThat(mantisFileDTO1).isNotEqualTo(mantisFileDTO2);
        mantisFileDTO1.setId(null);
        assertThat(mantisFileDTO1).isNotEqualTo(mantisFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mantisFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mantisFileMapper.fromId(null)).isNull();
    }
}
