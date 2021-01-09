package com.github.maikoncanut.mcpspringbatch.batchs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.maikoncanut.mcpspringbatch.batchs.processors.PessoaUpperCaseProcessor;
import com.github.maikoncanut.mcpspringbatch.batchs.writers.PessoaDatabaseWriter;
import com.github.maikoncanut.mcpspringbatch.repositories.PessoaRepository;
import com.github.maikoncanut.mcpspringbatch.services.PessoaService;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { BatchConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BatchIntegrationTest {

    private static final String ARQUIVO_CSV_PESSOA = "src/main/resources/pessoas.csv";
    private static final String BATCH_STATUS_ESPERADO = "COMPLETED";
    private static final String BATCH_INSTANCIA_ESPERADA = "batchLoad";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @MockBean
    private PessoaUpperCaseProcessor pessoaUpperCaseProcessor;

    @MockBean
    private PessoaDatabaseWriter pessoaDatabaseWriter;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private PessoaRepository pessoaRepository;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void givenFilePessoasCsvInputWhenBatchExecutedSuccess() throws Exception {
        final var jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());

        final var atualInstanciaJob = jobExecution.getJobInstance();
        final var atualStatusJob = jobExecution.getExitStatus();

        assertNotNull(atualInstanciaJob);
        assertNotNull(atualInstanciaJob.getJobName());
        assertNotNull(atualStatusJob);
        assertNotNull(atualStatusJob.getExitCode());
        assertEquals(BATCH_INSTANCIA_ESPERADA, atualInstanciaJob.getJobName());
        assertEquals(BATCH_STATUS_ESPERADO, atualStatusJob.getExitCode());
    }

    private JobParameters defaultJobParameters() {
        final var paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", ARQUIVO_CSV_PESSOA);
        return paramsBuilder.toJobParameters();
    }

}
