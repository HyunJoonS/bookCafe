import axios from 'axios';
import styles from 'css/admin/modal.module.scss';
import { useEffect, useState } from 'react';
function BookApiModal({ data, clearData, setData, searchQuery, ChangeSelectData }) {
    const [target, setTarget] = useState(null);
    let [isLoaded, setIsLoaded] = useState(false);
    let [page, setPage] = useState(0);
    let observer;

    let getBookDataApi = async () => {
        console.log("함수실행");
        try {
            let response = await axios.get("/bookSearch", {
                params: {
                    query: searchQuery,
                    display: 10,
                    start: (page * 10) + 1
                }
            })
            if (data) {
                console.log(response.data);
                console.log([...data, ...response.data.items]);
                setData([...data, ...response.data.items]);
            }
            else {
                setData([...response.data.items]);
            }

        } catch {
            alert("서버와의 연결이 원할하지 않습니다.");
            clearData();
        }

    }



    const onIntersect = ([entry], observer) => {
        console.log("감시되었음1");
        if (entry.isIntersecting && isLoaded) {
            setPage((page => page + 1));

            console.log("page:",page);
        }
    };
    useEffect(() => {
        if (target) {
            observer = new IntersectionObserver(onIntersect, {
                threshold: 0.4, //40%만 보여도 작동함
            });
            observer.observe(target); //타겟등록
        }
        return () => observer && observer.disconnect();
    }, [target]);

    useEffect(() => {
        setIsLoaded(false);
    }, [data])

    useEffect(() => {
        setIsLoaded(true);
        console.log(page);
        getBookDataApi(page);
    }, [page])
    if (data) {
        return (
            <div className={styles.wrap}>
                <div className={styles.contentBoxWrap}>
                    <button className={styles.Xbtn} onClick={clearData}>닫기</button>
                    <div className={styles.contentBox}>
                        <table>
                            <colgroup>
                                <col></col>
                                <col style={{ width: '110px' }}></col>
                                <col style={{ width: '70px' }}></col>
                                <col style={{ width: '70px' }}></col>
                                <col style={{ width: '300px' }}></col>
                                <col style={{ width: '100px' }}></col>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th onClick={() => { setIsLoaded(false) }}>제목</th>
                                    <th>저자</th>
                                    <th>출판</th>
                                    <th>출간</th>
                                    <th>줄거리</th>
                                    <th>이미지</th>
                                </tr>
                            </thead>
                            <tbody>

                                {data.map((app) => {
                                    return (
                                        <tr onClick={() => { ChangeSelectData(app) }}>
                                            <td>{app.title}</td>
                                            <td>{app.author}</td>
                                            <td>{app.publisher}</td>
                                            <td>{app.pubdate}</td>
                                            <td>{app.description}</td>
                                            <td><img src={app.image}></img></td>
                                        </tr>
                                    )
                                })}
                            </tbody>
                        </table>
                        <div ref={setTarget}>
                            {isLoaded ? <>로딩중</>
                                : data.length > 0 ? <>마지막 입니다.</>
                                    : <>검색결과가 없습니다.</>}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default BookApiModal;