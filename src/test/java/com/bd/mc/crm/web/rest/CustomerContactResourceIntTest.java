package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.CustomerContact;
import com.bd.mc.crm.repository.CustomerContactRepository;

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

import com.bd.mc.crm.domain.enumeration.ContactType;

/**
 * Test class for the CustomerContactResource REST controller.
 *
 * @see CustomerContactResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerContactResourceIntTest {

    private static final String DEFAULT_GREETING = "AAAAA";
    private static final String UPDATED_GREETING = "BBBBB";
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_MEMO = "AAAAA";
    private static final String UPDATED_MEMO = "BBBBB";

    private static final ContactType DEFAULT_CONTACT_TYPE = ContactType.REGULAR;
    private static final ContactType UPDATED_CONTACT_TYPE = ContactType.CONTACT;

    @Inject
    private CustomerContactRepository customerContactRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerContactMockMvc;

    private CustomerContact customerContact;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerContactResource customerContactResource = new CustomerContactResource();
        ReflectionTestUtils.setField(customerContactResource, "customerContactRepository", customerContactRepository);
        this.restCustomerContactMockMvc = MockMvcBuilders.standaloneSetup(customerContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerContact = new CustomerContact();
        customerContact.setGreeting(DEFAULT_GREETING);
        customerContact.setFirstName(DEFAULT_FIRST_NAME);
        customerContact.setLastName(DEFAULT_LAST_NAME);
        customerContact.setMobile(DEFAULT_MOBILE);
        customerContact.setFax(DEFAULT_FAX);
        customerContact.setEmail(DEFAULT_EMAIL);
        customerContact.setMemo(DEFAULT_MEMO);
        customerContact.setContactType(DEFAULT_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void createCustomerContact() throws Exception {
        int databaseSizeBeforeCreate = customerContactRepository.findAll().size();

        // Create the CustomerContact

        restCustomerContactMockMvc.perform(post("/api/customer-contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerContact)))
                .andExpect(status().isCreated());

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContacts = customerContactRepository.findAll();
        assertThat(customerContacts).hasSize(databaseSizeBeforeCreate + 1);
        CustomerContact testCustomerContact = customerContacts.get(customerContacts.size() - 1);
        assertThat(testCustomerContact.getGreeting()).isEqualTo(DEFAULT_GREETING);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerContact.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustomerContact.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCustomerContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerContact.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCustomerContact.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCustomerContacts() throws Exception {
        // Initialize the database
        customerContactRepository.saveAndFlush(customerContact);

        // Get all the customerContacts
        restCustomerContactMockMvc.perform(get("/api/customer-contacts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerContact.getId().intValue())))
                .andExpect(jsonPath("$.[*].greeting").value(hasItem(DEFAULT_GREETING.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO.toString())))
                .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCustomerContact() throws Exception {
        // Initialize the database
        customerContactRepository.saveAndFlush(customerContact);

        // Get the customerContact
        restCustomerContactMockMvc.perform(get("/api/customer-contacts/{id}", customerContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customerContact.getId().intValue()))
            .andExpect(jsonPath("$.greeting").value(DEFAULT_GREETING.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO.toString()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerContact() throws Exception {
        // Get the customerContact
        restCustomerContactMockMvc.perform(get("/api/customer-contacts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerContact() throws Exception {
        // Initialize the database
        customerContactRepository.saveAndFlush(customerContact);
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().size();

        // Update the customerContact
        CustomerContact updatedCustomerContact = new CustomerContact();
        updatedCustomerContact.setId(customerContact.getId());
        updatedCustomerContact.setGreeting(UPDATED_GREETING);
        updatedCustomerContact.setFirstName(UPDATED_FIRST_NAME);
        updatedCustomerContact.setLastName(UPDATED_LAST_NAME);
        updatedCustomerContact.setMobile(UPDATED_MOBILE);
        updatedCustomerContact.setFax(UPDATED_FAX);
        updatedCustomerContact.setEmail(UPDATED_EMAIL);
        updatedCustomerContact.setMemo(UPDATED_MEMO);
        updatedCustomerContact.setContactType(UPDATED_CONTACT_TYPE);

        restCustomerContactMockMvc.perform(put("/api/customer-contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCustomerContact)))
                .andExpect(status().isOk());

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContacts = customerContactRepository.findAll();
        assertThat(customerContacts).hasSize(databaseSizeBeforeUpdate);
        CustomerContact testCustomerContact = customerContacts.get(customerContacts.size() - 1);
        assertThat(testCustomerContact.getGreeting()).isEqualTo(UPDATED_GREETING);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerContact.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomerContact.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCustomerContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerContact.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCustomerContact.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void deleteCustomerContact() throws Exception {
        // Initialize the database
        customerContactRepository.saveAndFlush(customerContact);
        int databaseSizeBeforeDelete = customerContactRepository.findAll().size();

        // Get the customerContact
        restCustomerContactMockMvc.perform(delete("/api/customer-contacts/{id}", customerContact.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerContact> customerContacts = customerContactRepository.findAll();
        assertThat(customerContacts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
