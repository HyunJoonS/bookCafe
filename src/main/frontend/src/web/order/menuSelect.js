import styles from 'css/order/menuSelect.module.css';
import { FaPlus, FaMinus } from "react-icons/fa";
import { useState } from 'react';
import * as gvar from 'global_variables'
import { addCart } from 'web/axios/menuAPI';
function MenuSelect({visible, closeModal, data}){
    let imgurl = '/images/';
    
    let [quantity,setQuantity] = useState(1);    
    const plusQuantity = ()=>{
        console.log(quantity);
        if(quantity < 9){
            setQuantity(quantity+1);
        }
        else{
            alert("더이상 담을 수 없습니다.");
        }        
    }

    const minusQuantity = ()=>{
        console.log(quantity);
        if(quantity > 1){
            setQuantity(quantity-1);
        }
    }



    const addCartAction=()=>{
        let params={
            itemId : data.id,
            quantity : quantity
        }
        addCart(params);
        closeModal();
    }
    if(visible){
        return(
            <>            
                <div className={styles.wrap} >
                    <div className={styles.xbtn} onClick={closeModal}>
                     <FaPlus></FaPlus>
                    </div>          
                    <div className={styles.topBox}>
                        <img src={imgurl+data.photoPath} alt="menu"></img>
                    </div>
                    <div className={styles.middleBox}>
                        <h4>수량</h4>
                        <div className={styles.countBox}>
                            <button className={styles.minusBtn} onClick={minusQuantity}>
                                <FaMinus></FaMinus>
                            </button>
                            <p>{quantity}</p>
                            <button className={styles.plusBtn} onClick={plusQuantity}>
                                <FaPlus></FaPlus>
                            </button>
                        </div>
                        
                    </div>
                    <div className={styles.bottomBox} onClick={addCartAction}>
                        <h4>{(data.price*quantity).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원 담기</h4>
                    </div>                   
                </div>
            </>
        )
    }
   
}
export default MenuSelect;