import styles from 'css/admin/modal.module.scss';
import { useEffect } from 'react';

function Modal({children , closeModal}){
    useEffect(()=>{
        document.body.style.cssText = `
        position: fixed; 
        top: -${window.scrollY}px;
        overflow-y: scroll;
        width: 100%;`
            ;
            return()=>{
                const scrollY = document.body.style.top;
                document.body.style.cssText = '';
                window.scrollTo(0, parseInt(scrollY || '0', 10) * -1);
            }
    },[])
    return(
        <div className={styles.wrap}>

            <div className={styles.modalBox}>
                <div className={styles.modalWrap}>
                    {children}  

                </div>
                <button onClick={closeModal} className={styles.Xbtn2}>닫기</button>

            </div>
        </div>
    )
}

export default Modal;