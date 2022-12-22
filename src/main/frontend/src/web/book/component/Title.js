import styles from 'css/book/component/Title.module.scss'

function Title({mainTxt,subTxt}){
    return(
        <div>
            <h4>{mainTxt}</h4><h4 className={styles.color}>{subTxt}</h4>
        </div>
    )
}
export default Title;