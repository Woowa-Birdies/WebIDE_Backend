package goorm.woowa.webide.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyHandler extends TextWebSocketHandler {

//    // Map<key:value> -> key:세션ID, value:세션
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    //웹소켓 연결
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // session을 sessions에 추가
//        sessions.put(session.getId(), session);
//
//        // 연결 성공 시 메세지 전송
//        session.sendMessage(new TextMessage("소켓 연결 성공"));
//    }
//
//    //양방향 데이터 통신
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        String payload = message.getPayload();
//        System.out.println("수신한 메세지 : " + payload);
//
//    }
//
//    //소켓 연결 종료
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        //연결이 종료된 세션을 sessions에서 제거
//        System.out.println("소켓 연결 종료");
//        sessions.remove(session.getId());
//    }
//
//    //소켓 통신 에러
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        System.out.println("소켓 통신 에러");
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("받은메세지:"+payload);

        session.sendMessage(new TextMessage("received!!"));
    }
}
