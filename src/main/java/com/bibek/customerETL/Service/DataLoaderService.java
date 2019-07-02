package com.bibek.customerETL.Service;

import com.bibek.customerETL.Repository.DataLoaderRepository;
import com.bibek.customerETL.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;


@Service
public class DataLoaderService {
    private static Logger LOG = LoggerFactory
            .getLogger(DataLoaderService.class);

    @Autowired
    ValidatorService validatorService;

    @Autowired
    DataLoaderRepository dataLoaderRepository;

    List<Customer> customers;

    public boolean process(File dataFile, File mappingFile){

        boolean isMappingFileValid = validatorService.validateMapper(mappingFile);

        if(isMappingFileValid){
            boolean isDataFileValid = validatorService.validateDataFile(dataFile);
            if(isDataFileValid){
                customers = validatorService.getCustomers();
                int[] inserted = dataLoaderRepository.insertBatch(customers);
                LOG.info("Successfully Uploaded records: "+ inserted.length);
                return true;
            }
        }

        return false;
    }

}
