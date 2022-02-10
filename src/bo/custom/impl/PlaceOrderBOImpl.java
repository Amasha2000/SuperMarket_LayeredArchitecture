package bo.custom.impl;

import bo.custom.PlaceOrderBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DatabaseConnection;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Order;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public boolean addOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection=null;
        try{
            connection= DatabaseConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
           /* Order orders=new Order(dto.getOrderId(),dto.getCustId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost());
            while(dto.getDetailList()==null) {
                for (OrderDetailDTO detail : dto.getDetailList()) {
                    System.out.println(detail.getItemCode() + '\n' + detail.getOrderId() + '\n' + detail.getOrderQty() + '\n' + detail.getPrice() + '\n' + detail.getDiscount());
                    OrderDetail orderDetail = new OrderDetail(detail.getItemCode(), detail.getOrderId(), detail.getOrderQty(), detail.getPrice(), detail.getDiscount());
                    if (orderDAO.add(orders) && orderDetailDAO.add(orderDetail)) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                }
            }*/
            Order orders=new Order(dto.getOrderId(),dto.getCustId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost());
            boolean orderAdded = orderDAO.add(orders);
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }else{
                for (OrderDetailDTO detail : dto.getDetailList()) {
                    OrderDetail orderDetail = new OrderDetail(detail.getItemCode(), detail.getOrderId(), detail.getOrderQty(), detail.getPrice(), detail.getDiscount());
                    boolean orderDetailsAdded = orderDetailDAO.add(orderDetail);
                    if (!orderDetailsAdded) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;

    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(dto.getOrderId(),dto.getCustId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost()));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
       return orderDAO.delete(id);
    }

    @Override
    public OrderDTO search(String id) throws SQLException, ClassNotFoundException {
        Order order=orderDAO.search(id);
        return new OrderDTO(order.getOrderId(),order.getCustId(),order.getOrderDate(),order.getTime(),order.getCost());

    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll();
        for (Order order : all) {
            allOrders.add(new OrderDTO(order.getOrderId(),order.getCustId(),order.getOrderDate(),order.getTime(),order.getCost()));
        }
        return allOrders;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }
}
