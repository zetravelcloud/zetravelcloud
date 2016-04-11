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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CHECKIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHECKOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKOUT = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);
    private static final String DEFAULT_FILE_ID = "AAAAA";
    private static final String UPDATED_FILE_ID = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_SENT_TO_ACCOUNTING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_SENT_TO_ACCOUNTING = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_SENT_TO_ACCOUNTING_STR = dateTimeFormatter.format(DEFAULT_DATE_SENT_TO_ACCOUNTING);
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";
    private static final String DEFAULT_PAYMENT_TYPE = "AAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBB";

    private static final Integer DEFAULT_NUM_OF_ADULTS = 1;
    private static final Integer UPDATED_NUM_OF_ADULTS = 2;

    private static final Integer DEFAULT_NUM_OFCHILDREN = 1;
    private static final Integer UPDATED_NUM_OFCHILDREN = 2;
    private static final String DEFAULT_DESTINATION = "AAAAA";
    private static final String UPDATED_DESTINATION = "BBBBB";

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
        travelRequest.setDate(DEFAULT_DATE);
        travelRequest.setFileId(DEFAULT_FILE_ID);
        travelRequest.setDateSentToAccounting(DEFAULT_DATE_SENT_TO_ACCOUNTING);
        travelRequest.setStatus(DEFAULT_STATUS);
        travelRequest.setPaymentType(DEFAULT_PAYMENT_TYPE);
        travelRequest.setNumOfAdults(DEFAULT_NUM_OF_ADULTS);
        travelRequest.setNumOfchildren(DEFAULT_NUM_OFCHILDREN);
        travelRequest.setDestination(DEFAULT_DESTINATION);
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
        assertThat(testTravelRequest.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTravelRequest.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testTravelRequest.getDateSentToAccounting()).isEqualTo(DEFAULT_DATE_SENT_TO_ACCOUNTING);
        assertThat(testTravelRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTravelRequest.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testTravelRequest.getNumOfAdults()).isEqualTo(DEFAULT_NUM_OF_ADULTS);
        assertThat(testTravelRequest.getNumOfchildren()).isEqualTo(DEFAULT_NUM_OFCHILDREN);
        assertThat(testTravelRequest.getDestination()).isEqualTo(DEFAULT_DESTINATION);
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
                .andExpect(jsonPath("$.[*].checkout").value(hasItem(DEFAULT_CHECKOUT.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.toString())))
                .andExpect(jsonPath("$.[*].dateSentToAccounting").value(hasItem(DEFAULT_DATE_SENT_TO_ACCOUNTING_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].numOfAdults").value(hasItem(DEFAULT_NUM_OF_ADULTS)))
                .andExpect(jsonPath("$.[*].numOfchildren").value(hasItem(DEFAULT_NUM_OFCHILDREN)))
                .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())));
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
            .andExpect(jsonPath("$.checkout").value(DEFAULT_CHECKOUT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.toString()))
            .andExpect(jsonPath("$.dateSentToAccounting").value(DEFAULT_DATE_SENT_TO_ACCOUNTING_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.numOfAdults").value(DEFAULT_NUM_OF_ADULTS))
            .andExpect(jsonPath("$.numOfchildren").value(DEFAULT_NUM_OFCHILDREN))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()));
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
        travelRequest.setDate(UPDATED_DATE);
        travelRequest.setFileId(UPDATED_FILE_ID);
        travelRequest.setDateSentToAccounting(UPDATED_DATE_SENT_TO_ACCOUNTING);
        travelRequest.setStatus(UPDATED_STATUS);
        travelRequest.setPaymentType(UPDATED_PAYMENT_TYPE);
        travelRequest.setNumOfAdults(UPDATED_NUM_OF_ADULTS);
        travelRequest.setNumOfchildren(UPDATED_NUM_OFCHILDREN);
        travelRequest.setDestination(UPDATED_DESTINATION);

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
        assertThat(testTravelRequest.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTravelRequest.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testTravelRequest.getDateSentToAccounting()).isEqualTo(UPDATED_DATE_SENT_TO_ACCOUNTING);
        assertThat(testTravelRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTravelRequest.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testTravelRequest.getNumOfAdults()).isEqualTo(UPDATED_NUM_OF_ADULTS);
        assertThat(testTravelRequest.getNumOfchildren()).isEqualTo(UPDATED_NUM_OFCHILDREN);
        assertThat(testTravelRequest.getDestination()).isEqualTo(UPDATED_DESTINATION);
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
