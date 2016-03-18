package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.TravelRequest;
import com.zetravelcloud.webapp.repository.TravelRequestRepository;
import com.zetravelcloud.webapp.service.TravelRequestService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelRequestResource REST controller.
 *
 * @see TravelRequestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TravelRequestResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CHECKIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHECKOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKOUT = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TravelRequestRepository travelRequestRepository;

    @Inject
    private TravelRequestService travelRequestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelRequestMockMvc;

    private TravelRequest travelRequest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelRequestResource travelRequestResource = new TravelRequestResource();
        ReflectionTestUtils.setField(travelRequestResource, "travelRequestService", travelRequestService);
        this.restTravelRequestMockMvc = MockMvcBuilders.standaloneSetup(travelRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelRequest = new TravelRequest();
        travelRequest.setTitle(DEFAULT_TITLE);
        travelRequest.setDescription(DEFAULT_DESCRIPTION);
        travelRequest.setCheckin(DEFAULT_CHECKIN);
        travelRequest.setCheckout(DEFAULT_CHECKOUT);
    }

    @Test
    @Transactional
    public void createTravelRequest() throws Exception {
        int databaseSizeBeforeCreate = travelRequestRepository.findAll().size();

        // Create the TravelRequest

        restTravelRequestMockMvc.perform(post("/api/travelRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelRequest)))
                .andExpect(status().isCreated());

        // Validate the TravelRequest in the database
        List<TravelRequest> travelRequests = travelRequestRepository.findAll();
        assertThat(travelRequests).hasSize(databaseSizeBeforeCreate + 1);
        TravelRequest testTravelRequest = travelRequests.get(travelRequests.size() - 1);
        assertThat(testTravelRequest.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTravelRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTravelRequest.getCheckin()).isEqualTo(DEFAULT_CHECKIN);
        assertThat(testTravelRequest.getCheckout()).isEqualTo(DEFAULT_CHECKOUT);
    }

    @Test
    @Transactional
    public void checkCheckinIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelRequestRepository.findAll().size();
        // set the field null
        travelRequest.setCheckin(null);

        // Create the TravelRequest, which fails.

        restTravelRequestMockMvc.perform(post("/api/travelRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelRequest)))
                .andExpect(status().isBadRequest());

        List<TravelRequest> travelRequests = travelRequestRepository.findAll();
        assertThat(travelRequests).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelRequestRepository.findAll().size();
        // set the field null
        travelRequest.setCheckout(null);

        // Create the TravelRequest, which fails.

        restTravelRequestMockMvc.perform(post("/api/travelRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelRequest)))
                .andExpect(status().isBadRequest());

        List<TravelRequest> travelRequests = travelRequestRepository.findAll();
        assertThat(travelRequests).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTravelRequests() throws Exception {
        // Initialize the database
        travelRequestRepository.saveAndFlush(travelRequest);

        // Get all the travelRequests
        restTravelRequestMockMvc.perform(get("/api/travelRequests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelRequest.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].checkin").value(hasItem(DEFAULT_CHECKIN.toString())))
                .andExpect(jsonPath("$.[*].checkout").value(hasItem(DEFAULT_CHECKOUT.toString())));
    }

    @Test
    @Transactional
    public void getTravelRequest() throws Exception {
        // Initialize the database
        travelRequestRepository.saveAndFlush(travelRequest);

        // Get the travelRequest
        restTravelRequestMockMvc.perform(get("/api/travelRequests/{id}", travelRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelRequest.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.checkin").value(DEFAULT_CHECKIN.toString()))
            .andExpect(jsonPath("$.checkout").value(DEFAULT_CHECKOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelRequest() throws Exception {
        // Get the travelRequest
        restTravelRequestMockMvc.perform(get("/api/travelRequests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelRequest() throws Exception {
        // Initialize the database
        travelRequestRepository.saveAndFlush(travelRequest);

		int databaseSizeBeforeUpdate = travelRequestRepository.findAll().size();

        // Update the travelRequest
        travelRequest.setTitle(UPDATED_TITLE);
        travelRequest.setDescription(UPDATED_DESCRIPTION);
        travelRequest.setCheckin(UPDATED_CHECKIN);
        travelRequest.setCheckout(UPDATED_CHECKOUT);

        restTravelRequestMockMvc.perform(put("/api/travelRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelRequest)))
                .andExpect(status().isOk());

        // Validate the TravelRequest in the database
        List<TravelRequest> travelRequests = travelRequestRepository.findAll();
        assertThat(travelRequests).hasSize(databaseSizeBeforeUpdate);
        TravelRequest testTravelRequest = travelRequests.get(travelRequests.size() - 1);
        assertThat(testTravelRequest.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTravelRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTravelRequest.getCheckin()).isEqualTo(UPDATED_CHECKIN);
        assertThat(testTravelRequest.getCheckout()).isEqualTo(UPDATED_CHECKOUT);
    }

    @Test
    @Transactional
    public void deleteTravelRequest() throws Exception {
        // Initialize the database
        travelRequestRepository.saveAndFlush(travelRequest);

		int databaseSizeBeforeDelete = travelRequestRepository.findAll().size();

        // Get the travelRequest
        restTravelRequestMockMvc.perform(delete("/api/travelRequests/{id}", travelRequest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelRequest> travelRequests = travelRequestRepository.findAll();
        assertThat(travelRequests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
