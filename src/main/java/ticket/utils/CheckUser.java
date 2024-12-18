package ticket.utils;

import ticket.model.dto.OrderDto;
import ticket.model.dto.UserDto;
import ticket.service.OrderService;
import ticket.service.UserService;

public class CheckUser {
	
	
	public boolean checkOrderUser(String orderId, Integer userId) {
		OrderService orderService = new OrderService();
		OrderDto orderDto = orderService.getOrder(orderId);
		Integer orderUserId = orderDto.getUserId();
		if (orderUserId == userId) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkUserId(String userId, Integer certUserId) {
		if (Integer.parseInt(userId) == certUserId) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkUserRole(Integer userId , String userRole) {
		if (userRole.equals("ROLE_ADMIN")) {
			return true;
		} else {
			return false;
		}
	}
	
}
