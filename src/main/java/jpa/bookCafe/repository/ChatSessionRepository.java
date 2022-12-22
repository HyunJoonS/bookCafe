package jpa.bookCafe.repository;

import jpa.bookCafe.dto.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatSessionRepository {
    private final RandomColorRepository randomColor;


    private static Map<String, ChatUser> user =  new HashMap<>();
    private static int num = 0;
    public void save(String sessionId,String writer){
        String color = randomColor.lendColor();
        user.put(sessionId, new ChatUser(writer,color));
    }
    public void delete(String sessionId){
        ChatUser chatUser = user.get(sessionId);
        randomColor.returnColor(chatUser.getColor());
        user.remove(sessionId);
    }
    public int size(){
        return user.size();
    }

    public ChatUser findName(String sessionId){
        return user.get(sessionId);
    }
}
