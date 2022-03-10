package com.example.batchdemo;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

	@Bean
	Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
		return (jackson) -> jackson
				.indentOutput(true)
				.failOnUnknownProperties(false)
				.mixIn(StepExecution.class, StepExecutionMixin.class)
				.mixIn(JobExecution.class, JobExecutionMixin.class)
				.mixIn(ExitStatus.class, ExitStatusMixin.class);
	}

	@JsonIgnoreProperties(value = "allFailureExceptions")
	public abstract static class JobExecutionMixin {

		@JsonManagedReference
		private Collection<StepExecution> stepExecutions;

		@JsonCreator
		JobExecutionMixin(
				@JsonProperty("job") JobInstance job,
				@JsonProperty("jobParameters") JobParameters jobParameters,
				@JsonProperty("jobConfigurationName") String jobConfigurationName) {
		}
	}

	public abstract static class StepExecutionMixin {

		@JsonBackReference
		private JobExecution jobExecution;

		@JsonCreator
		StepExecutionMixin(
				@JsonProperty("stepName") String stepName,
				@JsonProperty("jobExecution") JobExecution jobExecution) {
		}
	}

	public abstract static class ExitStatusMixin {

		@JsonCreator
		ExitStatusMixin(
				@JsonProperty("exitCode") String exitCode,
				@JsonProperty("exitDescription") String exitDescription) {
		}
	}

}
