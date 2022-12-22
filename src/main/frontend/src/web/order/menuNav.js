import styles from 'css/order/menuNav.module.css';

function menuNav({changeSelectMenu}){
    return(
        <div className={styles.wrap}>
            <ul>
                <li onClick={()=>{changeSelectMenu("ALL")}}>
                    전체
                </li>
                <li onClick={()=>{changeSelectMenu("DRINK")}}>
                    음료
                </li>
                <li onClick={()=>{changeSelectMenu("FOOD")}}>
                    음식
                </li>
                <li onClick={()=>{changeSelectMenu("BAKERY")}}>
                    빵
                </li>
            </ul>
        </div>
    )
}
export default menuNav;
