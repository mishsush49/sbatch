package com.sushavi.sbatch.config;

import com.sushavi.sbatch.chunk.MyItemProcessor;
import com.sushavi.sbatch.chunk.MyItemReader;
import com.sushavi.sbatch.chunk.MyItemWriter;
import com.sushavi.sbatch.listener.FirstJobListener;
import com.sushavi.sbatch.service.SecondTaskService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class SampleJob   {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTaskService secondTaskService;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private MyItemReader myItemReader;
    @Autowired
    private MyItemProcessor myItemProcessor;
    @Autowired
    private MyItemWriter myItemWriter;

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .listener(firstJobListener)
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("Chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("Chunk Step")
                .<Integer,Long>chunk(3)
                .reader(myItemReader)
                .processor(myItemProcessor)
                .writer(myItemWriter)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .build();

    }

    private Step secondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTaskService)
                .build();

    }

    private Tasklet firstTask(){
        return (contribution, chunkContext) -> {
            System.out.println("This is first Tasklet step");
            return RepeatStatus.FINISHED;
        };
    }
}
