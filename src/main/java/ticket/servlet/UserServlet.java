package ticket.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ticket.model.dto.UserCert;
import ticket.model.dto.UserDto;
import ticket.service.UserService;


/**
 request  +-------------+         +-------------+        +---------+
--------> | UserServlet | ------> | UserServise | -----> | UserDao | -----> MySQL(web.users)
          | (Controller)| <-----  |             | <----- |         | <-----
          +-------------+ UserDto +-------------+  User  +---------+
          		 |   	   (Dto)				 (Entity)
          		 |
          		 v
          +-------------+
<-------- |   user.jsp  |
 response |    (View)   |
          +-------------+
          
 查詢所有: GET /user
 查詢單筆: GET /user/get?username=admin
 新增單筆: POST /user/add
 修改單筆: POST /user/update?userId=1
 刪除單筆: GET /user/delete?userId=1 
 修改密碼: GET /user/update/password (得到修改密碼頁面)
 修改密碼: POST /user/update/password (修改密碼處理程序)
          
 */

@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {

	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		//resp.getWriter().print(pathInfo);
		if (pathInfo == null || pathInfo.equals("/*")) {
			//查詢全部
			List<UserDto> userDtos = userService.findAll();
			//resp.getWriter().print(userDtos);
			// 將必要資料加入到 requset 屬性中以便交由 jsp 進行分析與呈現
			req.setAttribute("userDtos", userDtos);
			// 重導到 user.jsp
			req.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(req, resp);
			return;
		} else if (pathInfo.equals("/delete")) {
			String userId = req.getParameter("userId");
			userService.deleteUser(userId);
			// 刪除完畢後，重新執行首頁
			resp.sendRedirect("/javaweb/user");
			return;
		} else if (pathInfo.equals("/get")) { //  取得 user 資料並導入到修改頁面
			String userId =req.getParameter("userId");
			UserDto userDto = userService.getUser(userId);
			// 將必要資料加入到 requset 屬性中以便交由 jsp 進行分析與呈現
			req.setAttribute("userDto", userDto);
			// 重導到 user_update.jsp
			req.getRequestDispatcher("/WEB-INF/view/user_update.jsp").forward(req, resp);
			return;
		} else if (pathInfo.equals("/update/password")) { //修改密碼頁面
			req.getRequestDispatcher("/WEB-INF/view/update_password.jsp").forward(req, resp);
			return;
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String role = req.getParameter("role");
		String phonenumber = req.getParameter("phonenumber");
		String userId = req.getParameter("userId");
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		
		switch (pathInfo) {
		case "/add": 
			userService.appendUser(username, phonenumber, password, email, role);
			break;
		case "/update":
			userService.updateUser(userId, username, phonenumber, email, role);
			break;
		case "/update/password":
			// 修改密碼要在已登入的環境下
			HttpSession session = req.getSession();
			UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
			try {
				userService.updatePassword(userCert.getUserId(), userCert.getUserPhonenumber(), oldPassword, newPassword);
				req.setAttribute("message", "密碼更新成功");
				req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
			} catch (Exception e) {
				req.setAttribute("message", e.getMessage());
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
			}
			return;
		}
		
		// 外重導到指定 URL 網頁
		resp.sendRedirect("/ticket/user");
	}
	
	
}
