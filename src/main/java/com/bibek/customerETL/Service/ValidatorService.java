package com.bibek.customerETL.Service;

import com.bibek.customerETL.model.Customer;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ValidatorService {

    private int createdAtIndex;
    private int firstNameIndex;
    private int lastNameIndex;
    private int emailIndex;
    private int latitudeIndex;
    private int longitutudeIndex;
    private int ipIndex;

    private List<String> header;

    List<Customer> customers = new ArrayList<>();

    private static Logger LOG = LoggerFactory
            .getLogger(ValidatorService.class);

    public boolean validateMapper(File mapperFile) {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mapperFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }

            if(!validateHeader(records.get(0))){
                LOG.error("Validation Failed: Mapping file mismatch");
                    return false;
            }

            header = records.get(1);

        } catch (FileNotFoundException e) {
            LOG.error("Mapping file doesn't exists:" + mapperFile.toString());
            return false;
        } catch (IOException e) {
            LOG.error("Validation Failed: ", e);
            return false;
        }

        return true;
    }

    public boolean validateDataFile(File dataFile){

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }

            if(!records.get(0).equals(header)){
                LOG.error("Header did not match");
                return false;
            }

            records.remove(header);

            for( List<String> data: records){
                Customer customer = new Customer();
                customer.setFirst_name(data.get(firstNameIndex));
                customer.setLast_name(data.get(lastNameIndex));
                customer.setEmail(data.get(emailIndex));
                customer.setIp(data.get(ipIndex));
                customer.setLatitude(data.get(latitudeIndex));
                customer.setLongitude(data.get(longitutudeIndex));
                customer.setCreated_at(DateUtils.parseDate(data.get(createdAtIndex), new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"}));
                if(customer.isValid()) customers.add(customer);
            }

        } catch (FileNotFoundException e) {
            LOG.error("Mapping file doesn't exists:" + dataFile.toString());
            return false;
        } catch (IOException e) {
            LOG.error("Validation Failed: ", e);
            return false;
        } catch (ParseException e) {
            LOG.error("Validation Failed: ", e);
            return false;
        }

        return true;
    }

    private boolean validateHeader(List<String> values){
        createdAtIndex = values.indexOf("created_at");
        firstNameIndex = values.indexOf("first_name");
        lastNameIndex = values.indexOf("last_name");
        emailIndex = values.indexOf("email");
        latitudeIndex = values.indexOf("latitude");
        longitutudeIndex = values.indexOf("longitude");
        ipIndex = values.indexOf("ip");

        if(createdAtIndex == -1 || firstNameIndex == -1 || lastNameIndex ==-1 || emailIndex == -1 || latitudeIndex == -1 || longitutudeIndex == -1 || ipIndex == -1){
            return false;
        }

        return true;
    }

    public List<Customer> getCustomers(){
        return customers;
    }
}
