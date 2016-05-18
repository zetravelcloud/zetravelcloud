package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.Traveler;
import com.zetravelcloud.webapp.repository.TravelerRepository;
import com.zetravelcloud.webapp.service.TravelerService;

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
 * Test class for the TravelerResource REST controller.
 *
 * @see TravelerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TravelerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TravelerRepository travelerRepository;

    @Inject
    private TravelerService travelerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelerMockMvc;

    private Traveler traveler;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelerResource travelerResource = new TravelerResource();
        ReflectionTestUtils.setField(travelerResource, "travelerService", travelerService);
        this.restTravelerMockMvc = MockMvcBuilders.standaloneSetup(travelerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traveler = new Traveler();
        traveler.setName(DEFAULT_NAME);
        traveler.setEmail(DEFAULT_EMAIL);
        traveler.setPhone(DEFAULT_PHONE);
        traveler.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void createTraveler() throws Exception {
        int databaseSizeBeforeCreate = travelerRepository.findAll().size();

        // Create the Traveler

        restTravelerMockMvc.perform(post("/api/travelers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traveler)))
                .andExpect(status().isCreated());

        // Validate the Traveler in the database
        List<Traveler> travelers = travelerRepository.findAll();
        assertThat(travelers).hasSize(databaseSizeBeforeCreate + 1);
        Traveler testTraveler = travelers.get(travelers.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraveler.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTraveler.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTraveler.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllTravelers() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        // Get all the travelers
        restTravelerMockMvc.perform(get("/api/travelers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traveler.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())));
    }

    @Test
    @Transactional
    public void getTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        // Get the traveler
        restTravelerMockMvc.perform(get("/api/travelers/{id}", traveler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(traveler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraveler() throws Exception {
        // Get the traveler
        restTravelerMockMvc.perform(get("/api/travelers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

		int databaseSizeBeforeUpdate = travelerRepository.findAll().size();

        // Update the traveler
        traveler.setName(UPDATED_NAME);
        traveler.setEmail(UPDATED_EMAIL);
        traveler.setPhone(UPDATED_PHONE);
        traveler.setDateOfBirth(UPDATED_DATE_OF_BIRTH);

        restTravelerMockMvc.perform(put("/api/travelers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traveler)))
                .andExpect(status().isOk());

        // Validate the Traveler in the database
        List<Traveler> travelers = travelerRepository.findAll();
        assertThat(travelers).hasSize(databaseSizeBeforeUpdate);
        Traveler testTraveler = travelers.get(travelers.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraveler.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTraveler.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTraveler.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void deleteTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

		int databaseSizeBeforeDelete = travelerRepository.findAll().size();

        // Get the traveler
        restTravelerMockMvc.perform(delete("/api/travelers/{id}", traveler.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Traveler> travelers = travelerRepository.findAll();
        assertThat(travelers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
