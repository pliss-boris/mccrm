package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.Communication;
import com.bd.mc.crm.repository.CommunicationRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CommunicationResource REST controller.
 *
 * @see CommunicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class CommunicationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_CONTACT_PERSON = "AAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBB";
    private static final String DEFAULT_CONTACT_DESCRIPTON = "AAAAA";
    private static final String UPDATED_CONTACT_DESCRIPTON = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.format(DEFAULT_CREATED);

    @Inject
    private CommunicationRepository communicationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCommunicationMockMvc;

    private Communication communication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommunicationResource communicationResource = new CommunicationResource();
        ReflectionTestUtils.setField(communicationResource, "communicationRepository", communicationRepository);
        this.restCommunicationMockMvc = MockMvcBuilders.standaloneSetup(communicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        communication = new Communication();
        communication.setTitle(DEFAULT_TITLE);
        communication.setContactNumber(DEFAULT_CONTACT_NUMBER);
        communication.setContactPerson(DEFAULT_CONTACT_PERSON);
        communication.setContactDescripton(DEFAULT_CONTACT_DESCRIPTON);
        communication.setCreated(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createCommunication() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();

        // Create the Communication

        restCommunicationMockMvc.perform(post("/api/communications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(communication)))
                .andExpect(status().isCreated());

        // Validate the Communication in the database
        List<Communication> communications = communicationRepository.findAll();
        assertThat(communications).hasSize(databaseSizeBeforeCreate + 1);
        Communication testCommunication = communications.get(communications.size() - 1);
        assertThat(testCommunication.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCommunication.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testCommunication.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testCommunication.getContactDescripton()).isEqualTo(DEFAULT_CONTACT_DESCRIPTON);
        assertThat(testCommunication.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void getAllCommunications() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communications
        restCommunicationMockMvc.perform(get("/api/communications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(communication.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
                .andExpect(jsonPath("$.[*].contactDescripton").value(hasItem(DEFAULT_CONTACT_DESCRIPTON.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", communication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(communication.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.contactDescripton").value(DEFAULT_CONTACT_DESCRIPTON.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCommunication() throws Exception {
        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication
        Communication updatedCommunication = new Communication();
        updatedCommunication.setId(communication.getId());
        updatedCommunication.setTitle(UPDATED_TITLE);
        updatedCommunication.setContactNumber(UPDATED_CONTACT_NUMBER);
        updatedCommunication.setContactPerson(UPDATED_CONTACT_PERSON);
        updatedCommunication.setContactDescripton(UPDATED_CONTACT_DESCRIPTON);
        updatedCommunication.setCreated(UPDATED_CREATED);

        restCommunicationMockMvc.perform(put("/api/communications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCommunication)))
                .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communications = communicationRepository.findAll();
        assertThat(communications).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communications.get(communications.size() - 1);
        assertThat(testCommunication.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCommunication.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testCommunication.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testCommunication.getContactDescripton()).isEqualTo(UPDATED_CONTACT_DESCRIPTON);
        assertThat(testCommunication.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void deleteCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        int databaseSizeBeforeDelete = communicationRepository.findAll().size();

        // Get the communication
        restCommunicationMockMvc.perform(delete("/api/communications/{id}", communication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Communication> communications = communicationRepository.findAll();
        assertThat(communications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
