package jpa.bookCafe.휴지통;

import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import jpa.bookCafe.domain.enumStatus.BookStatus;
import jpa.bookCafe.repository.BookRepository;
import jpa.bookCafe.repository.BookshelfRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class bookServiceTest2 {
    
    @Autowired BookRepository bookRepository;
    @Autowired BookshelfRepository bookshelfRepository;


    @BeforeAll //딱한번만 실행
    public void beforeAll() throws Exception{
        Bookshelf 가 = bookshelfRepository.save(new Bookshelf("가"));
        Bookshelf 나 = bookshelfRepository.save(new Bookshelf("나"));
        Bookshelf 다 = bookshelfRepository.save(new Bookshelf("다"));
        Bookshelf 라 = bookshelfRepository.save(new Bookshelf("라"));
        Bookshelf 마 = bookshelfRepository.save(new Bookshelf("마"));
        Bookshelf 바 = bookshelfRepository.save(new Bookshelf("바"));
        Bookshelf 사 = bookshelfRepository.save(new Bookshelf("사"));
        Bookshelf 아 = bookshelfRepository.save(new Bookshelf("아"));

        Book 나루토 = new Book("나루토", "키시모토 마사시", "『나루토』 여기는 나뭇잎 마을. 닌자 학교의 문제아 나루토는 오늘도 장난질에 열중이다!! 그런 나루토의 꿈은 역대의 용사. 호카게의 이름을 물려 받아 그 누구보다 뛰어난 닌자가 되는 것. 하지만 나루토에겐 출생의 비밀이?!",
                "https://shopping-phinf.pstatic.net/main_3248605/32486053939.20221019105128.jpg",
                "20000919", "대원씨아이", 72, 가, BookStatus.O);
        Book 원피스 = new Book(
                "원피스",
                "오다 에이치로",
                "유쾌한 해적들의 신나는 모험 이야기를 그린 만화『원피스』. 해적왕이라 불리웠던 ‘G 로저’가 남긴 보물 중의 보물 ‘원피스’를 둘러싸고 펼쳐지는 대해적 시대. 악마의 열매를 먹어버린 소년 루피는 미래의 해적왕을 꿈꾸며 동지를 모으기 위해 위대한 항해를 시작한다.",
                "https://shopping-phinf.pstatic.net/main_3248195/32481952724.20221019101937.jpg",
                "20211005",
                "대원씨아이",
                103, 가,BookStatus.X);
        Book 데스노트 = new Book(
                "데스노트",
                "Tsugumi Ohba",
                "만화 『데스노트』. 사신 류크가 인간계에 떨어뜨린 한 권의 'Death Note'로 인해 두 명의 선택받은 자 '야가미 라이토'와 'L'의 두뇌싸움이 벌어진다.",
                "https://shopping-phinf.pstatic.net/main_3249036/32490368722.20220803181627.jpg",
                "20110906",
                "대원씨아이",
                12, 가,BookStatus.O);
        Book 원조괴짜가족 = new Book(
                "원조 괴짜가족",
                "Kenji Hamaoka",
                "최강엽기 개그만화 『원조괴짜가족』. 엽기적 괴짜 가족의 소소한 일상을 유쾌하게 표현한 만화다. 엽기적 말과 행동이야말로 웃음의 코드다. 등장하는 캐릭터마다 오버액션이 재미나다.",
                "https://shopping-phinf.pstatic.net/main_3246655/32466550820.20220530154253.jpg",
                "20110530",
                "서울미디어코믹스(서울문화사)",
                28, 가,BookStatus.O);
        Book 블리치 = new Book(
                "블리치",
                "쿠보 타이토",
                "TITE KUBO의 만화 『블리치』. '쿠로사키 이치고'는 유령을 볼 수 있는 15세의 남자다. 특수한 체질을 가지고도 평범한 생활을 하던 이치고에게 갑자기 스스로를 사신이라고 소개하는 소녀 '쿠치키 루키아'가 나타난다. 유아울러 산 사람, 죽은 사람 안 가리고 공격해서 영혼을 먹어치우는 나쁜 혼령 '호로'도 모습을 드러낸다. 그런데 호로로 인해 이치고의 가족이 쓰러져가는데…….",
                "https://shopping-phinf.pstatic.net/main_3250471/32504717628.20221019123328.jpg",
                "20101125",
                "서울미디어코믹스(서울문화사)",
                74, 가,BookStatus.O);
        Book 미스터초밥왕 = new Book(
                "미스터 초밥왕",
                "Daisuke Terasawa",
                "『미스터 초밥왕』(애장판). 옛날에는 유명한 초밥 요리사였던 쇼타의 아버지 겐지. 그러나 사사 초밥의 방해로 좋은 재료를 구할 수 없게 된 데다 어머니마저 잃자 완전히 의욕을 상실한다. 쇼타는 초밥 콘테스트에 나갈 것을 권하지만 또다시 사사 초밥의 방해로 아버지는 부상을 입는다. 이에 쇼타는 대신 출장하기로 결심하는데...",
                "https://shopping-phinf.pstatic.net/main_3249221/32492210893.20220524184052.jpg",
                "20110127",
                "학산문화사",
                14, 가,BookStatus.O);
        Book 명탐정코난 = new Book(
                "명탐정 코난",
                "아오야마 고쇼",
                "본격 탐정 만화『명탐정 코난』제1권. 셜록 홈즈를 능가하는 추리력으로, 모든 사건들에서 대활약을 벌이는 고교생 명탐정 신이치! 하지만 어느 날, 사건을 뒤쫓던 그는 이상한 약의 힘으로 갑자기 어린아이가 되어버리는데…?! 신이치를 대신할 이름은 바로 \"코난\"! 꼬마 명탐정이 등장한다!!",
                "https://shopping-phinf.pstatic.net/main_3249662/32496626950.20220530153527.jpg",
                "20100925",
                "서울미디어코믹스(서울문화사)",
                101, 가,BookStatus.X);
        Book 북두의권 = new Book(
                "북두의 권",
                "Buronson",
                "세기말 구세주 전설! 그 신화가 부활한다!\n" +
                        "세계는 핵의 화염에 휩싸여 멸망하고 폭력만이 지배하는 황폐한 시대가 되었다.\n" +
                        "바다는 마르고, 땅은 갈라지고… 모든 생명체가 멸종한 것처럼 보였다.\n" +
                        "하지만, 인류는 멸망하지 않았다!\n" +
                        "북두신권의 전수자 켄시로는 린, 바트와 함께 폭정을 펴는 제국에 맞서 싸운다.",
                "https://shopping-phinf.pstatic.net/main_3248507/32485078635.20221019134342.jpg",
                "20180710",
                "학산문화사",
                24, 가,BookStatus.O);
        Book 도쿄구울 = new Book(
                "도쿄 구울",
                "Sui Ishida",
                "Sui Ishida의 만화『도쿄 구울』제1권. 먹이사슬이 정점에 있는 인간, 그 중에서도 가장 상위에 위치한 최정점의 존재-인간을 먹이로 삼는 그들의 이름은 ‘구울’. 인간과바로 그 ‘구울’이 뒤섞여사는 세상에서 대학생 카네키는 그 존재를 실감하지 못한 채 평범한 나날을 보내고 있었다. 죽을 목숨이었으나 놀랍게도 살아난 카네키. 그날부터 그는 ‘반 인간, 반 구울’ 상태의 돌연변이가 되어 인간의 날고기 외에는 식욕을 느끼지 못하고 모두 토해버리는 끔찍한 괴물이 되어버리는데...",
                "https://shopping-phinf.pstatic.net/main_3248471/32484716901.20220527023451.jpg",
                "20140130",
                "대원씨아이",
                14, 가,BookStatus.O);
        Book 레이브 = new Book(
                "레이브",
                "마시마 히로",
                "Hiro Mashima의 만화 『레이브』 제1권. 빛의 돌 '레이브'와 어둠의 돌 '다크블링'을 둘러싸고 펼쳐지는 하루와 푸르의 퓨전 판타지 액션 만화다. 이미 예고된 빛과 어둠의 숙명적인 대결 속에서 세계 평화를 되찾기 위해 거대한 힘에 도전하는 하루와 푸르, 그리고 친구들의 대결이 흥미진진하게 펼쳐진다.",
                "https://shopping-phinf.pstatic.net/main_3250543/32505433011.20220521134111.jpg",
                "20120320",
                "학산문화사",
                35, 가,BookStatus.O);
        Book 유유백서 = new Book(
                "유유백서 (완전판)",
                "Yoshihiro Togashi",
                "토가시 요시히로의 만화 『유유백서』 제1권. 평소에 싸움만 일삼으며 살던 불량소년 우라메시 유스케, 그는 어느 날 차에 치일 뻔한 어린아이를 구하다가 자기가 치여 죽고 만다. 그 때 저승에서 나타난 영계 안내인 보탄은 유스케에게 영계 탐정이란 임무를 맡게 되면 다시 살려주겠다는 조건을 내거는데….",
                "https://shopping-phinf.pstatic.net/main_3244103/32441032616.20220527055533.jpg",
                "20130830",
                "대원씨아이",
                15, 나,BookStatus.O);
        Book 베르세르크 = new Book(
                "베르세르크",
                "미우라 켄타로",
                "판타지 만화 『베르세르크』 제1권. 거대한 검, 철로 된 의수, 그리고 전신에 검은 망토를 두른 사나이 가츠. 절대절명의 순간에 그에게 도움을 받아 목숨을 건진 엘프 파크는 가츠의 뒤를 따라 나서지만 그가 가는 곳마다 달라붙는 악령의 무리로 인해 그의 주변은 붉은 핏빛으로 물든다.",
                "https://shopping-phinf.pstatic.net/main_3248038/32480380667.20220527022405.jpg",
                "20110823",
                "대원씨아이",
                41, 나,BookStatus.X);
        Book 원펀맨 = new Book(
                "원펀맨",
                "ONE",
                "ONE, 뮤라타 유수케 만화 『원펀맨(One Punch Man)』. 괴수들이 출몰하는 현대 도시. 취미로 히어로 일을 하는 사나이가 나타났다. 반짝이는 대머리에 맹한 얼굴, 다소 촌스러운 복장을 한 사이타마는 아무리 봐도 유약한 소시민처럼 보이지만 사실은 혹독한 훈련을 거쳐 비현실적인 힘을 손에 넣은 인물이다. 그 힘을 이용해 어떤 괴수나 로봇도, 심지어 외계인까지 주먹 한방으로 해결해 버리는데...",
                "https://shopping-phinf.pstatic.net/main_3243639/32436391647.20220527054506.jpg",
                "20150724",
                "대원씨아이",
                26, 나,BookStatus.X);
        Book 슬램덩크오리지널 = new Book(
                "슬램덩크 오리지널",
                "이노우에 다케히코",
                "중학교 3년 동안 50명의 여자에게 차인 강백호. 고등학생이 된 그는 문득 말을 걸어 온 여자, 채소연에게 첫눈에 반한다.\n" +
                        "\"농구 좋아하세요?\"\n" +
                        "라는 물음과 함께 꽃길이 펼쳐진다...!?",
                "https://shopping-phinf.pstatic.net/main_3243818/32438188835.20221019150109.jpg",
                "20150930",
                "대원씨아이",
                31, 나,BookStatus.O);
        Book 배가본드 = new Book(
                "배가본드",
                "이노우에 다케히코",
                "Takehiko Inoue의 만화 『배가본드』. 일본에서 최고의 무사로 회자되는 미야모토 무사시의 일대기를 그린 만화로, 인물들의 개성과 심리 묘사가 탁월하다. 특히 신화적인 인물인 미야모토 무사시의 인간적인 면모를 부각시켜 보여준다.",
                "https://shopping-phinf.pstatic.net/main_3249206/32492069164.20221019140607.jpg",
                "20130420",
                "학산문화사",
                37, 나,BookStatus.X);
        Book 나의히어로아카데미아 = new Book(
                "나의 히어로 아카데미아",
                "KOHEI HORIKOSHI",
                "Kohei Horikoshi 만화 『나의 히어로 아카데미아』. 인구의 약 8할이 \"특이체질\"=\"개성\"을 갖고 있는 초인사회로 탈바꿈한 세계.  폭발적으로 급증한 범죄와 맞서 싸우는 \"히어로\"들은 시민 모두의 동경의 대상이었다.   미도리야 이즈쿠도 그런 히어로를 꿈꾸는 중학생.  하지만 그는 히어로에게 필수적인 \"개성\"을 타고나지 않았는데--!!",
                "https://shopping-phinf.pstatic.net/main_3248715/32487152830.20221027194558.jpg",
                "20150630",
                "서울미디어코믹스(서울문화사)",
                36, 나,BookStatus.X);
        Book 진격의거인 = new Book(
                "진격의 거인",
                "이사야마 하지메",
                "거인이 모든 것을 지배하는 세계. 100여 년 전 갑작스레 나타난 거인의 먹이가 되어버린 인류는 높이 50미터에 이르는 거대한 벽을 쌓고 벽 바깥으로 나가는 자유와 맞바꿔 침략을 막고 있었다. 그러나 허울뿐인 평화는 벽을 넘어 버리는 초대형 거인의 등장으로 깨져 버리고 지옥 같은 상황에 빠지고 만다. 한편 아직 보지 못한 벽 바깥 세상에 대해 동경하던 앨런과 막강한 전투력의 보유자 미카사, 마음 약한 아르민은 병사가 되어 마을을 지켜 나가는데...",
                "https://shopping-phinf.pstatic.net/main_3246712/32467120991.20220527030742.jpg",
                "20110221",
                "학산문화사",
                36, 나,BookStatus.X);
        Book 강철의연금술사 = new Book(
                "강철의 연금술사",
                "아라카와 히로무",
                "『강철의 연금술사 완전판』. 잃어버린 모든 것을 되찾기 위해 엘릭 형제의 여행이 시작된다! 금기로 여겨지는 인체연성을 시도한 에드워드와 알폰스 형제. 그들은 금기를 범한 대가로 절망에 빠지지만 모든 것을 되찾으려 결의한다. 이들 형제의 여행이 지금 시작된다-.",
                "https://shopping-phinf.pstatic.net/main_3248718/32487186024.20221019142916.jpg",
                "20120625",
                "학산문화사",
                18, 나,BookStatus.O);
        Book 헌터X헌터 = new Book(
                "헌터 X 헌터",
                "Yoshihiro Togashi",
                "『헌터 X 헌터 신장판』. 일본 뿐 아니라 한국에서도 많은 골수팬들을 거느리고 있으며, 매번 최신 권이 나올 때마다 판매량 1위에 랭크되는 작품으로 볼수록 빠져드는 흥미진진한 스토리가 펼쳐진다.  남녀 모두에게 어필하는 매력적인 캐릭터, 그리고 천재적인 감각의 기상천외한 설정과 세계관을 엿볼 수 있다.",
                "https://shopping-phinf.pstatic.net/main_3244176/32441765764.20220527054349.jpg",
                "20130625",
                "학산문화사",
                32, 나,BookStatus.O);
        Book 귀멸의칼날 = new Book(
                "귀멸의 칼날",
                "고토게 코요하루",
                "때는 다이쇼 시대.[3] [4]숯을 파는 마음씨 착한 소년 카마도 탄지로는, 어느날 도깨비에게 가족을 몰살당한다.\n" +
                        "유일하게 살아남은 누이동생 카마도 네즈코마저도 도깨비로 변하고 마는데...\n" +
                        "절망적인 현실에 큰 타격을 입은 카마도 탄지로였지만, 동생을 인간으로 돌려놓기 위해, 가족을 죽인 도깨비를 심판하기 위해, 귀살대의 길을 가기로 결의한다.",
                "https://shopping-phinf.pstatic.net/main_3244388/32443885131.20221019145956.jpg",
                "20170921",
                "학산문화사",
                23, 나,BookStatus.O);
        Book 드래곤볼 = new Book(
                "드래곤볼 완전판",
                "토리야마 아키라",
                "『드래곤 볼 완전판』. 만화계의 전설 '드래곤 볼'이 완전판으로 복간되었다. 드래곤 볼을 찾기 위한 손오공과 그 일행의 여행을 담았다. 일본 만화 붐을 일으킨 화제작.",
                "https://shopping-phinf.pstatic.net/main_3248709/32487096628.20221019140119.jpg",
                "20041210",
                "서울미디어코믹스(서울문화사)",
                34, 나,BookStatus.O);
        Book 아따맘마 = new Book(
                "아따맘마",
                "Eiko Kera",
                "투니버스 초절정 인기 애니메이션  만화책! 귀여운 2등신 캐릭터 가족, 아리네 이야기가 흥미진진하게 펼쳐진다. 한번 읽기 시작하면 절대로 그 매력에서 벗어날 수 없다.",
                "https://shopping-phinf.pstatic.net/main_3250468/32504684410.20220521125615.jpg",
                "20040930",
                "대원씨아이",
                21, 나,BookStatus.O);
        Book 갓오브하이스쿨 = new Book(
                "갓 오브 하이스쿨",
                "박용제",
                "‘쎈’사람이 되기 위해서, 친구의 병을 치료하기 위해서, 월광검법의 대를 잇기 위해서 그리고 각자의 소원을 이루기 위해 우승만 한다면 어떤 소원이든 이뤄주겠다는 「God of Highschool」에 초대받는다. 세계 최강의 고등학생을 가린다!",
                "https://shopping-phinf.pstatic.net/main_3244163/32441632396.20221019124443.jpg",
                "20170131",
                "길찾기",
                3, 다,BookStatus.O);
        Book 헬퍼 = new Book(
                "헬퍼",
                "삭",
                "저승과 이승의 경계에서 벌어지는 기묘한 판타지!\n" +
                        "\n" +
                        "네이버 웹툰에 연재 중인 삭의 웹툰만화 『헬퍼』. 이 작품은 저승을 만난 ‘장광남’의 기괴한 이야기를 명확한 흑과 백의 대조를 통해 세련되게 그려낸 것이다. 도시를 지키는 가드 트라이브 ‘킬베로스’의 대장인 장광남은 여자친구를 데리러 가던 길에 의문의 교통사고로 죽음을 당한다. 정신을 차려보니 이미 저승에 떨어진 신세이며, 게다가 팔목에는 이상한 검은 팔찌가 채워져 있었다. 저승인지 이승인지도 구분이 안 가는 기묘한 공간에 맨몸으로 떨어져 혼란스러운 가운데, 마침 그의 앞에 저승사자가 나타난다. 저승사자는 그를 지옥으로 인도하려 하지만 그 사실을 납득할 수 없는 광남은 저승사자를 한방에 쓰러뜨리고 마는데…….",
                "https://shopping-phinf.pstatic.net/main_3250509/32505098742.20220527054423.jpg",
                "20120303",
                "애니북스",
                18, 다,BookStatus.O);
        Book 가우스전자 = new Book(
                "가우스 전자",
                "곽백수",
                "굴지의 인재 집합소에서 펼쳐지는 유쾌한 오피스 라이프!\n" +
                        "\n" +
                        "‘네이버 만화’에 연재 중인 곽백수의 만화 『가우스 전자』 제1권. 이 책은 세계적으로 촉망받는 가전업체 가우스 전자에서 벌어지는 웃음과 눈물을 알싸하게 그려낸 단편들을 선별하여 수록한 것이다. 지나친 성형수술로 포커페이스가 된 미녀 과장 ‘성형미’, 회사 보안시스템에게마저 존재감이 읽히지 않는 그림자 직장인 ‘나무명’, 소설가를 꿈꾸었지만 현실에 안주한 ‘김문학’, 후계자 수업을 받으려고 입사한 경쟁사 회장의 아들 ‘백마탄’, 궂은 일 마다하지 않고 최달봉 이사를 위해 일해 온 ‘위 대리’ 등 직장생활에서 흔히 만날 수 있는 인물들의 오피스 라이프를 위트 있는 에피소드로 담았다. 직장생활에 대한 기대감으로 부푼 취업준비생들에게는 시원한 웃음을, 직장인들에게는 큰 공감을 가져다준다.",
                "https://shopping-phinf.pstatic.net/main_3249274/32492749119.20220530153922.jpg",
                "20120507",
                "중앙북스",
                3, 다,BookStatus.O);
        Book 치즈인더트랩시즌1 = new Book(
                "치즈인더트랩 시즌1",
                "순끼",
                "캠퍼스, 심리, 로맨스, 드라마, 코미디, 미스터리, 옴므파탈 심지어 사이코까지. 하나의 만화에 이렇게 많은 단어를 떠올리게 할 수 있는 작품은 좀처럼 만나기 쉽지 않다. 인간의 심리를 파고드는 고난도 연출과 치밀한 계산에 의해 흘러나오는 대사, 그리고 유연하게 만들어지는 각가지의 상황이 어우러지면서 인기를 끌기 시작한 이 작품은 완벽한 남녀 캐릭터 구성과 치밀한 연출, 공감을 불러일으키는 다양한 상황을 캠퍼스를 무대로 삼아 독자들에게 최고의 쾌감을 선사하는 작품이다. \n",
                "https://shopping-phinf.pstatic.net/main_3246696/32466965550.20220530155514.jpg",
                "20120302",
                "재미주의",
                6, 다,BookStatus.O);
        Book 치즈인더트랩시즌2 = new Book(
                "치즈인더트랩 시즌2",
                "순끼",
                "캠퍼스, 심리, 로맨스, 드라마, 코미디, 미스터리, 옴므파탈 심지어 사이코까지. 하나의 만화에 이렇게 많은 단어를 떠올리게 할 수 있는 작품은 좀처럼 만나기 쉽지 않다. 인간의 심리를 파고드는 고난도 연출과 치밀한 계산에 의해 흘러나오는 대사, 그리고 유연하게 만들어지는 각가지의 상황이 어우러지면서 인기를 끌기 시작한 이 작품은 완벽한 남녀 캐릭터 구성과 치밀한 연출, 공감을 불러일으키는 다양한 상황을 캠퍼스를 무대로 삼아 독자들에게 최고의 쾌감을 선사하는 작품이다. \n",
                "https://shopping-phinf.pstatic.net/main_3243633/32436333992.20220530153222.jpg",
                "20130610",
                "재미주의",
                9, 다,BookStatus.O);
        Book 치즈인더트랩시즌3 = new Book(
                "치즈인더트랩 시즌3",
                "순끼",
                "캠퍼스, 심리, 로맨스, 드라마, 코미디, 미스터리, 옴므파탈 심지어 사이코까지. 하나의 만화에 이렇게 많은 단어를 떠올리게 할 수 있는 작품은 좀처럼 만나기 쉽지 않다. 인간의 심리를 파고드는 고난도 연출과 치밀한 계산에 의해 흘러나오는 대사, 그리고 유연하게 만들어지는 각가지의 상황이 어우러지면서 인기를 끌기 시작한 이 작품은 완벽한 남녀 캐릭터 구성과 치밀한 연출, 공감을 불러일으키는 다양한 상황을 캠퍼스를 무대로 삼아 독자들에게 최고의 쾌감을 선사하는 작품이다. \n",
                "https://shopping-phinf.pstatic.net/main_3248963/32489636621.20220527041407.jpg",
                "20160128",
                "바이브릿지",
                12, 다,BookStatus.O);
        Book 치즈인더트랩시즌4 = new Book(
                "치즈인더트랩 시즌4",
                "순끼",
                "캠퍼스, 심리, 로맨스, 드라마, 코미디, 미스터리, 옴므파탈 심지어 사이코까지. 하나의 만화에 이렇게 많은 단어를 떠올리게 할 수 있는 작품은 좀처럼 만나기 쉽지 않다. 인간의 심리를 파고드는 고난도 연출과 치밀한 계산에 의해 흘러나오는 대사, 그리고 유연하게 만들어지는 각가지의 상황이 어우러지면서 인기를 끌기 시작한 이 작품은 완벽한 남녀 캐릭터 구성과 치밀한 연출, 공감을 불러일으키는 다양한 상황을 캠퍼스를 무대로 삼아 독자들에게 최고의 쾌감을 선사하는 작품이다. \n",
                "https://shopping-phinf.pstatic.net/main_3250600/32506009009.20220527040332.jpg",
                "20170831",
                "바이브릿지",
                7, 다,BookStatus.O);
        Book 무한동력 = new Book(
                "무한동력",
                "주호민",
                "젊은이들이여, 모진 현실 속에서도 꿈을 꾸자!\n" +
                        "우리 시대 젊은이들의 힘겨운 취업 도전기를 그린 만화『무한동력』제1권. 군대만화 으로 2006년 독자만화대상 신인작가상을 수상한 작가 주호민이 이번에는 청년 실업 문제를 다루었다. 불황의 시대를 살아가는 젊은이들의 희망 찾기가 펼쳐진다. 포털사이트 '야후'에서 인기리에 연재되었던 이 만화는 지금도 누리꾼들의 찬사를 받고 있다. \n" +
                        "담백한 그림체로 잔잔한 이야기들을 풀어놓지만, 그 이면에는 가슴 아픈 현실이 감춰져 있다. 경영학과 4학년에 다니고 있는 27살의 대학생 장선재. 그의 목표는 번듯한 대기업에 들어가서 시원한 연봉을 받는 것이다. 취업을 하기 위해 토익점수에 목을 매고, 여기저기 원서를 내느라 바쁘다. 그 나이의 다른 젊은이들도 마찬가지다. 그러나 현실은 녹록치 않다.\n" +
                        "무한동력 영구기관을 만들고 있는 하숙집에서 젊은이들은 곤궁한 삶을 살아간다. 남들 따라 공무원 시험을 준비하고 있는 하숙생 진기한, 아버지 사업이 망해 네일 아티스트로 일하고 있는 하숙생 김솔, 세상을 떠난 어머니의 빈자리를 채우고 살림을 도맡아 하는 하숙집 딸 한수자…. 그들은 서로 정을 주고받고 연애도 하면서, 내일을 위한 꿈을 꾼다.",
                "https://shopping-phinf.pstatic.net/main_3245445/32454452788.20221019114011.jpg",
                "20090728",
                "상상공방",
                2, 다,BookStatus.O);
        Book 신과함께이승편 = new Book(
                "신과 함께: 이승편",
                "주호민",
                "망자를 데려가려는 저승차사와 이를 막으려는 가택신의 싸움.\n" +
                        "그리고… 탐욕에 눈 먼 인간들의 이전투구!",
                "https://shopping-phinf.pstatic.net/main_3246112/32461128817.20221019140001.jpg",
                "20210408",
                "문학동네",
                2, 다,BookStatus.O);
        Book 신과함께저승편 = new Book(
                "신과 함께: 저승편",
                "주호민",
                "“혹시… 신(神) 아니세요?”\n" +
                        "평생 손해만 보고 살아온 평범한 소시민 김자홍과 \n" +
                        "저승 최고의 변호사 진기한,\n" +
                        "49일간의 험난한 저승재판을 돌파하다!",
                "https://shopping-phinf.pstatic.net/main_3246696/32466964574.20221019135935.jpg",
                "20200817",
                "문학동네",
                3, 다,BookStatus.O);
        Book 신과함께신화편 = new Book(
                "신과 함께: 신화편편",
                "주호민",
                "신화편은 한국 신화라는 소재를 가지고 전편인 저승편, 이승편에 등장한 캐릭터들의 과거 모습을 다룬 일종의 프리퀄이다. 그리스 로마신화나 중국 건국신화 등은 많이 알려져 있지만 정작 한국 신화에 대해서는 잘 알려져 있지 않았다. 주호민의 『신과 함께』를 통해서나마 전통적 가치관이 묻어나는 한국 신화를 알릴 수 있게 된 점은 큰 의의라 하겠다. \n" +
                        "단, 『신과 함께』 신화편은 저자의 의도에 따라 각색이 되었고, 전편들과 연결시키는 과정에서 전혀 상관이 없는 여섯 개의 신화를 하나의 세계관으로 통일하여 모두 이어지게 만들었다. ",
                "https://shopping-phinf.pstatic.net/main_3246668/32466688666.20221019142747.jpg",
                "20200615",
                "문학동네",
                3, 다,BookStatus.O);
        Book 열혈강호 = new Book(
                "열혈강호",
                "전극진",
                "전극진의 만화 『열혈강호』 제1권. 초보무사 한비광의 좌충우돌 무림기행! 할 줄 아는 것은 경공…. 그러나 학습능력은 상상초월?! 사파 최고의 인물인 천마신군의 6번째 제자 한비광과 정파 최고의 수뇌 검황의 딸 담화린이 거친 무림을 배경으로 펼쳐가는 신무협 극화! 끝없이 등장하는 무림 고수들과의 처절한 격돌! 그 감동과 재미의 세계가 끝없이 펼쳐진다!",
                "https://shopping-phinf.pstatic.net/main_3248508/32485082746.20221019150158.jpg",
                "19940520",
                "대원씨아이",
                86, 라,BookStatus.X);
        Book 미생 = new Book(
                "미생",
                "윤태호",
                "사회라는 거대한 바둑판에서 성실히 돌을 놓아가는 여정!\n" +
                        "\n" +
                        "≪이끼≫를 발간하였던 윤태호의 웹툰 만화 『미생: 아직 살아 있지 못한 자』. 이 작품은 어린 나이에 바둑을 통해 패배를 경험하였던 한 인물이 세상에 나와서 한수 한수 옮기는 발걸음을 보여주고 있다. ‘그래’는 새벽같이 일어나 혼자 바둑돌을 놓아보던 아이였다. 열한 살에 한국기원 연구생으로 들어갔고, 7년간 오직 바둑판 위의 세계에서만 살았다. 그러나 그래는 아버지가 돌아가신 후 입단에 실패한 후 아무런 준비도 없이, 도피하듯 사회로 나서게 된다. 바둑밖에 모르던 삶에서 철저히 바둑을 지운 삶으로, 차갑고 냉정하지만 혼자가 아닌 일터로, 그렇게, 기보에게 전혀 새로운 삶이 시작되었다. 하지만 바둑으로 길러진 승부사적 기질이 그래의 앞날을 밝혀준다.",
                "https://shopping-phinf.pstatic.net/main_3249206/32492069013.20220524184013.jpg",
                "20120915",
                "위즈덤하우스",
                13, 라,BookStatus.O);
        Book 킹덤 = new Book(
                "킹덤",
                "Yasuhisa Hara",
                "Yasuhisa Hara의 만화 『킹덤』. 지금까지 한 번도 통일된 적이 없는 중국은 500년의 대전쟁 시대를 겪는다. 가열찬 전란의 시대를 사는 소년 신은, 자신의 힘으로 천하에 이름을 떨칠 것을 맹세하는데…….",
                "https://shopping-phinf.pstatic.net/main_3249336/32493361226.20220530161038.jpg",
                "20120221",
                "대원씨아이",
                65, 라,BookStatus.X);
        Book 기생수 = new Book(
                "기생수",
                "이와아키 히토시",
                "어느날 지구에 대량으로 살포된 정체 모를 생명체, 그와 함께 도처에서 인간 도살 사건이 일어난다. 이 생명체들은 인간의 몸으로 파고들어와 뇌를 점령, 사람의 육체와 정신까지 집어삼킨다. 평범한 고교생 신이치의 몸에 들어온 괴생명체. 그러나 그것은 뇌까지 도달하지 못하고 신이치의 오른손에 기생하게 되는데….",
                "https://shopping-phinf.pstatic.net/main_3249758/32497589903.20220530152630.jpg",
                "19981231",
                "학산문화사",
                10, 라,BookStatus.O);
        Book 마기 = new Book(
                "마기",
                "Shinobo Ohtaka",
                "메마른 사막 한가운데에 자리 잡은, 축복받은 오아시스의 도시 우탄. 그곳은 상인들과 금은보화로 활기가 넘친다. 상인의 딸 사라사와 상인 라이라. 절친이기도 한 두 사람 앞에 작은 몸집으로 어마어마하게 음식을 먹어치우는 이상한 소년 알라딘이 나타난다. 천진난만한 얼굴을 하고 있지만 신비한 피리로 목 없는 괴물을 자유자재로 다루는 알라딘. 그는 위험천만한 ‘던전’에 잠들어있는 최고의 보물 ‘진의 금속기’를 찾고 있다고 하는데….",
                "https://shopping-phinf.pstatic.net/main_3249253/32492539239.20221019104547.jpg",
                "20110415",
                "대원씨아이",
                37, 라,BookStatus.O);
        Book 아이실드21 = new Book(
                "아이실드 21",
                "이나가키 리이치로",
                "심약한 성격이 화근이 되어 어렸을 때부터\n" +
                        "똘마니 인생을 살아온 데이몬 고교 1학년 코바야카와 세나.\n" +
                        "\n" +
                        "하지만 그 덕분에(?) 발군의 준족을 가지게 된 세나는\n" +
                        "악마 같은 사나이 히루마의 눈에 띄어 미식축구부에\n" +
                        "끌려 들어가게 되는데?!",
                "https://w.namu.la/s/1ed01e06a9ca5e405a77a743e33c6011b615809e174117a6c1b6825124280e01499fb92ae1b76476f9ea6f422ff06e1131794887866ca9c06c40c6a0b37f57596c549fe259cd3c30d95fce637ced9d6894f9db88b966926f027fe8b6c2eba89277a3a7c77ac2149f1d7368210d397be6",
                "20100622",
                "대원씨아이",
                37, 라,BookStatus.O);
        Book 하이큐 = new Book(
                "하이큐!!",
                "HARUICHI FURUDATE",
                "Haruichi Furudate의 『하이큐』 제1권. 차세대 왕도 스포츠 만화! 마침내 한국 상륙! 일본 현지 누계부수 100만부 돌파 「주간소년점프」인기작! 극강의 청춘 배구 스토리! 이것이 바로 정통 스포츠 소년만화다! 동료들과의 특별한 만남과 흘러내리는 소중한 땀, 그리고 정상을 향한 목표! 모두가 기다려 왔던 ‘점프’식 왕도 스포츠 만화!",
                "https://shopping-phinf.pstatic.net/main_3247425/32474251269.20221019143024.jpg",
                "20170822",
                "대원씨아이",
                45, 라,BookStatus.O);
        Book 주술회전 = new Book(
                "주술회전",
                "Gege Akutami",
                "주간 소년 점프 돌풍의 주역 드디어 한국 상륙!\n" +
                        "햇병아리 주술사들의 뜨거운 반란이 시작된다.\n" +
                        "\n" +
                        "경이로운 신체 능력을 가진 고등학생 이타도리 유지는 \n" +
                        "병상에 누워 있는 할아버지의 병문안을 가는 것이 일과이다.\n" +
                        "그러던 어느 날, 학교에 잠들어 있던\n" +
                        "‘주물(呪物)’의 봉인이 풀리면서 괴물이 나타나고\n" +
                        "이타도리는 미처 도망치지 못한 선배를 구하기 위해 \n" +
                        "학교 건물로 뛰어드는데?!",
                "https://shopping-phinf.pstatic.net/main_3247839/32478395862.20221019142113.jpg",
                "20190330",
                "서울미디어코믹스(서울문화사)",
                20, 마,BookStatus.X);
        Book 무직전생 = new Book(
                "무직전생",
                "리후진 나 마고노테",
                "34세 무직 동정의 니트족은 무일푼으로 집에서 쫓겨나 자기 인생이 완전히 궁지임을 깨달았다.\n" +
                        "스스로를 후회하던 순간, 그는 트럭에 치여서 어이없이 죽었다.\n" +
                        "그리고 눈을 뜬 곳은─ 바로 검과 마법의 이세계였다!!\n" +
                        "루데우스라는 이름이 붙은 아기로 다시 태어난 그는,\n" +
                        "“이번에야말로 진지하게 살아주겠어…!”라며 후회 없는 인생을 보내기로 결심한다.\n" +
                        "전생의 지식을 살린 루데우스는 순식간에 마술 재능을 꽃피우고 어린 여자아이의 가정교사를 맡게 된다. \n" +
                        "또한 에메랄드그린색 머리칼을 가진 아름다운 쿼터엘프와 만나고, 그의 새로운 인생이 움직이기 시작한다.",
                "https://shopping-phinf.pstatic.net/main_3244149/32441498630.20220527034413.jpg",
                "20150407",
                "학산문화사(라이트노벨)",
                25, 마,BookStatus.X);
        Book 아인 = new Book(
                "아인",
                "Gamon Sakurai",
                "미우라 츠이나 원작의 만화 『아인』 제1권. 17년 전, 아프리카의 전장에 죽지 않는 인간이 나타났다. 그 후, 드물게 인류에 나타나는 결코 죽지 않는 미지의 신생물을 인간은「아인(亞人)」이라 불렀다. 여름방학 직전, 한 일본인 고교생이 하교길에 교통사고를 당해 즉사. 되살아난 소년에게는 거액의 상금이 걸렸다 그리고 전 일류를 상대로 한 소년의 도피행이 시작되었다.",
                "https://shopping-phinf.pstatic.net/main_3247607/32476075696.20221019135024.jpg",
                "20140825",
                "학산문화사",
                17, 마,BookStatus.O);


        bookRepository.save(나루토);
        bookRepository.save(원피스);
        bookRepository.save(데스노트);
        bookRepository.save(원조괴짜가족);
        bookRepository.save(미스터초밥왕);
        bookRepository.save(명탐정코난);
        bookRepository.save(블리치);
        bookRepository.save(북두의권);
        bookRepository.save(도쿄구울);
        bookRepository.save(레이브);

        bookRepository.save(유유백서);
        bookRepository.save(베르세르크);
        bookRepository.save(원펀맨);
        bookRepository.save(슬램덩크오리지널);
        bookRepository.save(배가본드);
        bookRepository.save(나의히어로아카데미아);
        bookRepository.save(진격의거인);
        bookRepository.save(강철의연금술사);
        bookRepository.save(헌터X헌터);
        bookRepository.save(귀멸의칼날);
        bookRepository.save(드래곤볼);
        bookRepository.save(아따맘마);

        bookRepository.save(갓오브하이스쿨);
        bookRepository.save(헬퍼);
        bookRepository.save(가우스전자);
        bookRepository.save(치즈인더트랩시즌1);
        bookRepository.save(치즈인더트랩시즌2);
        bookRepository.save(치즈인더트랩시즌3);
        bookRepository.save(치즈인더트랩시즌4);
        bookRepository.save(무한동력);
        bookRepository.save(신과함께저승편);
        bookRepository.save(신과함께이승편);
        bookRepository.save(신과함께신화편);

        bookRepository.save(열혈강호);
        bookRepository.save(미생);
        bookRepository.save(킹덤);
        bookRepository.save(기생수);
        bookRepository.save(마기);
        bookRepository.save(아이실드21);
        bookRepository.save(하이큐);

        bookRepository.save(주술회전);
        bookRepository.save(무직전생);
        bookRepository.save(아인);
    }

    @Test
    public void findByQueryTest() throws Exception{
        List<Book> books = bookRepository.findByQuery("나루토");
        System.out.println("size=" + books.size());
        Assertions.assertThat(books.size()>0);
    }

    @Test
    public void findByBookshelfNameTest() throws Exception{
        PageRequest pageable = PageRequest.of(0, 8);
        Page<Book> 가 = bookRepository.findByBookshelfName("가",pageable);
        System.out.println("책장으로찾기 = " + 가.getTotalElements());

        가.getTotalPages();

    }
    
}
