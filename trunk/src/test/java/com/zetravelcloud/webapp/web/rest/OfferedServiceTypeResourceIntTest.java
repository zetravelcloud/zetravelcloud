package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.OfferedServiceType;
import com.zetravelcloud.webapp.repository.OfferedServiceTypeRepository;
import com.zetravelcloud.webapp.service.OfferedServiceTypeService;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OfferedServiceTypeResource REST controller.
 *
 * @see OfferedServiceTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OfferedServiceTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private OfferedServiceTypeRepository offeredServiceTypeRepository;

    @Inject
    private OfferedServiceTypeService offeredServiceTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOfferedServiceTypeMockMvc;

    private OfferedServiceType offeredServiceType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OfferedServiceTypeResource offeredServiceTypeResource = new OfferedServiceTypeResource();
        ReflectionTestUtils.setField(offeredServiceTypeResource, "offeredServiceTypeService", offeredServiceTypeService);
        this.restOfferedServiceTypeMockMvc = MockMvcBuilders.standaloneSetup(offeredServiceTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        offeredServiceType = new OfferedServiceType();
        offeredServiceType.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOfferedServiceType() throws Exception {
        int databaseSizeBeforeCreate = offeredServiceTypeRepository.findAll().size();

        // Create the OfferedServiceType

        restOfferedServiceTypeMockMvc.perform(post("/api/offeredServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offeredServiceType)))
                .andExpect(status().isCreated());

        // Validate the OfferedServiceType in the database
        List<OfferedServiceType> offeredServiceTypes = offeredServiceTypeRepository.findAll();
        assertThat(offeredServiceTypes).hasSize(databaseSizeBeforeCreate + 1);
        OfferedServiceType testOfferedServiceType = offeredServiceTypes.get(offeredServiceTypes.size() - 1);
        assertThat(testOfferedServiceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllOfferedServiceTypes() throws Exception {
        // Initialize the database
        offeredServiceTypeRepository.saveAndFlush(offeredServiceType);

        // Get all the offeredServiceTypes
        restOfferedServiceTypeMockMvc.perform(get("/api/offeredServiceTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(offeredServiceType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOfferedServiceType() throws Exception {
        // Initialize the database
        offeredServiceTypeRepository.saveAndFlush(offeredServiceType);

        // Get the offeredServiceType
        restOfferedServiceTypeMockMvc.perform(get("/api/offeredServiceTypes/{id}", offeredServiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(offeredServiceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfferedServiceType() throws Exception {
        // Get the offeredServiceType
        restOfferedServiceTypeMockMvc.perform(get("/api/offeredServiceTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferedServiceType() throws Exception {
        // Initialize the database
        offeredServiceTypeRepository.saveAndFlush(offeredServiceType);

		int databaseSizeBeforeUpdate = offeredServiceTypeRepository.findAll().size();

        // Update the offeredServiceType
        offeredServiceType.setName(UPDATED_NAME);

        restOfferedServiceTypeMockMvc.perform(put("/api/offeredServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offeredServiceType)))
                .andExpect(status().isOk());

        // Validate the OfferedServiceType in the database
        List<OfferedServiceType> offeredServiceTypes = offeredServiceTypeRepository.findAll();
        assertThat(offeredServiceTypes).hasSize(databaseSizeBeforeUpdate);
        OfferedServiceType testOfferedServiceType = offeredServiceTypes.get(offeredServiceTypes.size() - 1);
        assertThat(testOfferedServiceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteOfferedServiceType() throws Exception {
        // Initialize the database
        offeredServiceTypeRepository.saveAndFlush(offeredServiceType);

		int databaseSizeBeforeDelete = offeredServiceTypeRepository.findAll().size();

        // Get the offeredServiceType
        restOfferedServiceTypeMockMvc.perform(delete("/api/offeredServiceTypes/{id}", offeredServiceType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OfferedServiceType> offeredServiceTypes = offeredServiceTypeRepository.findAll();
        assertThat(offeredServiceTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
