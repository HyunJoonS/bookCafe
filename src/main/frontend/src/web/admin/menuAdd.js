import { useRef, useState } from "react";
import styles from 'css/admin/admin.module.scss';
import axios from "axios";
import styles2 from 'css/admin/admin.bookAdd.module.scss';

import { useEffect } from "react";

const MenuAdd=()=>{
    let 카테고리ref = useRef();
    let 메뉴이름ref = useRef();
    let 가격ref = useRef();
    let 재고ref = useRef();
    let 이미지경로ref = useRef();
    let 이미지파일ref = useRef();
    let [이미지,set이미지] = useState('/img/noimg.jpg');

    const SaveDB = async(formData)=>{
        let axiosConfig = {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        }
        try{
            let response  = await axios.post("/api/menu",formData,axiosConfig)
            alert("성공적으로 저장되었습니다.");
        }catch{
            alert("에러");
        }
    }

    const onSubmit =  (e) => {
        e.preventDefault();
        e.persist();
        let formData = new FormData(); // formData 객체를 생성한다.

        let files = e.target.imgFile.files; // Form의 input을 들고온다.
        for (let i = 0; i < files.length; i++) { 
          formData.append("file", files[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
        }
        const params = {
            category : 카테고리ref.current.value,
            name : 메뉴이름ref.current.value,
            price : 가격ref.current.value,
            stockQuantity : 재고ref.current.value,
        }
        if(files.length === 0){
            alert("이미지가 비어있습니다.");
            return;
        }
        else if(params.category === '' || params.name === '' || params.price === '' || params.stockQuantity ===''){
            alert("빈칸을 모두 채워주세요");
            return;
        }

        let pw = window.prompt("삭제시 사용할 비밀번호를 입력해 주세요");
        if(pw===''){
            alert("비밀번호가 비어있을 수 없습니다.");
            return;
        }

        if(pw === null){
            return;
        }
        let pw2 = window.prompt("비밀번호 확인");
        if(pw != pw2){
            alert("확인 비밀번호가 일치하지 않습니다.");
            return;
        }
        params.password = pw;

        formData.append("data", new Blob([JSON.stringify(params)], { type: "application/json" }));      
        SaveDB(formData); 
      };

    const preView = (fileBlob)=>{
        console.log(이미지파일ref.current.files[0]);
        const reader = new FileReader();
        reader.readAsDataURL(fileBlob);
        return new Promise((resolve) => {
          reader.onload = () => {
            set이미지(reader.result);
            resolve();
          };
        });
    }
    
    return(
        <>
        <div className={styles2.tbdiv}>
            <h4>메뉴 추가하기</h4>
            <hr></hr>
        <form onSubmit={(e) => onSubmit(e)}>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <label>카테고리</label>
                        </td>
                        <td>
                            <select ref={카테고리ref}>
                                <option value={"FOOD"}>음식</option>
                                <option value={"DRINK"}>음료</option>
                                <option value={"BAKERY"}>빵</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>메뉴이름</label>
                        </td>
                        <td>
                            <input ref={메뉴이름ref}></input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>가격</label>
                        </td>
                        <td>
                            <input type={'number'} ref={가격ref}></input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>재고</label>
                        </td>
                        <td>
                            <input type={'number'} ref={재고ref}></input>
                        </td>
                    </tr>
   
                    <tr>
                        <td>
                            <label>이미지</label>
                        </td>
                        <td>
                            <input type="file" name="imgFile" ref={이미지파일ref} 
                            onChange={(e)=>{preView(e.target.files[0])}}></input>
                        </td>
                    </tr>    
                </tbody>
            </table>

            <div className={styles2.btns}>
                <button type="submit">저장</button>
            </div>
            </form>
            <div className={styles2.imgdiv}>
            <h4>이미지 미리보기</h4><br></br>
            <img src={이미지?이미지:null}></img>
        </div>
        </div>
    
    </>
    )
}


export default MenuAdd;

