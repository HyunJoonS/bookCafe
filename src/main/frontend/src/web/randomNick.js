
let color = [
    "빨강","파랑","주황","노랑","초록","황토","하양","검은","야광","형광","금빛","은빛","멋있는","맛있는","이쁜","귀여운","아름다운","이상한","노래하는","작은","얼큰한","커다란"
]
let object =[
    "바나나","나무","콜라","코코넛","포도","치즈","피자","치킨","떡볶이","라면","사과","딸기","응가","사람","다람쥐","토끼","사자","원숭이","기린","고릴라","코끼리","햄스터","오징어","문어","물고기","빵"
]

export const randomNick = ()=>{
    let firstName = color[Math.floor(Math.random() * color.length)]
    let lastName = object[Math.floor(Math.random() * object.length)]
    console.log("firstName",firstName)
    console.log("lastName",lastName)
    return firstName+lastName;
}
