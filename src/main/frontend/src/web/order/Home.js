import { useNavigate } from "react-router-dom";
import styles from "css/home.module.scss";
import Footer from 'web/footer';
function Home() {
    let navigate = useNavigate();

    return (
        <>
            <div className={styles.wrap}>

                <div className={styles.btns} onClick={() => {
                    navigate("/book")
                }}>
                    <div className={styles.centerText}>
                        <h4>도서 검색</h4>
                    </div>

                </div>

                <div className={styles.btns} onClick={() => {
                    navigate("/menu")
                }}>
                    <div className={styles.centerText}>
                        <h4>메뉴 주문</h4>
                    </div>

                </div>
            </div>
            <Footer></Footer>
        </>
    )
}
export default Home;