package ticket.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ticket.task.OrderCleanupTask;


@WebListener
public class AppStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 // 獲取 Web 應用的上下文
        ServletContext context = sce.getServletContext();
        // 設置一個屬性，表示應用已經啟動
        context.setAttribute("appStatus", "started");

        
        // 啟動定時任務
        OrderCleanupTask.startTask();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 停止定時任務
        OrderCleanupTask.stopTask();
	}
}
