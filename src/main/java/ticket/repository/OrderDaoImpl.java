package ticket.repository;

import java.util.List;

import ticket.model.entity.Order;

public class OrderDaoImpl extends BaseDao implements OrderDao {

	@Override
	public List<Order> findAllOrders(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrderSeats(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Integer addOrder(Integer userId, String eventName, Integer orderPrice, String orderDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrderSeats(List<Order> orders) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateOrderStatus(Integer orderId, String orderStatus) {
		// TODO Auto-generated method stub
		
	}

	
	
}
