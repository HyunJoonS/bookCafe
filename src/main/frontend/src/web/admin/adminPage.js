import styles_nav from 'css/admin/adminNav.module.scss';
import styles from 'css/admin/admin.module.scss';
import BookAddPage from './bookAddPage';
import BookListPage from './bookListPage';
import MenuAdd from './menuAdd';
import MenuListPage from './menuList';
import OrderList from './orderList';
import PaymentList from './paymentList';

import { useState } from 'react';
import { useSelector,useDispatch } from 'react-redux';

function AdminPage() {
    const number = useSelector((state)=> state.newOrder);
    let [menuNum,setMenuNum] = useState("주문현황");
    const dispatch = useDispatch();

    console.log(number);
    const tabMenu ={
        도서추가:<BookAddPage/>,
        도서목록:<BookListPage/>,
        메뉴추가:<MenuAdd/>,
        메뉴목록:<MenuListPage/>,
        주문현황:<OrderList/>,
        매출현황:<PaymentList/>
    }

    const menuChange=(menuName)=>{
        dispatch({type:'init'});
        setMenuNum(menuName);
    }

    return (
        <div className={styles.wrap}>
            <div className={styles_nav.left_navbar}>
                <div className={styles_nav.menu}>
                    <h6>자유롭게 테스트해보세요</h6>
                    <h5>
                        주문
                    </h5>
                    <ul className={styles_nav.ul}>
                        <li onClick={()=>{menuChange("주문현황")}} className={menuNum =='주문현황' ?styles_nav.selected:null}>
                            주문현황{number>0?<span className={styles.badg}>{number}</span>:null}
                        </li>
                        <li onClick={()=>{setMenuNum("매출현황")}} className={menuNum =='매출현황' ?styles_nav.selected:null}>
                            매출현황
                        </li>           
                        {/* <li onClick={()=>{dispatch({type:'plus'})}}>
                            버튼
                        </li>            */}
                    </ul>
                    <h5>
                        도서
                    </h5>
                    <ul className={styles_nav.ul}>
                        <li onClick={()=>{setMenuNum("도서목록")}} className={menuNum =='도서목록' ?styles_nav.selected:null}>
                            도서목록
                        </li>
                        <li onClick={()=>{setMenuNum("도서추가")}} className={menuNum =='도서추가' ?styles_nav.selected:null}>
                            도서추가
                        </li>
                    </ul>
                    <h5>
                        판매상품
                    </h5>
                    <ul className={styles_nav.ul}>
                        <li onClick={()=>{setMenuNum("메뉴목록")}} className={menuNum =='메뉴목록' ?styles_nav.selected:null}>
                            메뉴목록
                        </li>
                        <li onClick={()=>{setMenuNum("메뉴추가")}} className={menuNum =='메뉴추가' ?styles_nav.selected:null}>
                            메뉴추가
                        </li>
                    </ul>
                </div>
            </div>
            <div className={styles.main}>
                {tabMenu[menuNum]}
            </div>     
        </div>
    )
}
export default AdminPage;
