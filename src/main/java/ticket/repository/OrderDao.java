package ticket.repository;

import java.util.List;

import ticket.model.entity.Order;

public interface OrderDao {
	
	List<Order> findAllOrders(Integer userId);
	
	Order getOrderSeats(Integer orderId);
}
