import axios from "axios"


export const orderUpdate = (data)=>{
    let url = '/api/order'
    axios.post(url,JSON.stringify(data), {
        headers: {
            "Content-Type": `application/json`,
        },
        })
        .then((result) => {
            console.log(result.data);   
    
        })
        .catch((e) => {
            alert("오류가 발생하였습니다.");
        });    
}


export const getOrders = async()=>{
    try{
        let res = await axios.get('/api/orders');
        return res.status == 200 ? res : "error";
    }
    catch(error){
        return error;
    }         
}

export const getPaymentList = async(i)=>{
    let params = {
        day:i
    }
    try{
        let res = await axios.get('/api/payment',{params});
        return res.status == 200 ? res : "error";
    }
    catch(error){
        return error;
    }         
}
export const getOrder = async(id)=>{
    let params = {
        orderId : id
    }
    try{
        let res = await axios.get('/api/order',{params});
        return res.status == 200 ? res : "error";
    }
    catch(error){
        return error;
    }         
}