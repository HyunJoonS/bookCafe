import styles from 'css/chat.module.scss'
import { useState } from 'react';
import {useDrag} from 'react-use-gesture';
import { FaTimes } from "react-icons/fa";
import { useRef } from 'react';
import { useEffect } from 'react';

const ChatView = ({nickName,closeChat,msg,publish})=>{
    let inputRef=useRef();
    let chatwindowRef=useRef();
    const [userNum, setUserNum] = useState(0);

    useEffect(()=>{
        if(msg.length>0){
            let arr = msg[msg.length-1];
            if(arr.messageType =='ENTER' || arr.messageType =='QUIT'){
                setUserNum(arr.currentUserNum);
            }
        }
    },[msg])

    const enterKey=(e)=>{
        if(e.key=='Enter'){
            sendEvent();
        }
    }

    const sendEvent=()=>{
        publish(inputRef.current.value);
        inputRef.current.value='';
    }

    const [position, setPosition] = useState({x:45, y:55})
    const bindLogoPos = useDrag((params)=>{
        console.log(params.offset);
        setPosition({
            x: 45-params.offset[0],
            y: 55+params.offset[1],
          })
    });
    return(
       <div ref={chatwindowRef} className={styles.wrap} 
       style={{
        position: 'fixed',
        top: position.y,
        right: position.x
      }}>
            <div {...bindLogoPos()} className={styles.titlebar}>
                <span style={{color:'gray'}}>실시간{userNum}명 접속중</span>
                <span className={styles.xbtn} onClick={closeChat}><FaTimes></FaTimes></span>
            </div>
            <div className={styles.body}>
                <Sysmsg msg={"[안내] 채팅방 메세지는 서버에 저장되지 않습니다."}></Sysmsg>

                {msg.map((ele,i)=>{
                    return(
                        <> 
                            <Content msg={ele} key={i}/>
                        </>
                        
                    )
                })}
                
           
            </div>
            <div className={styles.inputbox}>
                <input onKeyUp={enterKey} ref={inputRef} placeholder={'['+nickName+'] 채팅을 입력하세요'} ></input>
                <button className={styles.btn_gradient} onClick={sendEvent}>보내기</button>
            </div>
       </div>
    )
}
const Content=({msg})=>{
    if(msg.messageType == 'ENTER' || msg.messageType == 'QUIT'){
        return(
            <div>
                <span className={styles.notice}>{msg.message}</span>
            </div>
        )
    }else{
        return(
            <div>
                <span className={styles.writer} style={{color:msg.color}}>{msg.writer}</span>
                <span className={styles.text}>{msg.message}</span>
            </div>
        )
    }
}

const Sysmsg=({msg})=>{
    return(
        <div>
            <span className={styles.notice}>{msg}</span>
        </div>
    )
   
}
export default ChatView;