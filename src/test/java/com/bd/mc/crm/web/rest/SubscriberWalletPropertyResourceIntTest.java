package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.SubscriberWalletProperty;
import com.bd.mc.crm.repository.SubscriberWalletPropertyRepository;

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
 * Test class for the SubscriberWalletPropertyResource REST controller.
 *
 * @see SubscriberWalletPropertyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubscriberWalletPropertyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.format(DEFAULT_CREATED);

    @Inject
    private SubscriberWalletPropertyRepository subscriberWalletPropertyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubscriberWalletPropertyMockMvc;

    private SubscriberWalletProperty subscriberWalletProperty;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriberWalletPropertyResource subscriberWalletPropertyResource = new SubscriberWalletPropertyResource();
        ReflectionTestUtils.setField(subscriberWalletPropertyResource, "subscriberWalletPropertyRepository", subscriberWalletPropertyRepository);
        this.restSubscriberWalletPropertyMockMvc = MockMvcBuilders.standaloneSetup(subscriberWalletPropertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subscriberWalletProperty = new SubscriberWalletProperty();
        subscriberWalletProperty.setBalance(DEFAULT_BALANCE);
        subscriberWalletProperty.setCreated(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createSubscriberWalletProperty() throws Exception {
        int databaseSizeBeforeCreate = subscriberWalletPropertyRepository.findAll().size();

        // Create the SubscriberWalletProperty

        restSubscriberWalletPropertyMockMvc.perform(post("/api/subscriber-wallet-properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subscriberWalletProperty)))
                .andExpect(status().isCreated());

        // Validate the SubscriberWalletProperty in the database
        List<SubscriberWalletProperty> subscriberWalletProperties = subscriberWalletPropertyRepository.findAll();
        assertThat(subscriberWalletProperties).hasSize(databaseSizeBeforeCreate + 1);
        SubscriberWalletProperty testSubscriberWalletProperty = subscriberWalletProperties.get(subscriberWalletProperties.size() - 1);
        assertThat(testSubscriberWalletProperty.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testSubscriberWalletProperty.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void getAllSubscriberWalletProperties() throws Exception {
        // Initialize the database
        subscriberWalletPropertyRepository.saveAndFlush(subscriberWalletProperty);

        // Get all the subscriberWalletProperties
        restSubscriberWalletPropertyMockMvc.perform(get("/api/subscriber-wallet-properties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subscriberWalletProperty.getId().intValue())))
                .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getSubscriberWalletProperty() throws Exception {
        // Initialize the database
        subscriberWalletPropertyRepository.saveAndFlush(subscriberWalletProperty);

        // Get the subscriberWalletProperty
        restSubscriberWalletPropertyMockMvc.perform(get("/api/subscriber-wallet-properties/{id}", subscriberWalletProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subscriberWalletProperty.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriberWalletProperty() throws Exception {
        // Get the subscriberWalletProperty
        restSubscriberWalletPropertyMockMvc.perform(get("/api/subscriber-wallet-properties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriberWalletProperty() throws Exception {
        // Initialize the database
        subscriberWalletPropertyRepository.saveAndFlush(subscriberWalletProperty);
        int databaseSizeBeforeUpdate = subscriberWalletPropertyRepository.findAll().size();

        // Update the subscriberWalletProperty
        SubscriberWalletProperty updatedSubscriberWalletProperty = new SubscriberWalletProperty();
        updatedSubscriberWalletProperty.setId(subscriberWalletProperty.getId());
        updatedSubscriberWalletProperty.setBalance(UPDATED_BALANCE);
        updatedSubscriberWalletProperty.setCreated(UPDATED_CREATED);

        restSubscriberWalletPropertyMockMvc.perform(put("/api/subscriber-wallet-properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubscriberWalletProperty)))
                .andExpect(status().isOk());

        // Validate the SubscriberWalletProperty in the database
        List<SubscriberWalletProperty> subscriberWalletProperties = subscriberWalletPropertyRepository.findAll();
        assertThat(subscriberWalletProperties).hasSize(databaseSizeBeforeUpdate);
        SubscriberWalletProperty testSubscriberWalletProperty = subscriberWalletProperties.get(subscriberWalletProperties.size() - 1);
        assertThat(testSubscriberWalletProperty.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testSubscriberWalletProperty.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void deleteSubscriberWalletProperty() throws Exception {
        // Initialize the database
        subscriberWalletPropertyRepository.saveAndFlush(subscriberWalletProperty);
        int databaseSizeBeforeDelete = subscriberWalletPropertyRepository.findAll().size();

        // Get the subscriberWalletProperty
        restSubscriberWalletPropertyMockMvc.perform(delete("/api/subscriber-wallet-properties/{id}", subscriberWalletProperty.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubscriberWalletProperty> subscriberWalletProperties = subscriberWalletPropertyRepository.findAll();
        assertThat(subscriberWalletProperties).hasSize(databaseSizeBeforeDelete - 1);
    }
}
