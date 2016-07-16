package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.CustomerAddres;
import com.bd.mc.crm.repository.CustomerAddresRepository;

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

import com.bd.mc.crm.domain.enumeration.AdressType;

/**
 * Test class for the CustomerAddresResource REST controller.
 *
 * @see CustomerAddresResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerAddresResourceIntTest {


    private static final AdressType DEFAULT_ADRESS_TYPE = AdressType.REGULAR;
    private static final AdressType UPDATED_ADRESS_TYPE = AdressType.INVOICE;
    private static final String DEFAULT_COUNTRY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CITY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_HOME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_HOME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_HOME_LETTER = "AAAAAAAAAAAAAAAA";
    private static final String UPDATED_HOME_LETTER = "BBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FLAT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FLAT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_ZIP = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private CustomerAddresRepository customerAddresRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerAddresMockMvc;

    private CustomerAddres customerAddres;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerAddresResource customerAddresResource = new CustomerAddresResource();
        ReflectionTestUtils.setField(customerAddresResource, "customerAddresRepository", customerAddresRepository);
        this.restCustomerAddresMockMvc = MockMvcBuilders.standaloneSetup(customerAddresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerAddres = new CustomerAddres();
        customerAddres.setAdressType(DEFAULT_ADRESS_TYPE);
        customerAddres.setCountry(DEFAULT_COUNTRY);
        customerAddres.setCity(DEFAULT_CITY);
        customerAddres.setHome(DEFAULT_HOME);
        customerAddres.setHomeLetter(DEFAULT_HOME_LETTER);
        customerAddres.setFlat(DEFAULT_FLAT);
        customerAddres.setZip(DEFAULT_ZIP);
        customerAddres.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerAddres() throws Exception {
        int databaseSizeBeforeCreate = customerAddresRepository.findAll().size();

        // Create the CustomerAddres

        restCustomerAddresMockMvc.perform(post("/api/customer-addres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerAddres)))
                .andExpect(status().isCreated());

        // Validate the CustomerAddres in the database
        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        assertThat(customerAddres).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAddres testCustomerAddres = customerAddres.get(customerAddres.size() - 1);
        assertThat(testCustomerAddres.getAdressType()).isEqualTo(DEFAULT_ADRESS_TYPE);
        assertThat(testCustomerAddres.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustomerAddres.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerAddres.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testCustomerAddres.getHomeLetter()).isEqualTo(DEFAULT_HOME_LETTER);
        assertThat(testCustomerAddres.getFlat()).isEqualTo(DEFAULT_FLAT);
        assertThat(testCustomerAddres.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testCustomerAddres.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void checkAdressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAddresRepository.findAll().size();
        // set the field null
        customerAddres.setAdressType(null);

        // Create the CustomerAddres, which fails.

        restCustomerAddresMockMvc.perform(post("/api/customer-addres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerAddres)))
                .andExpect(status().isBadRequest());

        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        assertThat(customerAddres).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAddresRepository.findAll().size();
        // set the field null
        customerAddres.setCountry(null);

        // Create the CustomerAddres, which fails.

        restCustomerAddresMockMvc.perform(post("/api/customer-addres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerAddres)))
                .andExpect(status().isBadRequest());

        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        assertThat(customerAddres).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerAddres() throws Exception {
        // Initialize the database
        customerAddresRepository.saveAndFlush(customerAddres);

        // Get all the customerAddres
        restCustomerAddresMockMvc.perform(get("/api/customer-addres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerAddres.getId().intValue())))
                .andExpect(jsonPath("$.[*].adressType").value(hasItem(DEFAULT_ADRESS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME.toString())))
                .andExpect(jsonPath("$.[*].homeLetter").value(hasItem(DEFAULT_HOME_LETTER.toString())))
                .andExpect(jsonPath("$.[*].flat").value(hasItem(DEFAULT_FLAT.toString())))
                .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCustomerAddres() throws Exception {
        // Initialize the database
        customerAddresRepository.saveAndFlush(customerAddres);

        // Get the customerAddres
        restCustomerAddresMockMvc.perform(get("/api/customer-addres/{id}", customerAddres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customerAddres.getId().intValue()))
            .andExpect(jsonPath("$.adressType").value(DEFAULT_ADRESS_TYPE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME.toString()))
            .andExpect(jsonPath("$.homeLetter").value(DEFAULT_HOME_LETTER.toString()))
            .andExpect(jsonPath("$.flat").value(DEFAULT_FLAT.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerAddres() throws Exception {
        // Get the customerAddres
        restCustomerAddresMockMvc.perform(get("/api/customer-addres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAddres() throws Exception {
        // Initialize the database
        customerAddresRepository.saveAndFlush(customerAddres);
        int databaseSizeBeforeUpdate = customerAddresRepository.findAll().size();

        // Update the customerAddres
        CustomerAddres updatedCustomerAddres = new CustomerAddres();
        updatedCustomerAddres.setId(customerAddres.getId());
        updatedCustomerAddres.setAdressType(UPDATED_ADRESS_TYPE);
        updatedCustomerAddres.setCountry(UPDATED_COUNTRY);
        updatedCustomerAddres.setCity(UPDATED_CITY);
        updatedCustomerAddres.setHome(UPDATED_HOME);
        updatedCustomerAddres.setHomeLetter(UPDATED_HOME_LETTER);
        updatedCustomerAddres.setFlat(UPDATED_FLAT);
        updatedCustomerAddres.setZip(UPDATED_ZIP);
        updatedCustomerAddres.setIsActive(UPDATED_IS_ACTIVE);

        restCustomerAddresMockMvc.perform(put("/api/customer-addres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCustomerAddres)))
                .andExpect(status().isOk());

        // Validate the CustomerAddres in the database
        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        assertThat(customerAddres).hasSize(databaseSizeBeforeUpdate);
        CustomerAddres testCustomerAddres = customerAddres.get(customerAddres.size() - 1);
        assertThat(testCustomerAddres.getAdressType()).isEqualTo(UPDATED_ADRESS_TYPE);
        assertThat(testCustomerAddres.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustomerAddres.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerAddres.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testCustomerAddres.getHomeLetter()).isEqualTo(UPDATED_HOME_LETTER);
        assertThat(testCustomerAddres.getFlat()).isEqualTo(UPDATED_FLAT);
        assertThat(testCustomerAddres.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testCustomerAddres.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteCustomerAddres() throws Exception {
        // Initialize the database
        customerAddresRepository.saveAndFlush(customerAddres);
        int databaseSizeBeforeDelete = customerAddresRepository.findAll().size();

        // Get the customerAddres
        restCustomerAddresMockMvc.perform(delete("/api/customer-addres/{id}", customerAddres.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerAddres> customerAddres = customerAddresRepository.findAll();
        assertThat(customerAddres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
