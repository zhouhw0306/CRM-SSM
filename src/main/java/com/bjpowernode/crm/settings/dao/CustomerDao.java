package com.bjpowernode.crm.settings.dao;


import com.bjpowernode.crm.settings.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);
}
