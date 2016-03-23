package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.ProposedRoom;
import com.zetravelcloud.webapp.repository.ProposedRoomRepository;
import com.zetravelcloud.webapp.service.ProposedRoomService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProposedRoomResource REST controller.
 *
 * @see ProposedRoomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProposedRoomResourceIntTest {

    private static final String DEFAULT_HOTEL_NAME = "AAAAA";
    private static final String UPDATED_HOTEL_NAME = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Inject
    private ProposedRoomRepository proposedRoomRepository;

    @Inject
    private ProposedRoomService proposedRoomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProposedRoomMockMvc;

    private ProposedRoom proposedRoom;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProposedRoomResource proposedRoomResource = new ProposedRoomResource();
        ReflectionTestUtils.setField(proposedRoomResource, "proposedRoomService", proposedRoomService);
        this.restProposedRoomMockMvc = MockMvcBuilders.standaloneSetup(proposedRoomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proposedRoom = new ProposedRoom();
        proposedRoom.setHotelName(DEFAULT_HOTEL_NAME);
        proposedRoom.setType(DEFAULT_TYPE);
        proposedRoom.setUrl(DEFAULT_URL);
        proposedRoom.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createProposedRoom() throws Exception {
        int databaseSizeBeforeCreate = proposedRoomRepository.findAll().size();

        // Create the ProposedRoom

        restProposedRoomMockMvc.perform(post("/api/proposedRooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proposedRoom)))
                .andExpect(status().isCreated());

        // Validate the ProposedRoom in the database
        List<ProposedRoom> proposedRooms = proposedRoomRepository.findAll();
        assertThat(proposedRooms).hasSize(databaseSizeBeforeCreate + 1);
        ProposedRoom testProposedRoom = proposedRooms.get(proposedRooms.size() - 1);
        assertThat(testProposedRoom.getHotelName()).isEqualTo(DEFAULT_HOTEL_NAME);
        assertThat(testProposedRoom.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProposedRoom.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProposedRoom.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void getAllProposedRooms() throws Exception {
        // Initialize the database
        proposedRoomRepository.saveAndFlush(proposedRoom);

        // Get all the proposedRooms
        restProposedRoomMockMvc.perform(get("/api/proposedRooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proposedRoom.getId().intValue())))
                .andExpect(jsonPath("$.[*].hotelName").value(hasItem(DEFAULT_HOTEL_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getProposedRoom() throws Exception {
        // Initialize the database
        proposedRoomRepository.saveAndFlush(proposedRoom);

        // Get the proposedRoom
        restProposedRoomMockMvc.perform(get("/api/proposedRooms/{id}", proposedRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(proposedRoom.getId().intValue()))
            .andExpect(jsonPath("$.hotelName").value(DEFAULT_HOTEL_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProposedRoom() throws Exception {
        // Get the proposedRoom
        restProposedRoomMockMvc.perform(get("/api/proposedRooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposedRoom() throws Exception {
        // Initialize the database
        proposedRoomRepository.saveAndFlush(proposedRoom);

		int databaseSizeBeforeUpdate = proposedRoomRepository.findAll().size();

        // Update the proposedRoom
        proposedRoom.setHotelName(UPDATED_HOTEL_NAME);
        proposedRoom.setType(UPDATED_TYPE);
        proposedRoom.setUrl(UPDATED_URL);
        proposedRoom.setPrice(UPDATED_PRICE);

        restProposedRoomMockMvc.perform(put("/api/proposedRooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proposedRoom)))
                .andExpect(status().isOk());

        // Validate the ProposedRoom in the database
        List<ProposedRoom> proposedRooms = proposedRoomRepository.findAll();
        assertThat(proposedRooms).hasSize(databaseSizeBeforeUpdate);
        ProposedRoom testProposedRoom = proposedRooms.get(proposedRooms.size() - 1);
        assertThat(testProposedRoom.getHotelName()).isEqualTo(UPDATED_HOTEL_NAME);
        assertThat(testProposedRoom.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProposedRoom.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProposedRoom.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteProposedRoom() throws Exception {
        // Initialize the database
        proposedRoomRepository.saveAndFlush(proposedRoom);

		int databaseSizeBeforeDelete = proposedRoomRepository.findAll().size();

        // Get the proposedRoom
        restProposedRoomMockMvc.perform(delete("/api/proposedRooms/{id}", proposedRoom.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProposedRoom> proposedRooms = proposedRoomRepository.findAll();
        assertThat(proposedRooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
