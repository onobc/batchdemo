package com.example.batchdemo;

import java.util.Arrays;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class BatchdemoApplicationTests {

	@Test
	void simpleSerialization(@Autowired ObjectMapper objectMapper) throws Exception {

		JobExecution jobExec = new JobExecution(5150L);
		StepExecution step1 = new StepExecution("step1", jobExec, 100L);
		StepExecution step2 = new StepExecution("step2", jobExec, 200L);
		jobExec.addStepExecutions(Arrays.asList(step1, step2));

		String json = objectMapper.writeValueAsString(jobExec);
		System.out.printf("*** SER " + json);

		JobExecution deserJobExec = objectMapper.readValue(json, JobExecution.class);
		System.out.println("*** DESER " + deserJobExec);

		// TODO add assertions
	}




}
