import axios from 'axios';
import styles from 'css/admin/orderlist.module.scss'
import { useEffect } from 'react';
import { useState } from 'react';
import { useSelector,useDispatch } from 'react-redux';
import { getOrders,orderUpdate } from 'web/axios/orderAPI';
function OrderList() {
    const number = useSelector((state)=> state.newOrder);
    const dispatch = useDispatch();
    useEffect(()=>{
        if(number>0){
            getOrders().then((res) => {
                setData(res.data);
            })
            dispatch({type:'init'})
        }
    },[number])


    let [data, setData] = useState([]);
    useEffect(() => {
        getOrders().then((res) => {
            setData(res.data);
        })
    }, [])
    useEffect(()=>{
        console.log("data=",data);
    },[data])
    const cook_Start = (i)=>{
        let arr = [...data];
        arr[i].orderStatus = '조리중';
        setData([...arr]);
        orderUpdate(arr[i]);

    }
    const cook_End = (i)=>{
        let arr = [...data];
        arr[i].orderStatus = '조리완료';
        setData([...arr]);
        orderUpdate(arr[i]);
    }
    return (
        <div className={styles.wrap}>
            <div className={styles.top}>
                <h4>새로 들어온 주문</h4>
                <span>(실시간알림)</span>
                <hr></hr>
                <ul className={styles.pl_15}>
                    {data.map((ele,i) => {
                        if (ele.orderStatus === '결제완료') {
                            return (
                                <li key={ele.orderId}>
                                    <Card order={ele} clickEvent={()=>{cook_Start(i)}} btnmsg={"조리시작>"}></Card>
                                </li>
                            )
                        }
                    })}
                </ul>
            </div>
            <div className={styles.bottom}>
                <h4>조리중인 주문</h4>
                <hr></hr>
                <ul className={styles.pl_15}>
                    {data.map((ele,i) => {
                        if (ele.orderStatus === '조리중') {
                            return (
                                <li key={ele.orderId}>
                                    <Card  order={ele} clickEvent={()=>{cook_End(i)}} btnmsg={"조리완료>"}></Card>
                                </li>
                            )
                        }
                    })}
                </ul>
            </div>
        </div>
    )
}

function Card({ order, btnmsg ,clickEvent}) {

    return (
        <div className={styles.cardwrap}>
            <h5>주문번호 <span style={{ color: 'red' }}>{order.orderId}</span> <span className={styles.time}>{order.orderTime.substring(10)}</span></h5>
            <div className={styles.tb}>
                <table>
                    <thead>
                        <tr>
                            <th>상품명</th>
                            <th>수량</th>
                        </tr>
                    </thead>
                    <tbody>
                        {order.orderItems.map((ele) => {
                            return (
                                <tr key={ele.itemCode}>
                                    <td>
                                        {ele.itemName}
                                    </td>
                                    <td>
                                        {ele.count}
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>

            <div>
                <button onClick={clickEvent}>{btnmsg}</button>
            </div>
        </div>
    )
}
export default OrderList;