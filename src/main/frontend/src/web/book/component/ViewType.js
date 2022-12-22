import React, { useState } from "react";
import styles from 'css/book/component/ViewType.module.scss';
function ViewType({setOrderBy}) {   
    let [tabIndex,setTabIndex] = useState(0);

    const changeTabIndex = (tabNum,orderBy)=>{
        setTabIndex(tabNum);
        setOrderBy(orderBy);
    } 
    return (
        <div className={styles.wrap}>
            <ul>
                <li className={tabIndex == 0 && styles.on}
                        onClick={()=>{changeTabIndex(0,"id")}}>
                    <a>인기순</a>
                </li>   
                <li className={tabIndex == 1 && styles.on}
                        onClick={()=>{changeTabIndex(1,"title")}}>
                    <a>이름순</a>
                </li>                 
                <li className={tabIndex == 2 && styles.on}
                        onClick={()=>{changeTabIndex(2,"lastModifiedDate,desc")}}>
                    <a>업데이트순</a>
                </li>   
            </ul>
        </div>
    )
  }
  export default ViewType;