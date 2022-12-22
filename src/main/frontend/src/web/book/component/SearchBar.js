import React, { useState } from "react";
import { FaSearch } from "react-icons/fa";
import {  useNavigate } from "react-router-dom";
import styles from 'css/book/component/searchBar.module.scss';
function SearchBar() {    
    let [검색어, set검색어] = useState('');
    let navigate = useNavigate();
    function 검색() {
        navigate('/book/search?query=' + 검색어);
    }
    return (
        <div className={styles.searchWrapper}>
            <div className={styles.box_search}>
                <input type="text" className={styles.input_search} name="q" title="검색어 입력" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" placeholder="책, 도서 검색"
                    onChange={(e) => { set검색어(e.target.value) }}
                    onKeyUp={(e) => { if (e.key == 'Enter') 검색() }} />
                <button type="button" className={styles.btn_search} data-tiara-layer="search" data-tiara-copy="내부검색" onClick={() => { 검색() }}>
                    <FaSearch></FaSearch>
                </button>
            </div>
        </div>        
    )
  }
  export default SearchBar;