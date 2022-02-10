package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?,?)", item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscountPercent());
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, qtyOnHand=?, unitPrice=?, discountPercent=? WHERE itemCode=?", item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscountPercent(), item.getItemCode());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?", id);
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", id);
        rst.next();
        return new Item(id, rst.getString("description"), rst.getString("packSize"), rst.getInt("qtyOnHand"), rst.getDouble("unitPrice"), rst.getDouble("discountPercent"));
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("itemCode"), rst.getString("description"), rst.getString("packSize"), rst.getInt("qtyOnHand"), rst.getDouble("unitPrice"), rst.getDouble("discountPercent")));
        }
        return allItems;
    }

    @Override
    public String getItemCode() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if (rst.next()) {
            int index = Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if (index < 10) {
                return "I-000" + index;
            } else if (index < 100) {
                return "I-00" + index;
            } else if (index < 1000) {
                return "I-0" + index;
            } else {
                return "I-" + index;
            }
        } else {
            return "I-0001";
        }
    }
}

