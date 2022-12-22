import styles from 'css/book/bookMain.module.scss';
import SearchBar from './component/SearchBar';
import Tab from './component/Tab';
import ViewType from './component/ViewType';
import CardListSlide from './component/CardList';
import Title from './component/Title';
import ViewList from './component/ViewList';
import Banner from './component/banner';
import { useCallback, useEffect, useRef } from 'react';
import { getBookList,getBookListPageing } from 'web/axios/bookAPI'
import { useState } from 'react';
import Loading from 'web/Loading';
function BookMain() {

    return (
        <div className={styles.wrapOutside}>
            <Banner></Banner>
            <div className={styles.wrap}>
                <SearchBar />
                <TopBox></TopBox>
            </div>
        </div>
    )
}

function TopBox() {
    let [data, setData] = useState([]);
    let [page,setPage] = useState(0);
    let [last,setLast]= useState(false);
    let [isLoading, setIsLoading ] = useState(false);

    let [filter, setFilter] = useState({
        bookshelf: "전체",
        sort: "id"
    })
    const observerRef = useRef(null);
    
    const getData = async ()=>{
        let params = {
            page:page,
            size:8,
            bookshelf:filter.bookshelf,
            sort:filter.orderBy
        }
        await getBookListPageing(params).then((res) => {
            if(res.last) {setLast(true);}
            setData([...data, ...res.content]);
            setPage(page=>page+1);
            console.log(res);
            
        }).catch(() => {
            console.log("error");
        })
    }

    let observer;
    useEffect(() => {
        observer = new IntersectionObserver(callbackFunction, {
            threshold: 1, //40%만 보여도 작동함
        });
        if (observerRef.current) observer.observe(observerRef.current); //타겟등록
        return () => {
            if (observerRef.current) observerRef.current.disconnect();
        }
    }, [observerRef])


    const callbackFunction = ([entry], observer) => {
        if(entry.isIntersecting)
        {   
             setIsLoading(true);
        }
      };

      useEffect(()=>{                    
            if(page==0){
                getData();
            }
      },[page])

      useEffect(()=>{
        setIsLoading(false);
      },[data])

      useEffect(()=>{
        if(isLoading&&!last&&page>0){
            getData();    
        }
      },[isLoading])
      
    const setBooksehlf = async(str) => {
        setFilter({ ...filter, bookshelf: str });
        dataChange();
    }
    const setOrderBy = (str) => {
        setFilter({ ...filter, orderBy: str });
        dataChange();
    }

    const dataChange=()=>{
        setPage(0);
        setData([]);
        setLast(false);
    }

    return (
        <>
            <Title mainTxt="책장별" subTxt="도서목록" setFilter={setFilter}></Title>
            <Tab setBooksehlf={setBooksehlf}></Tab>
            <ViewType setOrderBy={setOrderBy}></ViewType>
            {
                data.length > 0 ?
                    <ViewList data={data}></ViewList>
                    : null
            }
            <div className={styles.lodaing} ref={observerRef}>
                {!last? <Loading />:"마지막입니다."}
            </div>
        </>
    )
}

export default BookMain