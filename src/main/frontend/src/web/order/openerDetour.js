import { useEffect } from "react";
import { useParams } from "react-router-dom";

function OpenerDetour(){
    let { id } = useParams();
    const openAelertTest=()=>{
    }
    function openAlert(){
        window.opener.alert("부모창알림이니당");
    }
    function open(){
        let winObj =window.open('/cancel',"하이하이", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
        window.asdf=(a)=>{
            alert(a);
        };
    }
    function openerCodes(){
        window.opener.callbackFunction(id);
    }

    useEffect(()=>{
        openerCodes();
    },[])
    return(
        <div>
            오프너입니다.
            <button onClick={openAlert}>부모창알림</button>
            <button onClick={openAelertTest}>알림</button>
            <button onClick={open}>자식창열기</button>
            <button onClick={openerCodes}>부모창함수실행</button>
        </div>
    )
}
export default OpenerDetour;