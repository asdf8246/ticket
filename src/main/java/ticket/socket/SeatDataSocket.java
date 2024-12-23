package ticket.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import ticket.model.dto.SeatCategoriesDto;
import ticket.service.SeatCategoriesService;

@ServerEndpoint("/seatDataSocket")
public class SeatDataSocket {
	
    // 用於儲存所有的 WebSocket 會話
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    // 當 WebSocket 連線建立時觸發
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session); // 將新連線加入會話集合
        System.out.println("WebSocket connected: " + session.getId());
    }

    // 當收到前端訊息時觸發
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from client: " + message);
    }

    // 當 WebSocket 連線關閉時觸發
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session); // 將關閉的會話從集合中移除
        System.out.println("WebSocket disconnected: " + session.getId());
    }

    // 當 WebSocket 出現錯誤時觸發
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    
    
    public static void sendUpdatedData(String eventId) {
    	SeatCategoriesService seatCategoriesService = new SeatCategoriesService();
    	List<SeatCategoriesDto> seatCategoriesDto = seatCategoriesService.getSeatCategoriesChart(eventId);
    	
    	// 構建 JSON 資料
        JsonArrayBuilder categoriesBuilder = Json.createArrayBuilder();
        JsonArrayBuilder numSeatsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder soldSeatsBuilder = Json.createArrayBuilder();

        // 遍歷 SeatCategoriesDto 並構建 JSON 資料
        for (SeatCategoriesDto seatCategory : seatCategoriesDto) {
            String categoryName = seatCategory.getCategoryName();
            int numSeats = seatCategory.getNumSeats();
            int soldSeats = seatCategory.getSoldSeats();  // 直接使用已銷售的座位數

            // 將資料添加到 JSON 物件中
            categoriesBuilder.add(categoryName);
            numSeatsBuilder.add(numSeats);
            soldSeatsBuilder.add(soldSeats);
        }

        // 組合 JSON 資料
        JsonObject data = Json.createObjectBuilder()
                .add("categories", categoriesBuilder.build())
                .add("numSeats", numSeatsBuilder.build())
                .add("soldSeats", soldSeatsBuilder.build())
                .build();

        // 向所有連線的客戶端發送資料
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(data.toString());  // 發送 JSON 資料給前端
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}