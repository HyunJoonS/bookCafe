
import styles_cart from "css/order/cart.module.scss";
import { FaShoppingCart } from "react-icons/fa";
import { useEffect, useState, useRef } from "react";
import { FaPlus, FaMinus, FaExclamationCircle } from "react-icons/fa";
import axios from "axios";
import { Simulate } from "react-dom/test-utils";
import Receipt from "./Receipt";


function Cart() {
    let [visible, setVisible] = useState(false);
    let [quantity, setQuantity] = useState(0);
    let [data, setData] = useState([]);
    let [totalPrice, setTotalPrice] = useState(0);
    const plusQuantity = (index) => {
        if (data[index].quantity < 9) {
            setData([...data], data[index].quantity += 1);
        }
        else {
            alert("더이상 담을 수 없습니다.");
        }
    }
    const minusQuantity = (index) => {
        if (data[index].quantity > 1) {
            setData([...data], data[index].quantity -= 1);
        }
    }
    const deleteCartItem = (index) => {
        console.log("제거")
        data.splice(index,1);
        setData([...data]);

    }
    useEffect(() => {
        if (data.length > 0) {
            getTotalPrice();
        }
        else{
            setTotalPrice(0);
        }
    }, [data])


    const modalRef = useRef();

    const getCart = () => {
        let url = '/api/menu/cart';
        console.log("리스트가져옴");
        axios.get(url)
            .then((result) => {
                setData(result.data);
                console.log(result.data);
            })
            .catch((e) => {
                alert("데이터를 불러오는중 오류가 발생하였습니다.");
            });
    }

    const updateCart = () => {
        let url = '/api/menu/cart';
        console.log("업데이트");
        if(data.length<=0){
            return;
        }
        axios.put(url, JSON.stringify(data), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
            .then((result) => {
                console.log(result.data);
            })
            .catch((e) => {
                alert("데이터 업데이트중 오류가 발생하였습니다.");
            });
    }

    const orderCart =()=>{
        let url = '/pay';
        console.log("주문")
        if(data.length<=0){
            alert("장바구니에 물건을 담아주세요");
            return;
        }
        axios.post(url,JSON.stringify(data), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
            .then((result) => {
                console.log(result.data);
                let winObj =window.open(result.data.next_redirect_pc_url,"결제", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
                // let winObj =window.open("http://localhost:3000/openerdetour","네이버새창", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
                window.callbackFunction=(orderID)=>{
                    setOrderId(orderID);
                    openModalReceipt();
                    winObj.close();
                    setData([]);
                    setVisible(false);
                    clearCart();
                };

            })
            .catch((e) => {
                alert("결제 진행중 오류가 발생하였습니다.");
            });
    }


    const clearCart = () => {
        axios.delete('/api/menu/cart').then((res) => {
            setData([]);
            console.log(res);
        })
    }

    const closeCart = () => {
        updateCart();
        setVisible(false)
        const scrollY = document.body.style.top;
        document.body.style.cssText = '';
        window.scrollTo({
            top: parseInt(scrollY || '0', 10) * -1,
            left: 0,
            behavior: 'instant'
        });
    }


    const openCart = () => {
        setVisible(true)
        document.body.style.cssText = `
        position: fixed; 
        top: -${window.scrollY}px;
        overflow-y: scroll;
        width: 100%;`
            ;
        getCart();
    }

    let getTotalPrice = () => {
        let sum = 0;
        data.map((ele) => {
            sum += ele.price * ele.quantity;
        })
        setTotalPrice(sum);
    }
    let [visibleReceipt , setVisibleReceipt] = useState(false);
    let [orderId , setOrderId] = useState(0);
    const openModalReceipt =()=>{
        setVisibleReceipt(true);
    }
    const closeModalReceipt =()=>{
        setVisibleReceipt(false);
    }
    
    return (
        <>
            <div className={visible ? styles_cart.cartWrapBackground : null}>
            </div>
            <div className={visible ? styles_cart.cartWrap : [styles_cart.cartWrap, styles_cart.cartWrap2].join(" ")} ref={modalRef}>
                <div className={styles_cart.xbtn}
                    onClick={closeCart}>
                    <FaPlus />
                </div>
                <div className={styles_cart.cartWrap3}>


                    <div className={styles_cart.top}>
                        <h5>장바구니</h5>
                        <p id="asdasd">이전 주문내역</p>
                        <span onClick={clearCart}>전체 비우기</span>
                    </div>
                    <div className={styles_cart.messageLine}>
                        <FaExclamationCircle></FaExclamationCircle>
                        <p>1인 1주문은 필수입니다.</p>
                    </div>

                    <div className={styles_cart.middle}>
                        <ul>
                            {data.length > 0 ? data.map((ele, index) => {
                                return (
                                    <ListItem data={ele} plusQuantity={() => { plusQuantity(index) }} minusQuantity={() => { minusQuantity(index) }} 
                                    deleteCartItem={()=>{deleteCartItem(index)}}></ListItem>
                                )
                            }) : <p>장바구니가 비었습니다.</p>}
                        </ul>
                    </div>
                    <div className={styles_cart.bottom} onClick={orderCart}>
                        <h4>
                            {totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원 주문하기
                        </h4>
                    </div>
                </div>
            </div>

            <CartButton setVisible={openCart}></CartButton>

            {visibleReceipt?
            <Receipt orderId={orderId} closeModal={closeModalReceipt}></Receipt>
            :null
            }
        </>
    )
}


function CartButton({ setVisible }) {
    return (
        <>
            <div className={styles_cart.cartBtnWrap}
                onClick={setVisible}>
                <FaShoppingCart></FaShoppingCart>
            </div>
        </>
    )
}

function ListItem({ data, plusQuantity, minusQuantity, deleteCartItem }) {
    return (
        <>
            <li>
                <h5>{data.name}</h5>
                <div className={styles_cart.xbtn2} onClick={deleteCartItem}>
                    <FaPlus></FaPlus>
                </div>
                <div className={styles_cart.countBox}>
                    <span className={styles_cart.minusBtn} onClick={minusQuantity}>
                        <FaMinus></FaMinus>
                    </span>
                    <p>{data.quantity}</p>
                    <span className={styles_cart.plusBtn} onClick={plusQuantity}>
                        <FaPlus></FaPlus>
                    </span>
                </div>
                <div className={styles_cart.cost}>
                    {(data.price * data.quantity).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                </div>
            </li>
        </>
    )
}


export default Cart;