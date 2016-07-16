package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.Subscriber;
import com.bd.mc.crm.repository.SubscriberRepository;

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
 * Test class for the SubscriberResource REST controller.
 *
 * @see SubscriberResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubscriberResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_SUBSCRIBER_TYPE = "AAAAA";
    private static final String UPDATED_SUBSCRIBER_TYPE = "BBBBB";

    private static final Integer DEFAULT_SUBSCRIBER_PAYMENT_CLASS = 1;
    private static final Integer UPDATED_SUBSCRIBER_PAYMENT_CLASS = 2;

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);

    @Inject
    private SubscriberRepository subscriberRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubscriberMockMvc;

    private Subscriber subscriber;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriberResource subscriberResource = new SubscriberResource();
        ReflectionTestUtils.setField(subscriberResource, "subscriberRepository", subscriberRepository);
        this.restSubscriberMockMvc = MockMvcBuilders.standaloneSetup(subscriberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subscriber = new Subscriber();
        subscriber.setSubscriberType(DEFAULT_SUBSCRIBER_TYPE);
        subscriber.setSubscriberPaymentClass(DEFAULT_SUBSCRIBER_PAYMENT_CLASS);
        subscriber.setCreationDate(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createSubscriber() throws Exception {
        int databaseSizeBeforeCreate = subscriberRepository.findAll().size();

        // Create the Subscriber

        restSubscriberMockMvc.perform(post("/api/subscribers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subscriber)))
                .andExpect(status().isCreated());

        // Validate the Subscriber in the database
        List<Subscriber> subscribers = subscriberRepository.findAll();
        assertThat(subscribers).hasSize(databaseSizeBeforeCreate + 1);
        Subscriber testSubscriber = subscribers.get(subscribers.size() - 1);
        assertThat(testSubscriber.getSubscriberType()).isEqualTo(DEFAULT_SUBSCRIBER_TYPE);
        assertThat(testSubscriber.getSubscriberPaymentClass()).isEqualTo(DEFAULT_SUBSCRIBER_PAYMENT_CLASS);
        assertThat(testSubscriber.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubscribers() throws Exception {
        // Initialize the database
        subscriberRepository.saveAndFlush(subscriber);

        // Get all the subscribers
        restSubscriberMockMvc.perform(get("/api/subscribers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subscriber.getId().intValue())))
                .andExpect(jsonPath("$.[*].subscriberType").value(hasItem(DEFAULT_SUBSCRIBER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].subscriberPaymentClass").value(hasItem(DEFAULT_SUBSCRIBER_PAYMENT_CLASS)))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)));
    }

    @Test
    @Transactional
    public void getSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.saveAndFlush(subscriber);

        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", subscriber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subscriber.getId().intValue()))
            .andExpect(jsonPath("$.subscriberType").value(DEFAULT_SUBSCRIBER_TYPE.toString()))
            .andExpect(jsonPath("$.subscriberPaymentClass").value(DEFAULT_SUBSCRIBER_PAYMENT_CLASS))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriber() throws Exception {
        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.saveAndFlush(subscriber);
        int databaseSizeBeforeUpdate = subscriberRepository.findAll().size();

        // Update the subscriber
        Subscriber updatedSubscriber = new Subscriber();
        updatedSubscriber.setId(subscriber.getId());
        updatedSubscriber.setSubscriberType(UPDATED_SUBSCRIBER_TYPE);
        updatedSubscriber.setSubscriberPaymentClass(UPDATED_SUBSCRIBER_PAYMENT_CLASS);
        updatedSubscriber.setCreationDate(UPDATED_CREATION_DATE);

        restSubscriberMockMvc.perform(put("/api/subscribers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubscriber)))
                .andExpect(status().isOk());

        // Validate the Subscriber in the database
        List<Subscriber> subscribers = subscriberRepository.findAll();
        assertThat(subscribers).hasSize(databaseSizeBeforeUpdate);
        Subscriber testSubscriber = subscribers.get(subscribers.size() - 1);
        assertThat(testSubscriber.getSubscriberType()).isEqualTo(UPDATED_SUBSCRIBER_TYPE);
        assertThat(testSubscriber.getSubscriberPaymentClass()).isEqualTo(UPDATED_SUBSCRIBER_PAYMENT_CLASS);
        assertThat(testSubscriber.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void deleteSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.saveAndFlush(subscriber);
        int databaseSizeBeforeDelete = subscriberRepository.findAll().size();

        // Get the subscriber
        restSubscriberMockMvc.perform(delete("/api/subscribers/{id}", subscriber.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subscriber> subscribers = subscriberRepository.findAll();
        assertThat(subscribers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
