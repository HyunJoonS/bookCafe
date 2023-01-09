import MenuNav from "./menuNav";
import styles from "css/order/menuList.module.css";
import MenuSelect from "./menuSelect";
import { useEffect, useState } from 'react';
import Cart from "./cart";
import arrayData from "./menuTemp.json"
import axios from "axios";
import * as gvar from 'global_variables'

function MenuList() {
    let [modal, setModel] = useState(false);
    let [selectMenu, setSelectMenu] = useState("ALL");
    let [selectId, setSelectId] = useState(0);
    let [data, setData] = useState([]);
    let imgurl = '/images/';
    let url = '/api/menu';

    const getMenu = () => {
        axios.get(url).then((res) => {
            setData([...res.data]);
            console.log(...res.data);
        }).catch()
    }
    useEffect(() => {
        getMenu();
    }, [])
    useEffect(() => {
        console.log(data);
    }, data)

    const changeSelectMenu = (menuName) => {
        setSelectMenu(menuName)
    }
    const onModal = (id) => {
        setSelectId(id);
        setModel(true);
    }
    const closeModal = () => {
        setModel(false);
    }
    
    const menuTab = [
        {
            name: "DRINK", tab:
                <List category={"DRINK"} onModal={onModal} data={data}
                    setSelectId={setSelectId}></List>
        },
        {
            name: "FOOD", tab:
                <List category={"FOOD"} onModal={onModal} data={data}
                    setSelectId={setSelectId}></List>
        },
        {
            name: "BAKERY", tab:
                <List category={"BAKERY"} onModal={onModal} data={data}
                    setSelectId={setSelectId}></List>
        }
    ]
    return (
        <>
            <MenuNav changeSelectMenu={changeSelectMenu}></MenuNav>

            {
                data.length > 0 ?
                    menuTab.map((ele, index) => {
                        return (
                            <>
                                {ele.name == selectMenu || selectMenu == "ALL" ? data.length > 0 ? ele.tab : null
                                    : null}
                            </>
                        )
                    })
                    : null
            }

            {/* <List category={"Drink"} onModal={onModal}></List>
            <List category={"Food"} onModal={onModal}></List> */}
            <MenuSelect data={data.find(e => e.id == selectId)} visible={modal} closeModal={closeModal}></MenuSelect>
            <Cart></Cart>
        </>
    )
}
// menuTab.map((ele,index)=>{
//     return(
//         <>
//             {ele.name == selectMenu || selectMenu == "All" ? data.length>0? ele.tab : null
//             :null}
//         </>
//     )
// })
function List(props) {
    let [arr, setArr] = useState([]);
    let tempArr;
    useEffect(() => {
        tempArr = props.data.filter((element) => {
            return element.category === props.category;
        })
        setArr([...tempArr]);
    }, [])
    let imgurl = '/images/';

    return (
        <div className={styles.wrap}>
            <h4>{props.category}</h4>
            <ul>
                {arr.map((e) => {
                    return (
                        <li onClick={() => { props.onModal(e.id) }}>
                            <div className={styles.photo}>
                                <img src={imgurl + e.photoPath} alt="menu"></img>
                            </div>
                            <h5>{e.name}</h5>
                            <p>{e.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p>
                        </li>
                    )
                })}
            </ul>
        </div>
    )
}
export default MenuList;
