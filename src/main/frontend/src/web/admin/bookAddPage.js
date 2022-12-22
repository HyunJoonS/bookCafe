import axios from 'axios';
import BookApiModal from './bookApiPopup';
import { useEffect, useState } from 'react';
import { clear } from '@testing-library/user-event/dist/clear';
import { useRef } from 'react';
import styles from 'css/admin/admin.module.scss';
import styles2 from 'css/admin/admin.bookAdd.module.scss';
import {save} from 'web/axios/bookAPI';
function BookAddPage() {
    let [data, setData] = useState();
    let [visible, setVisible] = useState(false);
    let [searchQuery, setSerchQuery] = useState();
    let 검색ref = useRef();
    let 제목ref = useRef();
    let 저자ref = useRef();
    let 출판ref = useRef();
    let 출간ref = useRef();
    let 줄거리ref = useRef();
    let 책장ref = useRef();
    let 완결여부ref = useRef();
    let 마지막권수ref = useRef();
    let 이미지경로ref = useRef();
    let [이미지, set이미지] = useState('/img/noimg.jpg');

    const openPopup = () => {
        let str = 검색ref.current.value;
        str = str.replace(/\s/g, "");
        if (str == "") {
            alert("공백 입니다.");
        }
        else {
            setVisible(true);
        }

    }

    const clearData = () => {
        setData(null);
        setVisible(false);
    }

    const ChangeSelectData = (selectData) => {
        제목ref.current.value = selectData.title;
        저자ref.current.value = selectData.author;
        출판ref.current.value = selectData.publisher;
        출간ref.current.value = selectData.pubdate;
        줄거리ref.current.value = selectData.description;
        이미지경로ref.current.value = selectData.image;
        set이미지(selectData.image);
        setData(null);
        setVisible(false);
    }

    
    const SaveDB = ()=>{
        let params = {
            title: 제목ref.current.value,
            author: 저자ref.current.value,
            publisher: 출판ref.current.value,
            pubdate: 출간ref.current.value,
            description: 줄거리ref.current.value,
            image: 이미지경로ref.current.value,
            bookshelf : 책장ref.current.value,
            lastBooks : 마지막권수ref.current.value,
            completionStatus : 완결여부ref.current.value
        }
        if(params.title === '' || params.author === '' || params.publisher === '' ||
            params.pubdate === '' || params.description === '' || params.image === '' ||
            params.bookshelf === '' || params.lastBooks === '' || params.completionStatus === ''){
                alert("빈칸을 모두 채워주세요!");
            }
            else{
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
                save(params);
            }
    }

    return (
        <>
            <div className={styles2.tbdiv}>
                <div className={styles2.box}>

                <h4>책 추가하기</h4>
                <hr></hr>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label>책 검색(API)</label>
                                </td>
                                <td>
                                    <input ref={검색ref} onChange={(e) => { setSerchQuery(e.target.value) }} style={{ width: '300px' }} placeholder='책 제목을 적고 검색버튼을 눌러보세요'></input>
                                    <button onClick={openPopup}>검색</button>
                                </td>
                            </tr>
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
                                <td><input type={'number'} ref={출간ref}></input></td>
                            </tr>
                            <tr>
                                <td><label>줄거리</label></td>
                                <td><textarea ref={줄거리ref}></textarea></td>
                            </tr>
                            <tr>
                                <td><label>이미지</label></td>
                                <td>
                                    <input readOnly ref={이미지경로ref} style={{ width: '300px' }}></input>
                                    <button>찾기</button>
                                </td>
                            </tr>
                            <tr>
                                <td><label>마지막권수</label></td>
                                <td><input type={'number'} ref={마지막권수ref}></input></td>
                            </tr>
                            <tr>
                                <td>
                                    <label>완결여부</label>
                                </td>
                                <td>
                                    <select ref={완결여부ref}>
                                        <option value={"O"}>완결</option>
                                        <option value={"X"}>미완결</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>책 위치</label>
                                </td>
                                <td>
                                    <select ref={책장ref}>
                                        <option value={"가"}>가</option>
                                        <option value={"나"}>나</option>
                                        <option value={"다"}>다</option>
                                        <option value={"라"}>라</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div className={styles2.btns}>
                        <button onClick={SaveDB}>저장</button>
                    </div>
                    <div className={styles2.imgdiv}>
                        <h4>이미지 미리보기</h4><br></br>
                        <img src={이미지 ? 이미지 : null}></img>
                    </div>
                </div>
            </div>


            {visible ? <BookApiModal
                data={data} setData={setData} clearData={clearData} searchQuery={searchQuery}
                ChangeSelectData={ChangeSelectData}

            ></BookApiModal> : null}
        </>
    )
}
export default BookAddPage;