package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.TravelerFile;
import com.zetravelcloud.webapp.repository.TravelerFileRepository;
import com.zetravelcloud.webapp.service.TravelerFileService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelerFileResource REST controller.
 *
 * @see TravelerFileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TravelerFileResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Inject
    private TravelerFileRepository travelerFileRepository;

    @Inject
    private TravelerFileService travelerFileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelerFileMockMvc;

    private TravelerFile travelerFile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelerFileResource travelerFileResource = new TravelerFileResource();
        ReflectionTestUtils.setField(travelerFileResource, "travelerFileService", travelerFileService);
        this.restTravelerFileMockMvc = MockMvcBuilders.standaloneSetup(travelerFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelerFile = new TravelerFile();
        travelerFile.setFileName(DEFAULT_FILE_NAME);
        travelerFile.setFile(DEFAULT_FILE);
        travelerFile.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTravelerFile() throws Exception {
        int databaseSizeBeforeCreate = travelerFileRepository.findAll().size();

        // Create the TravelerFile

        restTravelerFileMockMvc.perform(post("/api/travelerFiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelerFile)))
                .andExpect(status().isCreated());

        // Validate the TravelerFile in the database
        List<TravelerFile> travelerFiles = travelerFileRepository.findAll();
        assertThat(travelerFiles).hasSize(databaseSizeBeforeCreate + 1);
        TravelerFile testTravelerFile = travelerFiles.get(travelerFiles.size() - 1);
        assertThat(testTravelerFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testTravelerFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testTravelerFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTravelerFiles() throws Exception {
        // Initialize the database
        travelerFileRepository.saveAndFlush(travelerFile);

        // Get all the travelerFiles
        restTravelerFileMockMvc.perform(get("/api/travelerFiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelerFile.getId().intValue())))
                .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getTravelerFile() throws Exception {
        // Initialize the database
        travelerFileRepository.saveAndFlush(travelerFile);

        // Get the travelerFile
        restTravelerFileMockMvc.perform(get("/api/travelerFiles/{id}", travelerFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelerFile.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingTravelerFile() throws Exception {
        // Get the travelerFile
        restTravelerFileMockMvc.perform(get("/api/travelerFiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelerFile() throws Exception {
        // Initialize the database
        travelerFileRepository.saveAndFlush(travelerFile);

		int databaseSizeBeforeUpdate = travelerFileRepository.findAll().size();

        // Update the travelerFile
        travelerFile.setFileName(UPDATED_FILE_NAME);
        travelerFile.setFile(UPDATED_FILE);
        travelerFile.setFileContentType(UPDATED_FILE_CONTENT_TYPE);

        restTravelerFileMockMvc.perform(put("/api/travelerFiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelerFile)))
                .andExpect(status().isOk());

        // Validate the TravelerFile in the database
        List<TravelerFile> travelerFiles = travelerFileRepository.findAll();
        assertThat(travelerFiles).hasSize(databaseSizeBeforeUpdate);
        TravelerFile testTravelerFile = travelerFiles.get(travelerFiles.size() - 1);
        assertThat(testTravelerFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testTravelerFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testTravelerFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteTravelerFile() throws Exception {
        // Initialize the database
        travelerFileRepository.saveAndFlush(travelerFile);

		int databaseSizeBeforeDelete = travelerFileRepository.findAll().size();

        // Get the travelerFile
        restTravelerFileMockMvc.perform(delete("/api/travelerFiles/{id}", travelerFile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelerFile> travelerFiles = travelerFileRepository.findAll();
        assertThat(travelerFiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
