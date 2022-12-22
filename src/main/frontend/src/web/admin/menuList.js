import axios from 'axios';
import styles from 'css/admin/admin.booklist.module.scss';
import { useEffect, useRef } from 'react';
import { useState } from 'react';
import Modal from './modal';
import styles2 from 'css/admin/admin.bookUpdate.module.scss';
import * as gvar from 'global_variables'



const MenuList=()=>{
    let imgurl = '/images/';
    let url ="/api/menu";
    let [data,setData] = useState([]);
    let [selectedData,setSelectedData] = useState([]);
    let [searchInput,setSearchInput] = useState();
    let [searchData,setSearchData] = useState([]);
    let [visible, setVisible] = useState(false);

    
    const deleteMenu = (id,index)=>{
        let pw = window.prompt("삭제를 원하시면 비밀번호를 입력해주세요");
        if(pw === ''){
            alert("공백 입니다.");
            return;
        }
        if(pw == undefined){
            return;
        }
        axios.delete('/api/menu',{
            params:{
                id:id,
                password : pw
            }
        })
            .then((res)=>{
                let arr = [...data];
                arr.splice(index,1);
            console.log(arr);
            setData(arr);
        }).catch(({response})=>{
            alert(response.data.message);
        })
        
    }

    const getList = ()=>{
        axios.get(url).then((res)=>{
            setData([...res.data]);
        }).catch()
    }
    useEffect(()=>{
        getList();
    },[])
    useEffect(()=>{
        console.log(data);
        setSearchData([...data]);
    },[data])

    useEffect(()=>{
        console.log(searchInput);
        let arr = [...data];
        let result;
        result = arr.filter((value)=> value.name.indexOf(searchInput) >=0);
        // else if(searchSelectRef.current.value == "author"){
        //     result = arr.filter((value)=> value.author.indexOf(searchInput) >=0);
        // }
        
        console.log(result);
        setSearchData(result);


    },[searchInput])

    useEffect(()=>{
        console.log(visible);
    },[visible])
    
    
    const closeModal = ()=>{
        setVisible(false);
        console.log("모달실행됨");
    }

    const selectData = (value)=>{
        setVisible(true);
        setSelectedData(value);
    }

    return(
        <div className={styles.wrap}>
            <div className={styles.searchBox}>
            <select>
                <option key="banana" value="banana">제목</option>
            </select>
            </div>
                <label>빠른검색</label>
                <input onChange={(e)=>{setSearchInput(e.target.value)}} value={searchInput}></input>
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th style={{width:'100px'}}>이미지</th>
                        <th>이름</th>
                        <th>가격</th>
                        <th>재고</th>
                        <th>카테고리</th>
                        <th style={{width:'100px'}}>비고</th>
                    </tr>
                </thead>
                <tbody>
                    {searchData.map((value,index) => {
                        return (
                            <tr key={value.id}>
                                <td>{index+1}</td>
                                <td><img src={imgurl+value.photoPath}></img></td>
                                <td>{value.name}</td>
                                <td>{value.price}</td>
                                <td>{value.stockQuantity}</td>
                                <td>{value.category}</td>
                                <td><button onClick={()=>{selectData(value)}} style={{marginBottom:'5px'}}>수정</button>
                                <button onClick={()=>{deleteMenu(value.id,index)}}>삭제</button>
                                </td>
                            </tr>
                        )
                    })}

                </tbody>
            </table>
            {visible?
            <MenuUpdateModal getList={getList} selectedData={selectedData} visible={visible} closeModal={closeModal}></MenuUpdateModal>
            :null
            }
            
        </div>
    )
}

const MenuUpdateModal = ({getList,visible, closeModal, selectedData})=>{
    let imgurl = '/images/'
    let 메뉴이름ref = useRef();
    let 카테고리ref = useRef();
    let 가격ref = useRef();
    let 재고ref = useRef();
    let 이미지경로ref = useRef();
    let [이미지,set이미지] = useState('/img/cartoon/naruto.jpg');
    const SaveDB = ()=>{
        let pw = window.prompt("수정을 원하시면 비밀번호를 입력해주세요");
        if(pw === ''){
            alert("공백 입니다.");
            return;
        }
        if(pw == undefined){
            return;
        }
        let url = '/api/menu'
        let arr={
            id:selectedData.id,
            category : 카테고리ref.current.value,
            name : 메뉴이름ref.current.value,
            price : 가격ref.current.value,
            stockQuantity : 재고ref.current.value,
            password:pw
        }
        axios.put(url,JSON.stringify(arr), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
            .then((result) => {
                alert("수정 되었습니다.");
                getList();
            })
            .catch(({response}) => {
                alert(response.data.message);
            });

    }

    useEffect(()=>{
        cencel();
    },[selectedData])

    const cencel =()=>{
        카테고리ref.current.value=selectedData.category;
        메뉴이름ref.current.value=selectedData.name;
        가격ref.current.value=selectedData.price;
        재고ref.current.value=selectedData.stockQuantity;
        이미지경로ref.current.value=selectedData.photoPath;
    }
    if(visible){
        return(
            <Modal closeModal={closeModal}>
                  <div className={styles2.tbdiv}>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label>카테고리</label>
                                </td>
                                <td>
                                    <input ref={카테고리ref}></input>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>이름</label>
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
                                    <input ref={가격ref}></input>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>재고</label>
                                </td>
                                <td>
                                    <input ref={재고ref}></input>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>이미지</label>
                                    
                                </td>
                                <td>
                                    <input readOnly ref={이미지경로ref} style={{width:'300px'}}></input>
                                    <button>찾기</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div className={styles2.btns}>
                        <button onClick={cencel}>되돌리기</button>
                        <button onClick={SaveDB}>수정하기</button>
                    </div>
    
                </div>
                <div className={styles2.imgdiv}>
                    <h4>이미지 미리보기</h4><br></br>
                    <img src={imgurl+selectedData.photoPath}></img>
                </div>
            </Modal>
        )
    }
}
export default MenuList;