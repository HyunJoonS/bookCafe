package jpa.bookCafe.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RandomColorRepository {
    private static List<String> randomColor = new ArrayList<>();

    public RandomColorRepository() {
        randomColor.add("#eeb7b4");
        randomColor.add("#AFC4E7");
        randomColor.add("#BAE7AF");
        randomColor.add("#FF7F7F");
        randomColor.add("#FDC4F8");
        randomColor.add("#CB9FFD");
        randomColor.add("#f2cfa5");
        randomColor.add("#b5c7ed");
        randomColor.add("#caa6fe");
        randomColor.add("#83a7a3");
        randomColor.add("#acb780");
        randomColor.add("#1a1a19");
        randomColor.add("#66663e");
        randomColor.add("#3621a9");
        randomColor.add("#6051b3");
        randomColor.add("#a39bd1");
        randomColor.add("#6b687c");
        randomColor.add("#d93737");
        randomColor.add("#9b4141");
        randomColor.add("#c79090");
        randomColor.add("#c7b690");
        randomColor.add("#9bbb38");
        randomColor.add("#1f2802");
        randomColor.add("#af3895");
        randomColor.add("#6c6c6c");
        randomColor.add("#5e333b");
    }
    public String lendColor(){
        Collections.shuffle(randomColor);
        Optional<String> remove = Optional.ofNullable(randomColor.remove(0));
        log.info("have list left={}",randomColor.size());
        return remove.orElse("gray");
    }
    public void returnColor(String color){
        randomColor.add(color);
        log.info("have list left={}",randomColor.size());
    }
}
