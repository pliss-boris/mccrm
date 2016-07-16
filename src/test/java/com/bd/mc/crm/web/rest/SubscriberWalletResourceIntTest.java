package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.SubscriberWallet;
import com.bd.mc.crm.repository.SubscriberWalletRepository;

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
 * Test class for the SubscriberWalletResource REST controller.
 *
 * @see SubscriberWalletResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubscriberWalletResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_ACTIVATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ACTIVATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ACTIVATION_DATE_STR = dateTimeFormatter.format(DEFAULT_ACTIVATION_DATE);

    private static final Boolean DEFAULT_EXPIRED = false;
    private static final Boolean UPDATED_EXPIRED = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.format(DEFAULT_CREATED);

    @Inject
    private SubscriberWalletRepository subscriberWalletRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubscriberWalletMockMvc;

    private SubscriberWallet subscriberWallet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriberWalletResource subscriberWalletResource = new SubscriberWalletResource();
        ReflectionTestUtils.setField(subscriberWalletResource, "subscriberWalletRepository", subscriberWalletRepository);
        this.restSubscriberWalletMockMvc = MockMvcBuilders.standaloneSetup(subscriberWalletResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subscriberWallet = new SubscriberWallet();
        subscriberWallet.setActivationDate(DEFAULT_ACTIVATION_DATE);
        subscriberWallet.setExpired(DEFAULT_EXPIRED);
        subscriberWallet.setCreated(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createSubscriberWallet() throws Exception {
        int databaseSizeBeforeCreate = subscriberWalletRepository.findAll().size();

        // Create the SubscriberWallet

        restSubscriberWalletMockMvc.perform(post("/api/subscriber-wallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subscriberWallet)))
                .andExpect(status().isCreated());

        // Validate the SubscriberWallet in the database
        List<SubscriberWallet> subscriberWallets = subscriberWalletRepository.findAll();
        assertThat(subscriberWallets).hasSize(databaseSizeBeforeCreate + 1);
        SubscriberWallet testSubscriberWallet = subscriberWallets.get(subscriberWallets.size() - 1);
        assertThat(testSubscriberWallet.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testSubscriberWallet.isExpired()).isEqualTo(DEFAULT_EXPIRED);
        assertThat(testSubscriberWallet.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void getAllSubscriberWallets() throws Exception {
        // Initialize the database
        subscriberWalletRepository.saveAndFlush(subscriberWallet);

        // Get all the subscriberWallets
        restSubscriberWalletMockMvc.perform(get("/api/subscriber-wallets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subscriberWallet.getId().intValue())))
                .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].expired").value(hasItem(DEFAULT_EXPIRED.booleanValue())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getSubscriberWallet() throws Exception {
        // Initialize the database
        subscriberWalletRepository.saveAndFlush(subscriberWallet);

        // Get the subscriberWallet
        restSubscriberWalletMockMvc.perform(get("/api/subscriber-wallets/{id}", subscriberWallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subscriberWallet.getId().intValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE_STR))
            .andExpect(jsonPath("$.expired").value(DEFAULT_EXPIRED.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriberWallet() throws Exception {
        // Get the subscriberWallet
        restSubscriberWalletMockMvc.perform(get("/api/subscriber-wallets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriberWallet() throws Exception {
        // Initialize the database
        subscriberWalletRepository.saveAndFlush(subscriberWallet);
        int databaseSizeBeforeUpdate = subscriberWalletRepository.findAll().size();

        // Update the subscriberWallet
        SubscriberWallet updatedSubscriberWallet = new SubscriberWallet();
        updatedSubscriberWallet.setId(subscriberWallet.getId());
        updatedSubscriberWallet.setActivationDate(UPDATED_ACTIVATION_DATE);
        updatedSubscriberWallet.setExpired(UPDATED_EXPIRED);
        updatedSubscriberWallet.setCreated(UPDATED_CREATED);

        restSubscriberWalletMockMvc.perform(put("/api/subscriber-wallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubscriberWallet)))
                .andExpect(status().isOk());

        // Validate the SubscriberWallet in the database
        List<SubscriberWallet> subscriberWallets = subscriberWalletRepository.findAll();
        assertThat(subscriberWallets).hasSize(databaseSizeBeforeUpdate);
        SubscriberWallet testSubscriberWallet = subscriberWallets.get(subscriberWallets.size() - 1);
        assertThat(testSubscriberWallet.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testSubscriberWallet.isExpired()).isEqualTo(UPDATED_EXPIRED);
        assertThat(testSubscriberWallet.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void deleteSubscriberWallet() throws Exception {
        // Initialize the database
        subscriberWalletRepository.saveAndFlush(subscriberWallet);
        int databaseSizeBeforeDelete = subscriberWalletRepository.findAll().size();

        // Get the subscriberWallet
        restSubscriberWalletMockMvc.perform(delete("/api/subscriber-wallets/{id}", subscriberWallet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubscriberWallet> subscriberWallets = subscriberWalletRepository.findAll();
        assertThat(subscriberWallets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
