package com.bibek.customerETL.Repository;

import com.bibek.customerETL.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataLoaderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int[] insertBatch(final List<Customer> customers){
        String sql = "INSERT INTO CUSTOMERS " +
                "( email, first_name, last_name, ip, latitude, longitude, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        List<Object[]> parameters = new ArrayList<>();

        for (Customer cust : customers) {
            parameters.add(new Object[] {cust.getEmail(),
                    cust.getFirst_name(), cust.getLast_name(),cust.getIp(), Float.valueOf(cust.getLatitude()), Float.valueOf(cust.getLongitude()), cust.getCreated_at()}
            );
        }
        return jdbcTemplate.batchUpdate(sql, parameters);
    }
}
