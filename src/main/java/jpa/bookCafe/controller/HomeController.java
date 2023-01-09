package jpa.bookCafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class HomeController{

    //리액트 페이지에 맵핑
    @GetMapping("/")
    public String home(){
        return "../static/index";
    }


    /*
        리액트는 싱글페이지 임 오직 '/' 경로 에서만 작동함.
        url을 이용하여 react의 다른 페이지를 탐색 하려 할 때 404 에러가 뜨게 되는데
        리액트 페이지를 보여주게끔 설정해야함

        추가적으로 WebConfig 설정으로 404 에러가 뜨면 '/error-page/404' 경로로 올수 있게 설정해줄것.
     */
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        return "../static/index";
    }

}
