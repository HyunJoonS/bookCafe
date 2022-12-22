import styles_view from 'css/book/component/ViewList.module.scss'
import { useNavigate } from 'react-router-dom';

function ViewList({data}){

    return(
        <>
        <div className={styles_view.wrap}>
            <ul>
                {data.map((data,index)=>{
                    return(
                        <List data={data}></List>
                    )
                })}                
            </ul>      
        </div>
        </>
        

    )
}
const List = ({data})=>{
    let nevigate = useNavigate();
    return(
        <li onClick={()=>{nevigate('./detail/'+data.id)}}>
        <div className={styles_view.imgbox}>
            <img src={data.image}></img>
        </div>
        <div className={styles_view.info}>
            <h6>
                <span>{data.title}</span>
                <span className={styles_view.badge}>코믹스</span>
                <span className={[styles_view.badge,styles_view.gray].join(' ')}>NEW</span>
            </h6>
            <p>
                <span>{data.lastModifiedDate}</span>
            </p>
            <a>
                <span>{data.author}</span>
            </a>
        </div>
    </li>
    )
}
export default ViewList;