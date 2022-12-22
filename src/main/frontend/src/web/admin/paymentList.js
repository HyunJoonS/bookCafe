import styles from 'css/admin/paymentlist.module.scss'
import styles2 from 'css/admin/orderlist.module.scss'

import { useEffect } from 'react';
import { useState } from 'react';
import { getPaymentList, getOrder } from 'web/axios/orderAPI';
import Modal from './modal';
function PatmentList() {
    let [data, setData] = useState([]);
    let [detail, setDetail] = useState([]);
    let [visible, setVisible] = useState(false);
    let [selectDays,setSelectDays] = useState(0);



    const changeSelectDays=(index)=>{
        setSelectDays(index);
        if(index>0){
            getPaymentList(index).then((res) => {
                setData(res.data);
            })
        }else{
            getPaymentList().then((res) => {
                setData(res.data);
            })
        }
        
    }

    const openModal = () => {
        setVisible(true);
    }
    const closeModal = () => {
        setVisible(false);
    }

    useEffect(() => {
        getPaymentList().then((res) => {
            setData(res.data);
        })
    }, [])

    const getOrderApi = (id) => {
        getOrder(id).then((res) => {
            let arr = [];
            arr.push(res.data);
            setDetail(arr);
        })
    }
    useEffect(() => {
        console.log("payment=", data);
    }, [data])

    useEffect(() => {
        console.log("detail=",detail);
        if(detail.length>0){
            openModal();
        }
    }, [detail])

    return (
        <div className={styles.wrap}>
            <h4>
                매출현황
            </h4>
            <hr>
            </hr>

            <div className={styles.spanbtns}>
                <span onClick={()=>{changeSelectDays(0)}} className={selectDays ===0 ?styles.selected:null}>전체</span>
                <span onClick={()=>{changeSelectDays(1)}} className={selectDays ===1 ?styles.selected:null}>최근 1일</span>
                <span onClick={()=>{changeSelectDays(7)}} className={selectDays ===7 ?styles.selected:null}>최근 7일</span>
                <span onClick={()=>{changeSelectDays(30)}} className={selectDays ===30 ?styles.selected:null}>최근 30일</span>
            </div>

            <table className={styles.tb}>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>결제코드</th>
                        <th>결제금액</th>
                        <th>결제시간</th>
                        <th>주문상품</th>
                        <th>비고</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        data.map((ele, i) => {
                            return (
                                <tr key={ele.orderId}>
                                    <td>{i + 1}</td>
                                    <td>{ele.tid}</td>
                                    <td>{ele.paymentCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</td>
                                    <td>{ele.paymentTime}</td>
                                    <td>{ele.itemName}</td>
                                    <td><a onClick={() => { getOrderApi(ele.orderId) }}>상세보기</a></td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
            {
                visible ?
                <Modal closeModal={closeModal}>
                    <Card order={detail[0]} ></Card>
                </Modal>
                :null
            }

        </div>
    )
}

function Card({ order }) {
    return (
        <div className={styles.cardwrap}>
            <h5>주문번호 <span>{order.orderId}</span>  </h5><span>tid : {order.tid}</span> <span className={styles.time}>결제시각 : {order.orderTime}</span>
            <div className={styles.tb}>
                <table>
                    <thead>
                        <tr>
                            <th>상품</th>
                            <th>단가</th>
                            <th>수량</th>
                            <th>합계</th>
                        </tr>
                    </thead>
                    <tbody>
                        {order.orderItems.map((ele) => {
                            return (
                                <tr>
                                    <td>
                                        {ele.itemName}
                                    </td>
                                    <td>
                                        {ele.itemPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                                    </td>
                                    <td>
                                        {ele.count}
                                    </td>
                                    <td>
                                        {(ele.count*ele.itemPrice).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </table>

            </div>
            <div className={styles.totalPrice}>
                <div className={styles.fs}>합계 금액 :</div> {order.orderTotalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
            </div>
        </div>
    )
}
export default PatmentList;
