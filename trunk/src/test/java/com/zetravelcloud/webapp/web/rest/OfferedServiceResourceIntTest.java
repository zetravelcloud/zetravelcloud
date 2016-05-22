package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.OfferedService;
import com.zetravelcloud.webapp.repository.OfferedServiceRepository;
import com.zetravelcloud.webapp.service.OfferedServiceService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OfferedServiceResource REST controller.
 *
 * @see OfferedServiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OfferedServiceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final BigDecimal DEFAULT_SELLING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELLING_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final Long DEFAULT_DETAILS_ID = 1L;
    private static final Long UPDATED_DETAILS_ID = 2L;

    private static final ZonedDateTime DEFAULT_CONFIRMATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CONFIRMATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CONFIRMATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CONFIRMATION_DATE);

    @Inject
    private OfferedServiceRepository offeredServiceRepository;

    @Inject
    private OfferedServiceService offeredServiceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOfferedServiceMockMvc;

    private OfferedService offeredService;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OfferedServiceResource offeredServiceResource = new OfferedServiceResource();
        ReflectionTestUtils.setField(offeredServiceResource, "offeredServiceService", offeredServiceService);
        this.restOfferedServiceMockMvc = MockMvcBuilders.standaloneSetup(offeredServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        offeredService = new OfferedService();
        offeredService.setSellingPrice(DEFAULT_SELLING_PRICE);
        offeredService.setCost(DEFAULT_COST);
        offeredService.setDetailsId(DEFAULT_DETAILS_ID);
        offeredService.setConfirmationDate(DEFAULT_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void createOfferedService() throws Exception {
        int databaseSizeBeforeCreate = offeredServiceRepository.findAll().size();

        // Create the OfferedService

        restOfferedServiceMockMvc.perform(post("/api/offeredServices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offeredService)))
                .andExpect(status().isCreated());

        // Validate the OfferedService in the database
        List<OfferedService> offeredServices = offeredServiceRepository.findAll();
        assertThat(offeredServices).hasSize(databaseSizeBeforeCreate + 1);
        OfferedService testOfferedService = offeredServices.get(offeredServices.size() - 1);
        assertThat(testOfferedService.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testOfferedService.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testOfferedService.getDetailsId()).isEqualTo(DEFAULT_DETAILS_ID);
        assertThat(testOfferedService.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferedServices() throws Exception {
        // Initialize the database
        offeredServiceRepository.saveAndFlush(offeredService);

        // Get all the offeredServices
        restOfferedServiceMockMvc.perform(get("/api/offeredServices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(offeredService.getId().intValue())))
                .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].detailsId").value(hasItem(DEFAULT_DETAILS_ID.intValue())))
                .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE_STR)));
    }

    @Test
    @Transactional
    public void getOfferedService() throws Exception {
        // Initialize the database
        offeredServiceRepository.saveAndFlush(offeredService);

        // Get the offeredService
        restOfferedServiceMockMvc.perform(get("/api/offeredServices/{id}", offeredService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(offeredService.getId().intValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.intValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.detailsId").value(DEFAULT_DETAILS_ID.intValue()))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingOfferedService() throws Exception {
        // Get the offeredService
        restOfferedServiceMockMvc.perform(get("/api/offeredServices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferedService() throws Exception {
        // Initialize the database
        offeredServiceRepository.saveAndFlush(offeredService);

		int databaseSizeBeforeUpdate = offeredServiceRepository.findAll().size();

        // Update the offeredService
        offeredService.setSellingPrice(UPDATED_SELLING_PRICE);
        offeredService.setCost(UPDATED_COST);
        offeredService.setDetailsId(UPDATED_DETAILS_ID);
        offeredService.setConfirmationDate(UPDATED_CONFIRMATION_DATE);

        restOfferedServiceMockMvc.perform(put("/api/offeredServices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offeredService)))
                .andExpect(status().isOk());

        // Validate the OfferedService in the database
        List<OfferedService> offeredServices = offeredServiceRepository.findAll();
        assertThat(offeredServices).hasSize(databaseSizeBeforeUpdate);
        OfferedService testOfferedService = offeredServices.get(offeredServices.size() - 1);
        assertThat(testOfferedService.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testOfferedService.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testOfferedService.getDetailsId()).isEqualTo(UPDATED_DETAILS_ID);
        assertThat(testOfferedService.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void deleteOfferedService() throws Exception {
        // Initialize the database
        offeredServiceRepository.saveAndFlush(offeredService);

		int databaseSizeBeforeDelete = offeredServiceRepository.findAll().size();

        // Get the offeredService
        restOfferedServiceMockMvc.perform(delete("/api/offeredServices/{id}", offeredService.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OfferedService> offeredServices = offeredServiceRepository.findAll();
        assertThat(offeredServices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
