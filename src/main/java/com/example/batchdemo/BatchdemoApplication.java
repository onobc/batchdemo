package com.example.batchdemo;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchdemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(BatchdemoApplication.class, args);
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobExecution jobExec = new JobExecution(5150L);
		StepExecution step1 = new StepExecution("step1", jobExec, 100L);
		StepExecution step2 = new StepExecution("step2", jobExec, 200L);
		jobExec.addStepExecutions(Arrays.asList(step1, step2));

		String json = objectMapper.writeValueAsString(jobExec);
		System.out.printf("*** SER " + json);

		JobExecution deserJobExec = objectMapper.readValue(json, JobExecution.class);
		System.out.println("*** DESER " + deserJobExec + " w/ stepExecutions= " + deserJobExec.getStepExecutions());
	}
}
