

import { useNavigate } from 'react-router-dom';
import { Button, Container, Form, InputGroup } from 'react-bootstrap';
import axios from 'axios';
import { useRef } from 'react';
import styles from 'css/login.module.scss';
import { useState } from 'react';
import { useEffect } from 'react';
function Home() {
    let IDref = useRef();
    let PWref = useRef();
    let errRef = useRef();
    let navigate = useNavigate();
    let url = "/api/login";

    let [errMsg,setErrMsg] = useState("");


    let login =()=>{
        axios.post(url,JSON.stringify({
            loginId : IDref.current.value,
            loginPw : PWref.current.value
        }), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
            .then((result) => {
                navigate("/adminpage");
            })
            .catch(({response}) => {
                setErrMsg(response.data.message);
                errRef.current.className=styles.vibration;
                setTimeout(()=>{
                    errRef.current.className="";
                  }, 400);
            });
        } 
    return (
            <div className={[styles.wrap, styles.vibration].join(" ")}>
                <h4>관리자 로그인</h4>
                <div className={styles.inputbox}>
                    <label className={styles.vibrations}>아이디</label>
                    <input type="text" ref={IDref}></input>
                </div>
                <div className={styles.inputbox}>
                    <label>비밀번호</label>
                    <input type="password" ref={PWref}></input>                    
                </div>
                <span ref={errRef}>{errMsg?errMsg:" "}</span>
                <button className={[styles.btn].join(" ")} onClick={login}>로그인</button>
            </div>
    )
}
export default Home;