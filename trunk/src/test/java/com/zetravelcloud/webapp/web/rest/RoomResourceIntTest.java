package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.Room;
import com.zetravelcloud.webapp.repository.RoomRepository;
import com.zetravelcloud.webapp.service.RoomService;

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
 * Test class for the RoomResource REST controller.
 *
 * @see RoomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RoomResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final Integer DEFAULT_NB_OF_ADULTS = 1;
    private static final Integer UPDATED_NB_OF_ADULTS = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Inject
    private RoomRepository roomRepository;

    @Inject
    private RoomService roomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRoomMockMvc;

    private Room room;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoomResource roomResource = new RoomResource();
        ReflectionTestUtils.setField(roomResource, "roomService", roomService);
        this.restRoomMockMvc = MockMvcBuilders.standaloneSetup(roomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        room = new Room();
        room.setType(DEFAULT_TYPE);
        room.setNbOfAdults(DEFAULT_NB_OF_ADULTS);
        room.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room

        restRoomMockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = rooms.get(rooms.size() - 1);
        assertThat(testRoom.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRoom.getNbOfAdults()).isEqualTo(DEFAULT_NB_OF_ADULTS);
        assertThat(testRoom.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the rooms
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].nbOfAdults").value(hasItem(DEFAULT_NB_OF_ADULTS)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nbOfAdults").value(DEFAULT_NB_OF_ADULTS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

		int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        room.setType(UPDATED_TYPE);
        room.setNbOfAdults(UPDATED_NB_OF_ADULTS);
        room.setPrice(UPDATED_PRICE);

        restRoomMockMvc.perform(put("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = rooms.get(rooms.size() - 1);
        assertThat(testRoom.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoom.getNbOfAdults()).isEqualTo(UPDATED_NB_OF_ADULTS);
        assertThat(testRoom.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

		int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Get the room
        restRoomMockMvc.perform(delete("/api/rooms/{id}", room.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
