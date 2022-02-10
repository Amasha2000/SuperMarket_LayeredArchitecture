package dao.custom;


import dao.CrudDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    String getCustomerId() throws SQLException, ClassNotFoundException;
}
