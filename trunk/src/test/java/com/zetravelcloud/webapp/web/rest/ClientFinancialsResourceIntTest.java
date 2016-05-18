package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.ClientFinancials;
import com.zetravelcloud.webapp.repository.ClientFinancialsRepository;
import com.zetravelcloud.webapp.service.ClientFinancialsService;

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
 * Test class for the ClientFinancialsResource REST controller.
 *
 * @see ClientFinancialsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientFinancialsResourceIntTest {


    private static final Long DEFAULT_CREDIT_LIMIT = 1L;
    private static final Long UPDATED_CREDIT_LIMIT = 2L;

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    @Inject
    private ClientFinancialsRepository clientFinancialsRepository;

    @Inject
    private ClientFinancialsService clientFinancialsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClientFinancialsMockMvc;

    private ClientFinancials clientFinancials;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientFinancialsResource clientFinancialsResource = new ClientFinancialsResource();
        ReflectionTestUtils.setField(clientFinancialsResource, "clientFinancialsService", clientFinancialsService);
        this.restClientFinancialsMockMvc = MockMvcBuilders.standaloneSetup(clientFinancialsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        clientFinancials = new ClientFinancials();
        clientFinancials.setCreditLimit(DEFAULT_CREDIT_LIMIT);
        clientFinancials.setBalance(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createClientFinancials() throws Exception {
        int databaseSizeBeforeCreate = clientFinancialsRepository.findAll().size();

        // Create the ClientFinancials

        restClientFinancialsMockMvc.perform(post("/api/clientFinancialss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientFinancials)))
                .andExpect(status().isCreated());

        // Validate the ClientFinancials in the database
        List<ClientFinancials> clientFinancialss = clientFinancialsRepository.findAll();
        assertThat(clientFinancialss).hasSize(databaseSizeBeforeCreate + 1);
        ClientFinancials testClientFinancials = clientFinancialss.get(clientFinancialss.size() - 1);
        assertThat(testClientFinancials.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testClientFinancials.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void getAllClientFinancialss() throws Exception {
        // Initialize the database
        clientFinancialsRepository.saveAndFlush(clientFinancials);

        // Get all the clientFinancialss
        restClientFinancialsMockMvc.perform(get("/api/clientFinancialss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clientFinancials.getId().intValue())))
                .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
                .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getClientFinancials() throws Exception {
        // Initialize the database
        clientFinancialsRepository.saveAndFlush(clientFinancials);

        // Get the clientFinancials
        restClientFinancialsMockMvc.perform(get("/api/clientFinancialss/{id}", clientFinancials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(clientFinancials.getId().intValue()))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientFinancials() throws Exception {
        // Get the clientFinancials
        restClientFinancialsMockMvc.perform(get("/api/clientFinancialss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientFinancials() throws Exception {
        // Initialize the database
        clientFinancialsRepository.saveAndFlush(clientFinancials);

		int databaseSizeBeforeUpdate = clientFinancialsRepository.findAll().size();

        // Update the clientFinancials
        clientFinancials.setCreditLimit(UPDATED_CREDIT_LIMIT);
        clientFinancials.setBalance(UPDATED_BALANCE);

        restClientFinancialsMockMvc.perform(put("/api/clientFinancialss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientFinancials)))
                .andExpect(status().isOk());

        // Validate the ClientFinancials in the database
        List<ClientFinancials> clientFinancialss = clientFinancialsRepository.findAll();
        assertThat(clientFinancialss).hasSize(databaseSizeBeforeUpdate);
        ClientFinancials testClientFinancials = clientFinancialss.get(clientFinancialss.size() - 1);
        assertThat(testClientFinancials.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testClientFinancials.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void deleteClientFinancials() throws Exception {
        // Initialize the database
        clientFinancialsRepository.saveAndFlush(clientFinancials);

		int databaseSizeBeforeDelete = clientFinancialsRepository.findAll().size();

        // Get the clientFinancials
        restClientFinancialsMockMvc.perform(delete("/api/clientFinancialss/{id}", clientFinancials.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientFinancials> clientFinancialss = clientFinancialsRepository.findAll();
        assertThat(clientFinancialss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
