import React, { useState } from "react";
import styles from 'css/book/component/Tab.module.scss';

function Tab({setBooksehlf}) {    
    let [tabIndex,setTabIndex] = useState(0);

    const changeTabIndex = (tabNum,bookshelf)=>{
        setTabIndex(tabNum);
        setBooksehlf(bookshelf);
    }

    return (
        <div className={styles.wrap}>
            <ul>
                <li className={tabIndex == 0 && styles.on}
                    onClick={()=>{changeTabIndex(0,'전체')}}>
                    <a>전체</a>
                </li>   
                <li className={tabIndex == 1 && styles.on}
                    onClick={()=>{changeTabIndex(1,'가')}}>
                    <a>가 책장</a>
                </li>                 
                <li className={tabIndex == 2 && styles.on}
                    onClick={()=>{changeTabIndex(2,'나')}}>
                    <a>나 책장</a>
                </li>   
                <li className={tabIndex == 3 && styles.on}
                    onClick={()=>{changeTabIndex(3,'다')}}>
                    <a>다 책장</a>
                </li> 
                <li className={tabIndex == 4 && styles.on}
                    onClick={()=>{changeTabIndex(4,'라')}}>
                    <a>라 책장</a>
                </li>
                <li className={tabIndex == 5 && styles.on}
                    onClick={()=>{changeTabIndex(5,'마')}}>
                    <a>마 책장</a>
                </li>   
            </ul>
        </div>
    )
  }
  export default Tab;