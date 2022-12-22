import { FaAngleLeft, FaAngleRight } from "react-icons/fa";
import { useEffect, useState } from "react";
import {  useNavigate } from "react-router-dom";
import styles from 'css/book/component/CardListSlide.module.scss'

function CardListSlide(props) {
    let history = useNavigate();
    let poster_url = 'https://image.tmdb.org/t/p/w500'
    let [popular, setPopular] = useState(); // 20개 목록 가져오기
    const btnSlideLeft = <FaAngleLeft/>
    const btnSlideRight = <FaAngleRight/>
    useEffect(() => {
       setPopular(props.state);
    }, [props.state])
    let [slide, setslide] = useState({ //슬라이드 관련
        maxpage: 3,
        width: 1120,
        page: 0,
        style: { transform: 'translateX(0px)' }
    });
    function Slide(action) {
        const slideWidth = -slide.width;
        let slidepage = slide.page;
        if (action.type == 'left') {
            if (slide.page > 0) {
                slidepage--;
                setslide({
                    ...slide,
                    page: slidepage,
                    style: { transform: 'translateX(' + slideWidth * slidepage + 'px)' }
                });
            }
        }
        else if (action.type == 'right') {
            if (slide.page < slide.maxpage) {
                slidepage++;
                setslide({
                    ...slide,
                    page: slidepage,
                    style: { transform: 'translateX(' + slideWidth * slidepage + 'px)' }
                });
            }
        }
        console.log(slidepage);
    }
    return (
        <div className={styles.cardlistslide}>
        <div className={styles.ranking_wrapper}>
        <div className={styles.maintitle}>{props.title}</div>
            <div className={styles._wrapper}>
                <ul className={styles.ranking} style={slide.style}>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                </ul>
            </div>
            <div>
            <div className={[styles.slide,styles.slide_left].join(' ')} style={slide.page == 0 ? { display: 'none' } : null} onClick={() => {
                Slide({ type: 'left' });
            }}>{btnSlideLeft}</div>
            </div>
            <div>
            <div className={[styles.slide,styles.slide_right].join(' ')} style={slide.page == slide.maxpage ? { display: 'none' } : null} onClick={() => {
                Slide({ type: 'right' });
            }}>{btnSlideRight}</div>
            </div>
        </div>
        </div>
    )
}
function Card(){
    return(
        <li className={styles.ranking_content} >
                <div className={styles.ranking_content_poster}>
                    <div className={styles.defult}>
                        <img src='./img/noimage.png'></img>
                    <div className={styles.number}>1</div>
                    <div className={[styles.point, styles.blurEffect].join(' ')}>평점 <b>123</b></div>
                </div>
                <div className={styles.info}>
                    <p>입력된 내용이 없습니다. dkdldndkasjdlkja</p>
                </div>
            </div>
            <div className={styles.title}><h4>괴짜가족</h4></div>
        </li>
    )
}

export default CardListSlide;