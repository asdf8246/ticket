package ticket.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ticket.exception.CertException;
import ticket.model.dto.UserCert;
import ticket.service.CertService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private CertService certService = new CertService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
		if (userCert != null) {
			resp.sendRedirect("/ticket/index.html");
			return;
		}
		// 重導到 login.jsp
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String phonenumber = req.getParameter("phonenumber");
		String password = req.getParameter("password");
		
		//驗證帳密取得憑證
		UserCert userCert = null;
		
		try {
			userCert = certService.gerCert(phonenumber, password);
		} catch (CertException e) {
			// 將錯誤丟給(重導) error.jsp
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
			return;
		}
		
		//將憑證放入 session 變數中以利其他城市進行取用與驗證
		HttpSession session = req.getSession();
		session.setAttribute("userCert", userCert);// 放憑證
		req.setAttribute("locale", req.getLocale());// 取得客戶端所在地 例如: zh_TW
		
		req.setAttribute("message", "登入成功");
		// 檢查 session 中的 redirectURL 是否有資料
		if (session.getAttribute("redirectURL")==null) {
			resp.sendRedirect("/ticket/index.html");
		} else {
			String redirectURL = session.getAttribute("redirectURL").toString();
			resp.sendRedirect(redirectURL);
			session.setAttribute("redirectURL", null); // 清空 "redirectURL"
		}
		
	}
	
}
