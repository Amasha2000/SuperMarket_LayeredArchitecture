package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",customer.getCustId(),customer.getCustTitle(),customer.getCustName(),customer.getCustAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode());
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=? WHERE custId=?", customer.getCustTitle(), customer.getCustName(), customer.getCustAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode(), customer.getCustId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE custId=?",id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE custId=?", id);
        rst.next();
        return new Customer(id, rst.getString("custTitle"), rst.getString("custName"),rst.getString("custAddress"),rst.getString("city"),rst.getString("province"),rst.getString("postalCode"));
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers=new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString("custId"),rst.getString("custTitle"), rst.getString("custName"),rst.getString("custAddress"),rst.getString("city"),rst.getString("province"),rst.getString("postalCode")));
        }
        return allCustomers;
    }

    @Override
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery("SELECT custId FROM Customer ORDER BY custId DESC LIMIT 1");
        if(rst.next()){
            int index=Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if(index<10){
                return "C-000"+ index;
            }else if(index<100){
                return "C-00"+ index;
            }else if(index<1000){
                return "C-0"+ index;
            }else{
                return "C-"+ index;
            }
        }else{
            return "C-0001";
        }
    }

}
