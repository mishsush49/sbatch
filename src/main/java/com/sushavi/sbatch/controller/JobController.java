package com.sushavi.sbatch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));
        jobLauncher.run("First Job".equals(jobName)?firstJob:secondJob, new JobParameters(params));
        return "Job Started";
    }
}
