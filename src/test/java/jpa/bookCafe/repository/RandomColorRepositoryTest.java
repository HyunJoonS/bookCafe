package jpa.bookCafe.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {RandomColorRepository.class})
class RandomColorRepositoryTest {

    @Autowired
    RandomColorRepository randomColorRepository;

    @DisplayName("컬러를 잘 꺼내는가?")
    @Test
    void rentalColor() {
        Set<String> s = new HashSet<>();
        for(int i=0; i<10; i++){
            s.add(randomColorRepository.rentalColor());
        }
        Assertions.assertThat(s.size()).isEqualTo(10);
    }

    @DisplayName("컬러반납에 문제가 없는가?")
    @Test
    void returnColor() {
        for(int i=0; i<10; i++){
            String s = randomColorRepository.rentalColor();
            randomColorRepository.returnColor(s);
        }
    }
}