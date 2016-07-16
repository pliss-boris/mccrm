package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.ClassOfService;
import com.bd.mc.crm.repository.ClassOfServiceRepository;

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
 * Test class for the ClassOfServiceResource REST controller.
 *
 * @see ClassOfServiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class ClassOfServiceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.format(DEFAULT_CREATED);

    @Inject
    private ClassOfServiceRepository classOfServiceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClassOfServiceMockMvc;

    private ClassOfService classOfService;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassOfServiceResource classOfServiceResource = new ClassOfServiceResource();
        ReflectionTestUtils.setField(classOfServiceResource, "classOfServiceRepository", classOfServiceRepository);
        this.restClassOfServiceMockMvc = MockMvcBuilders.standaloneSetup(classOfServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        classOfService = new ClassOfService();
        classOfService.setDescription(DEFAULT_DESCRIPTION);
        classOfService.setCreated(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createClassOfService() throws Exception {
        int databaseSizeBeforeCreate = classOfServiceRepository.findAll().size();

        // Create the ClassOfService

        restClassOfServiceMockMvc.perform(post("/api/class-of-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classOfService)))
                .andExpect(status().isCreated());

        // Validate the ClassOfService in the database
        List<ClassOfService> classOfServices = classOfServiceRepository.findAll();
        assertThat(classOfServices).hasSize(databaseSizeBeforeCreate + 1);
        ClassOfService testClassOfService = classOfServices.get(classOfServices.size() - 1);
        assertThat(testClassOfService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClassOfService.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void getAllClassOfServices() throws Exception {
        // Initialize the database
        classOfServiceRepository.saveAndFlush(classOfService);

        // Get all the classOfServices
        restClassOfServiceMockMvc.perform(get("/api/class-of-services?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classOfService.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getClassOfService() throws Exception {
        // Initialize the database
        classOfServiceRepository.saveAndFlush(classOfService);

        // Get the classOfService
        restClassOfServiceMockMvc.perform(get("/api/class-of-services/{id}", classOfService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classOfService.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingClassOfService() throws Exception {
        // Get the classOfService
        restClassOfServiceMockMvc.perform(get("/api/class-of-services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassOfService() throws Exception {
        // Initialize the database
        classOfServiceRepository.saveAndFlush(classOfService);
        int databaseSizeBeforeUpdate = classOfServiceRepository.findAll().size();

        // Update the classOfService
        ClassOfService updatedClassOfService = new ClassOfService();
        updatedClassOfService.setId(classOfService.getId());
        updatedClassOfService.setDescription(UPDATED_DESCRIPTION);
        updatedClassOfService.setCreated(UPDATED_CREATED);

        restClassOfServiceMockMvc.perform(put("/api/class-of-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClassOfService)))
                .andExpect(status().isOk());

        // Validate the ClassOfService in the database
        List<ClassOfService> classOfServices = classOfServiceRepository.findAll();
        assertThat(classOfServices).hasSize(databaseSizeBeforeUpdate);
        ClassOfService testClassOfService = classOfServices.get(classOfServices.size() - 1);
        assertThat(testClassOfService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClassOfService.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void deleteClassOfService() throws Exception {
        // Initialize the database
        classOfServiceRepository.saveAndFlush(classOfService);
        int databaseSizeBeforeDelete = classOfServiceRepository.findAll().size();

        // Get the classOfService
        restClassOfServiceMockMvc.perform(delete("/api/class-of-services/{id}", classOfService.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassOfService> classOfServices = classOfServiceRepository.findAll();
        assertThat(classOfServices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
