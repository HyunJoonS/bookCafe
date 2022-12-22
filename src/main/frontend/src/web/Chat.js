import axios from "axios";
import { useEffect, useRef, useState } from "react";

import { randomNick } from 'web/randomNick'
import * as SockJS from 'sockjs-client';
import * as StompJs from '@stomp/stompjs';
import ChatView from "./ChatView";
import { useLocation } from "react-router-dom";
import { useDispatch } from "react-redux";
const ROOM_SEQ = 1;
const Chat = ({ closeChat }) => {
  
  const dispatch = useDispatch();

  const location = useLocation();
  const client = useRef();
  const [chatMessages, setChatMessages] = useState([]);
  const [message, setMessage] = useState("");
  const [nickName, setNickName] = useState("");
  const [chat_subscribe,setChat_subscribe] = useState();
  const [order_subscribe,setOrder_subscribe] = useState();

  useEffect(() => {
    let name = randomNick();
    setNickName(name);
    return () => disconnect();
  }, [])

  useEffect(() => {
    if (nickName) { //이름받으면 소켓맺을거
      connect();
    }
  }, [nickName])
 

  useEffect(() => { 
    if (location.pathname == '/adminpage' && client.current != null) { //어드민페이지에서만 따로 주문알람 받을거
      setOrder_subscribe(orderSubscribe());
    }
    else{
      if(order_subscribe != null){
        order_subscribe.unsubscribe();
        setOrder_subscribe(null);
      }
    }

    console.log(location);

  }, [location.pathname])

  const connect = () => {
    client.current = new StompJs.Client({
      // brokerURL: "ws://localhost:8080/ws/websocket", // 웹소켓 서버로 직접 접속
      webSocketFactory: () => new SockJS("/ws"), // proxy를 통한 접속
      connectHeaders: {
        // "auth-token": "spring-chat-auth-token",
        // login: 'user',
        // password: 'password'
      },
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => { // 연결 성공 시 구독하는 로직 실행
        setChat_subscribe(subscribe());
        if (location.pathname == '/adminpage' && client.current != null) { //어드민페이지에서만 따로 주문알람 받을거
          setOrder_subscribe(orderSubscribe());
        }
        enter();
      },
      onStompError: (frame) => {
        console.error(frame);
      },
    });

    client.current.activate(); // 클라이언트 활성화
  };

  const disconnect = () => { //연결이 끊겼을 때
    client.current.deactivate();
  };

  const subscribe = ()=>{
    return client.current.subscribe("/topic/" + ROOM_SEQ, (body) => {
    console.log("test", body);
    setChatMessages((_chatMessages) => [..._chatMessages, JSON.parse(body.body)]);
  })};

  const orderSubscribe = ()=>{
    return client.current.subscribe("/topic/order", ({body}) => {
      dispatch({type:'plus'});
      console.log(body);
  })};

  const enter = () => {
    if (!client.current.connected) {
      return;
    }
    client.current.publish({
      destination: "/app/enter", body: JSON.stringify({ chatRoom: ROOM_SEQ, writer: nickName, message }),
    });
  };

  const publish = (message) => {
    if (!client.current.connected) {
      return;
    }

    client.current.publish({
      destination: "/app/chat", body: JSON.stringify({ chatRoom: ROOM_SEQ, writer: nickName, message }),
    });
  };
  return (
  <>
    <ChatView nickName={nickName} closeChat={closeChat} msg={chatMessages} publish={publish} />
    </>
  )
}
export default Chat;