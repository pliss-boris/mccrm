package com.bd.mc.crm.web.rest;

import com.bd.mc.crm.MccrmApp;
import com.bd.mc.crm.domain.Lre;
import com.bd.mc.crm.repository.LreRepository;

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
 * Test class for the LreResource REST controller.
 *
 * @see LreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MccrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class LreResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private LreRepository lreRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLreMockMvc;

    private Lre lre;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LreResource lreResource = new LreResource();
        ReflectionTestUtils.setField(lreResource, "lreRepository", lreRepository);
        this.restLreMockMvc = MockMvcBuilders.standaloneSetup(lreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lre = new Lre();
        lre.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLre() throws Exception {
        int databaseSizeBeforeCreate = lreRepository.findAll().size();

        // Create the Lre

        restLreMockMvc.perform(post("/api/lres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lre)))
                .andExpect(status().isCreated());

        // Validate the Lre in the database
        List<Lre> lres = lreRepository.findAll();
        assertThat(lres).hasSize(databaseSizeBeforeCreate + 1);
        Lre testLre = lres.get(lres.size() - 1);
        assertThat(testLre.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = lreRepository.findAll().size();
        // set the field null
        lre.setDescription(null);

        // Create the Lre, which fails.

        restLreMockMvc.perform(post("/api/lres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lre)))
                .andExpect(status().isBadRequest());

        List<Lre> lres = lreRepository.findAll();
        assertThat(lres).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLres() throws Exception {
        // Initialize the database
        lreRepository.saveAndFlush(lre);

        // Get all the lres
        restLreMockMvc.perform(get("/api/lres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lre.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLre() throws Exception {
        // Initialize the database
        lreRepository.saveAndFlush(lre);

        // Get the lre
        restLreMockMvc.perform(get("/api/lres/{id}", lre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lre.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLre() throws Exception {
        // Get the lre
        restLreMockMvc.perform(get("/api/lres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLre() throws Exception {
        // Initialize the database
        lreRepository.saveAndFlush(lre);
        int databaseSizeBeforeUpdate = lreRepository.findAll().size();

        // Update the lre
        Lre updatedLre = new Lre();
        updatedLre.setId(lre.getId());
        updatedLre.setDescription(UPDATED_DESCRIPTION);

        restLreMockMvc.perform(put("/api/lres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLre)))
                .andExpect(status().isOk());

        // Validate the Lre in the database
        List<Lre> lres = lreRepository.findAll();
        assertThat(lres).hasSize(databaseSizeBeforeUpdate);
        Lre testLre = lres.get(lres.size() - 1);
        assertThat(testLre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteLre() throws Exception {
        // Initialize the database
        lreRepository.saveAndFlush(lre);
        int databaseSizeBeforeDelete = lreRepository.findAll().size();

        // Get the lre
        restLreMockMvc.perform(delete("/api/lres/{id}", lre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lre> lres = lreRepository.findAll();
        assertThat(lres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
