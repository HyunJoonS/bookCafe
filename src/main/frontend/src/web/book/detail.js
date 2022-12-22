import axios from 'axios';
import styles from 'css/book/detail.module.scss';
import { useEffect } from 'react';
import { useState } from 'react';
import SearchBar from './component/SearchBar';

import { useNavigate, useParams } from "react-router-dom";
import moment from 'moment';
function Detail() {
    let nevigator = useNavigate();
    let [data, setData] = useState([]);
    let { id } = useParams();


    function change_date(published_at) {
        const publish_date = moment(published_at).format('YYYY년 MM월 DD일')
        return publish_date
    }


    useEffect(() => {
        console.log("호출됨");
        axios.get("/api/book/detail/" + id).then((response) => {
            let arr = [];
            arr.push(response.data);
            setData(arr);
        });
    }, [])

    useEffect(() => {
        console.log(data);
    }, [data])
    return (
        <div className={styles.wrap}>
            <SearchBar></SearchBar>
            {data.length>0 ?
                <div className={styles.topWrap}>
                    <div className={styles.imageBox}>
                        <img src={data[0].image}></img>
                    </div>
                    <div className={styles.infoBox}>
                        <h4>{data[0].title}</h4>
                        <ul>
                            <li>
                                <dt>저자</dt>
                                <dl>{data[0].author}</dl>
                            </li>
                            <li>
                                <dt>출판</dt>
                                <dl>{data[0].publisher}</dl>
                            </li>
                            <li>
                                <dt>최종권수</dt>
                                <dl>{data[0].lastBooks}</dl>
                            </li>
                            <li>
                                <dt>완결여부</dt>
                                <dl>{data[0].completionStatus}</dl>
                            </li>
                            <li>
                                <dt>마지막 수정일</dt>
                                <dl>{data[0].createDateTime}</dl>
                            </li>
                        </ul>
                        <hr></hr>
                        <h5>줄거리</h5>
                        <p>{data ? data[0].description : null}</p>
                        <hr></hr>
                        <h5>책 위치</h5>
                        가 책장
                    </div>
                </div> 
                : <>데이터가 없습니다</>}
            <hr></hr>
            <div className={styles.maps}>
                <h4>위치 안내도</h4><br></br>
                <img src="/img/cartoon/map.PNG"></img>
            </div>
            <div className={styles.goback}>
                <a onClick={()=>{nevigator('/book')}}>목록으로</a>
            </div>
        </div>

    )
}
export default Detail;