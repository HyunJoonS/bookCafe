import axios from "axios"


export const getBookListPageing = async (params) => {
    let url = '/book/list';
    try {
        let res = await axios.get(url,{params})
        return res.status == 200 ? res.data : "error";
    }
    catch (error) {
        return error
    }
}

export const getBookList = async () => {
    let url = '/bookList';
    try {
        let res = await axios.get(url)
        return res.status == 200 ? res.data : "error";
    }
    catch (error) {
        return error
    }
}

export const save = async (params) => {
    try {
        let response = await axios.post("/api/book",JSON.stringify(params),{
            headers: {
                "Content-Type": `application/json`,
            },
        })
        alert("성공적으로 저장되었습니다.");
    } catch {
        alert("에러");
    }
}

