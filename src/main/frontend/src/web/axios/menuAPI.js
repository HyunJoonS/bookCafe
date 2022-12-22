import axios from "axios"

export const addCart = async(params) => {
    const url = '/api/menu/cart';
    axios.post(url,null,{params})
    .then((result)=>{
        console.log(result);
    })
    .catch((e)=>{
        alert("error")
    });
}
