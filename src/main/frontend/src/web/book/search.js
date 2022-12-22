import SearchBar from './component/SearchBar';
import styles from 'css/book/search.module.scss';
import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import axios from 'axios';
import { useState } from 'react';

function Search() {
    let navigate = useNavigate();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const query = searchParams.get('query'); // id 취득
    let [data, setData] = useState([]);
    const goDetail = (id) => {
        navigate('/book/detail/' + id);
    }
    useEffect(() => {
        axios.get("/api/book/search", {
            params: {
                searchQuery: query
            }
        }).then((response) => {
            setData(response.data);
        }).catch((err) => { console.log(err) });
    }, [query])
    return (
        <div className={styles.wrap}>
            <SearchBar></SearchBar>
            <h4 className={styles.searchTitle}>'<div className={styles.red}>{query}</div>' 검색결과</h4>
            <hr></hr>

            <div>
                <ul>
                    {data.map((ele, index) => {
                        return (
                            <li>
                                <div className={styles.imgBox} onClick={()=>{goDetail(ele.id)}}>
                                    <img src={ele.image}></img>
                                </div>
                                <div>
                                    <h5 onClick={()=>{goDetail(ele.id)}}>{ele.title}</h5>
                                    <h6 onClick={()=>{goDetail(ele.id)}}>{ele.author}</h6>
                                    <dl>
                                        <dt>
                                            최종권수
                                        </dt>
                                        <dd>{ele.lastBooks}</dd>
                                    </dl>
                                    <dl>
                                        <dt>
                                            완결여부
                                        </dt>
                                        <dd> {ele.completionStatus}</dd>
                                    </dl>
                                    <dl>
                                        <dt>
                                            장르
                                        </dt>
                                        <dd>코믹스</dd>
                                    </dl>
                                    <dl>
                                        <dt>
                                            책장위치
                                        </dt>
                                        <dd>{ele.bookshelf}</dd>
                                    </dl>
                                </div>
                            </li>
                        )
                    })}

                </ul>
            </div>
        </div>
    )
}

export default Search;