import styles from 'css/nav.module.scss';
import { useNavigate } from 'react-router-dom';
import { FaRegCommentDots } from "react-icons/fa";
import Chat from './Chat';
import { useState } from 'react';
function MyNavbar() {
  let nevigate = useNavigate();
  let [visibleChat,setVisibleChat] = useState(false);
  const openChat = ()=>{
    setVisibleChat(!visibleChat);
  }
  return (
    <div className={styles.gnb}>
      <div className={styles.wrap}>
        <a className={styles.logo} onClick={() => { nevigate('/') }}>BOOKCAFE</a>
        <a className={styles.link} onClick={() => { nevigate('/') }}>UserHome</a>
        <a className={styles.link} onClick={() => { nevigate('/adminpage') }}>AdminPage</a>
        <div className={styles.chat}>
          <a className={styles.chatBtn} onClick={openChat}>
            <FaRegCommentDots />
          </a>
          <div className={styles.chatViewWrap} style={!visibleChat?{visibility:'hidden'}:null} >
            <Chat closeChat={openChat}></Chat> 
          </div>
        </div>
      </div>
    </div>
  );
}



export default MyNavbar;