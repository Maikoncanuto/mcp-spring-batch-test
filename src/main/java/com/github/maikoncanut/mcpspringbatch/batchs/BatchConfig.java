package com.github.maikoncanut.mcpspringbatch.batchs;

import com.github.maikoncanut.mcpspringbatch.models.Pessoa;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory,
            final ItemReader<Pessoa> itemReader, final ItemProcessor<Pessoa, Pessoa> itemProcessor,
            final ItemWriter<Pessoa> itemWriter) {

        final var step = stepBuilderFactory
                .get("batchFileLoad").<Pessoa, Pessoa>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory
                .get("batchLoad")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Pessoa> itemReader(@Value("#{jobParameters['file.input']}") final String input) {
        final var flatFile = new FlatFileItemReader<Pessoa>();
        flatFile.setResource(new FileSystemResource(input));
        flatFile.setName("batchCSV");
        flatFile.setLinesToSkip(1);
        flatFile.setLineMapper(lineMapper());
        return flatFile;
    }

    @Bean
    public LineMapper<Pessoa> lineMapper() {

        DefaultLineMapper<Pessoa> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "nome", "sobreNome", "idade");

        BeanWrapperFieldSetMapper<Pessoa> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Pessoa.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
