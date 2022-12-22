import styles from 'css/book/component/Banner.module.scss'
import { useEffect, useState } from 'react';
function Banner() {
    let [viewBanner, setViewBanner] = useState(0);

    useEffect(() => {
        // setInterval 삽입하여 2.5초마다 setCount 실행
            let temp = 0;
            const timer = setInterval(() => {
                if(temp<2){
                    temp++;
                    setViewBanner(temp);
                }
                else{
                    setViewBanner(0);
                    temp=0;
                }
            }, 5000);
        
        // clear를 안 해주어도 로직에는 문제가 없으나, clear가 없다면 React의 strict mode 때문에 초반 두 번 마운트 될 때, setInterval
        // 2개가 등록되어 count가 2씩 증가하는 문제가 발생한다.
        // 물론 그 문제가 없더라도 clear를 해주는 것이 정상적이다.
        // react strict mode mounting twice 키워드로 검색 권장
            return () => {
              clearInterval(timer);
            };
          }, []);
    const style = {
        transform: `translateX(-${viewBanner * 100}%)`
    }
    const styleTab = {
        backgroundColor : 'white'
    }
    const tabValue = (e)=>{
        switch(e){
            case viewBanner:
                return styleTab;
            default:
                break;
        }
    }
    return (
        <div className={styles.outsideWrap}>
            <div className={styles.wrap} style={style}>
                <ul>
                    <li >
                        <img src='./img/banner/banner1.jpg'></img>
                    </li>
                    <li >
                        <img src='./img/banner/banner2.jpg'></img>
                    </li>
                    <li >
                        <img src='./img/banner/banner3.jpg'></img>
                    </li>
                </ul>
            </div>
            <div className={styles.tab}>
                <span style={tabValue(0)} onClick={() => { setViewBanner(0) }}></span>
                <span style={tabValue(1)} onClick={() => { setViewBanner(1) }}></span>
                <span style={tabValue(2)} onClick={() => { setViewBanner(2) }}></span>
            </div>
        </div>

    )
}
export default Banner;