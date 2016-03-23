package com.zetravelcloud.webapp.web.rest;

import com.zetravelcloud.webapp.Application;
import com.zetravelcloud.webapp.domain.Proposal;
import com.zetravelcloud.webapp.repository.ProposalRepository;
import com.zetravelcloud.webapp.service.ProposalService;

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
 * Test class for the ProposalResource REST controller.
 *
 * @see ProposalResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProposalResourceIntTest {


    private static final LocalDate DEFAULT_CHECKIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHECKOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECKOUT = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ProposalRepository proposalRepository;

    @Inject
    private ProposalService proposalService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProposalMockMvc;

    private Proposal proposal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProposalResource proposalResource = new ProposalResource();
        ReflectionTestUtils.setField(proposalResource, "proposalService", proposalService);
        this.restProposalMockMvc = MockMvcBuilders.standaloneSetup(proposalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proposal = new Proposal();
        proposal.setCheckin(DEFAULT_CHECKIN);
        proposal.setCheckout(DEFAULT_CHECKOUT);
    }

    @Test
    @Transactional
    public void createProposal() throws Exception {
        int databaseSizeBeforeCreate = proposalRepository.findAll().size();

        // Create the Proposal

        restProposalMockMvc.perform(post("/api/proposals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proposal)))
                .andExpect(status().isCreated());

        // Validate the Proposal in the database
        List<Proposal> proposals = proposalRepository.findAll();
        assertThat(proposals).hasSize(databaseSizeBeforeCreate + 1);
        Proposal testProposal = proposals.get(proposals.size() - 1);
        assertThat(testProposal.getCheckin()).isEqualTo(DEFAULT_CHECKIN);
        assertThat(testProposal.getCheckout()).isEqualTo(DEFAULT_CHECKOUT);
    }

    @Test
    @Transactional
    public void getAllProposals() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposals
        restProposalMockMvc.perform(get("/api/proposals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proposal.getId().intValue())))
                .andExpect(jsonPath("$.[*].checkin").value(hasItem(DEFAULT_CHECKIN.toString())))
                .andExpect(jsonPath("$.[*].checkout").value(hasItem(DEFAULT_CHECKOUT.toString())));
    }

    @Test
    @Transactional
    public void getProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get the proposal
        restProposalMockMvc.perform(get("/api/proposals/{id}", proposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(proposal.getId().intValue()))
            .andExpect(jsonPath("$.checkin").value(DEFAULT_CHECKIN.toString()))
            .andExpect(jsonPath("$.checkout").value(DEFAULT_CHECKOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProposal() throws Exception {
        // Get the proposal
        restProposalMockMvc.perform(get("/api/proposals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

		int databaseSizeBeforeUpdate = proposalRepository.findAll().size();

        // Update the proposal
        proposal.setCheckin(UPDATED_CHECKIN);
        proposal.setCheckout(UPDATED_CHECKOUT);

        restProposalMockMvc.perform(put("/api/proposals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proposal)))
                .andExpect(status().isOk());

        // Validate the Proposal in the database
        List<Proposal> proposals = proposalRepository.findAll();
        assertThat(proposals).hasSize(databaseSizeBeforeUpdate);
        Proposal testProposal = proposals.get(proposals.size() - 1);
        assertThat(testProposal.getCheckin()).isEqualTo(UPDATED_CHECKIN);
        assertThat(testProposal.getCheckout()).isEqualTo(UPDATED_CHECKOUT);
    }

    @Test
    @Transactional
    public void deleteProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

		int databaseSizeBeforeDelete = proposalRepository.findAll().size();

        // Get the proposal
        restProposalMockMvc.perform(delete("/api/proposals/{id}", proposal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proposal> proposals = proposalRepository.findAll();
        assertThat(proposals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
