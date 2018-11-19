package com.playbook.batch;


import com.playbook.batch.listener.CollectionItemReaderListener;
import com.playbook.batch.listener.CollectionItemStepListener;
import com.playbook.batch.listener.CollectionItemWriterListener;
import com.playbook.entity.Coleccion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class CollectionJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<Coleccion> reader(@Value("#{jobParameters['input.file.name']}")String name) {
        FileSystemResource archivo = new FileSystemResource(name);
        return new FlatFileItemReaderBuilder<Coleccion>()
                .name("collectionItemReader")
                .resource(archivo)
                .delimited()
                .names(new String[]{"coleccionIdentity.userId", "coleccionIdentity.boardgameId"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Coleccion>() {{
                    setTargetType(Coleccion.class);
                }})
                .build();
    }

    @Bean
    public CollectionItemProcessor processor() {
        return new CollectionItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Coleccion> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Coleccion>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO collections (user_id, boardgame_id) VALUES " +
                        "(:coleccionIdentity.userId, :coleccionIdentity.boardgameId) ON DUPLICATE KEY UPDATE " +
                        "user_id = :coleccionIdentity.userId, boardgame_id = :coleccionIdentity.boardgameId")
                .dataSource(dataSource)
                .build();
    }

    @Bean(name="loadCSV")
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Coleccion> writer, FlatFileItemReader<Coleccion> reader,
                      CollectionItemReaderListener collectionItemReaderListener,
                      CollectionItemStepListener collectionItemStepListener,
                      CollectionItemWriterListener writerListener) {
        return stepBuilderFactory.get("step1")
                .<Coleccion, Coleccion> chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .listener(collectionItemReaderListener)
                .listener(collectionItemStepListener)
                .listener(writerListener)
                .build();
    }
}
