package jpa.bookCafe.repository;

import jpa.bookCafe.dto.ChatUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {ChatSessionRepository.class})
class ChatSessionRepositoryTest {

    @MockBean
    RandomColorRepository randomColorRepository;
    @Autowired
    ChatSessionRepository chatSessionRepository;

    @DisplayName("유저 채팅방 입장")
    @Test
    public void save() throws Exception{
        //given
        Mockito.when(randomColorRepository.rentalColor()).thenReturn("#1f2802");

        //when
        chatSessionRepository.save("uuid-111-222-33","user1");

        //then
        Assertions.assertThat(chatSessionRepository.size()).isEqualTo(1);
    }

    @DisplayName("유저 채팅방 퇴장")
    @Test
    public void delete() throws Exception{
        //given
        Mockito.when(randomColorRepository.rentalColor()).thenReturn("#1f2802");


        String sessionId = "uuid-111-222-33";
        //when
        chatSessionRepository.save(sessionId,"user1");
        chatSessionRepository.delete(sessionId);

        //then
        Assertions.assertThat(chatSessionRepository.size()).isEqualTo(0);
    }

    @DisplayName("유저 정보")
    @Test
    public void findById() throws Exception{
        //given
        Mockito.when(randomColorRepository.rentalColor()).thenReturn("#1f2802");
        String sessionId = "uuid-111-222-33";

        //when
        chatSessionRepository.save(sessionId,"user1");
        ChatUser user = chatSessionRepository.findById(sessionId);

        //then
        Assertions.assertThat(user.getNickName()).isEqualTo("user1");
        Assertions.assertThat(user.getColor()).isEqualTo("#1f2802");
    }
}