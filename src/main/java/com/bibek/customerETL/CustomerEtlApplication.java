package com.bibek.customerETL;

import com.bibek.customerETL.Service.DataLoaderService;
import com.bibek.customerETL.Service.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class CustomerEtlApplication implements CommandLineRunner {

	@Autowired
	DataLoaderService dataLoaderService;

	private static Logger LOG = LoggerFactory
			.getLogger(CustomerEtlApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(CustomerEtlApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING : command line runner");

		if(args.length != 2){
			LOG.error("Please run the application with 2 arguments, [DataFile, MappingFile]");
			System.exit(0);
		}

		File dataFile = new File(args[0]);
		File mappingFile = new File(args[1]);

		if(dataLoaderService.process(dataFile,mappingFile)){
			LOG.info("Job Succeeded");
		}else {
			LOG.error("Job Failed");
		}

	}

}
