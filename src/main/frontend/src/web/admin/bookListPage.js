import axios from 'axios';
import styles from 'css/admin/admin.booklist.module.scss';
import { useEffect, useRef } from 'react';
import { useState } from 'react';
import Modal from './modal';
import styles2 from 'css/admin/admin.bookUpdate.module.scss';




const BookListPage=()=>{

    let url ='/bookList';
    let [data,setData] = useState([]);
    let [selectedData,setSelectedData] = useState([]);
    let [searchInput,setSearchInput] = useState();
    let [searchData,setSearchData] = useState([]);
    let [visible, setVisible] = useState(false);
    let searchSelectRef = useRef();
    
    const deleteBook = (id,index)=>{
        let pw = window.prompt("삭제를 원하시면 비밀번호를 입력해주세요");
        if(pw === ''){
            alert("공백 입니다.");
            return;
        }
        if(pw == undefined){
            return;
        }
        axios.delete('/api/book',{
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
        if(searchSelectRef.current.value == "title"){
            result = arr.filter((value)=> value.title.indexOf(searchInput) >=0);
        }
        else if(searchSelectRef.current.value == "author"){
            result = arr.filter((value)=> value.author.indexOf(searchInput) >=0);
        }
        
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
            <select ref={searchSelectRef}>
                <option key="title" value="title">제목</option>
                <option key="author" value="author">저자</option>
            </select>
            </div>
                <label>빠른검색</label>
                <input onChange={(e)=>{setSearchInput(e.target.value)}} value={searchInput}></input>
            <table>
                <thead>
                    <tr>
                        <th style={{width:'50px'}}>번호</th>
                        <th style={{width:'100px'}}>이미지</th>
                        <th style={{minWidth:'50px',width:'120px'}}>제목</th>
                        <th style={{width:'100px'}}>저자</th>
                        <th style={{width:'100px'}}>출판사</th>
                        <th style={{width:'100px'}}>최근수정일</th>
                        <th style={{minWidth:'300px', width:'400px'}}>줄거리</th>
                        <th style={{minWidth:'50px', width:'50px'}}>최종권수</th>
                        <th style={{minWidth:'50px', width:'50px'}}>완결여부</th>
                        <th style={{minWidth:'50px', width:'50px'}}>책장</th>
                        <th style={{minWidth:'100px', width:'100px'}}>비고</th>
                    </tr>
                </thead>
                <tbody>
                    {searchData.map((value,index) => {
                        return (
                            <tr key={value.id}>
                                <td>{index+1}</td>
                                <td><img src={value.image}></img></td>
                                <td>{value.title}</td>
                                <td>{value.author}</td>
                                <td>{value.publisher}</td>
                                <td>{value.lastModifiedDate}</td>
                                <td>{value.description}</td>
                                <td>{value.lastBooks+"권"}</td>
                                <td>{value.completionStatus}</td>
                                <td>{value.bookshelf}</td>
                                <td><button onClick={()=>{selectData(value)}} style={{marginBottom:'5px'}}>수정</button>
                                <button onClick={()=>{deleteBook(value.id,index)}}>삭제</button>
                                </td>
                            </tr>
                        )
                    })}

                </tbody>
            </table>
            {visible?
            <BookUpdateModal getList={getList} selectedData={selectedData} visible={visible} closeModal={closeModal}></BookUpdateModal>
            :null
            }
            
        </div>
    )
}

const BookUpdateModal = ({getList, visible, closeModal, selectedData})=>{
    let 제목ref = useRef();
    let 저자ref = useRef();
    let 출판ref = useRef();
    let 출간ref = useRef();
    let 줄거리ref = useRef();
    let 마지막권수ref = useRef();
    let 완결여부ref = useRef();
    let 책위치ref = useRef();
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
        let url = '/api/book'
        let arr={
            id:selectedData.id,
            title: 제목ref.current.value,
            author: 저자ref.current.value,
            description: 줄거리ref.current.value,
            image: 이미지경로ref.current.value,
            pubdate: 출간ref.current.value,
            publisher: 출판ref.current.value,
            lastBooks: 마지막권수ref.current.value,
            bookshelf: 책위치ref.current.value,
            completionStatus: 완결여부ref.current.value,
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
        제목ref.current.value=selectedData.title;
        저자ref.current.value=selectedData.author;
        출판ref.current.value=selectedData.publisher;
        출간ref.current.value=selectedData.pubdate;
        줄거리ref.current.value=selectedData.description;
        이미지경로ref.current.value=selectedData.image;
        마지막권수ref.current.value=selectedData.lastBooks;
        완결여부ref.current.value=selectedData.completionStatus;
        책위치ref.current.value=selectedData.bookshelf;

        set이미지(selectedData.image);
    }
    if(visible){
        return(
            <Modal closeModal={closeModal}>
                  <div className={styles2.tbdiv}>
                    <table>
                        <tbody>
                            <tr>
                                <td><label>제목</label></td>
                                <td><input ref={제목ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>저자</label></td>
                                <td><input ref={저자ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>출판</label></td>
                                <td><input ref={출판ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>출간</label></td>
                                <td><input ref={출간ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>줄거리</label></td>
                                <td><textarea ref={줄거리ref}></textarea></td>
                            </tr>
                            <tr>
                                <td><label>이미지</label></td>
                                <td>
                                    <input readOnly ref={이미지경로ref} style={{width:'300px'}}></input>
                                    <button>찾기</button>
                                </td>
                            </tr>
                            <tr>
                                <td><label>마지막권수</label></td>
                                <td><input ref={마지막권수ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>완결여부</label></td>
                                <td><select ref={완결여부ref}>
                                        <option value={"O"}>완결</option>
                                        <option value={"X"}>미완결</option>
                                    </select></td>
                            </tr>
                            <tr>
                                <td><label>책 위치</label></td>
                                <td>      
                                    <select ref={책위치ref}>
                                        <option value={"가"}>가</option>
                                        <option value={"나"}>나</option>
                                        <option value={"다"}>다</option>
                                        <option value={"라"}>라</option>
                                    </select></td>
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
                    <img src={이미지?이미지:null}></img>
                </div>
            </Modal>
        )
    }
}
export default BookListPage;