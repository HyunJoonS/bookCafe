

import { useNavigate } from 'react-router-dom';
import { Button, Container, Form, InputGroup } from 'react-bootstrap';
function Home() {
    let navigate = useNavigate();
    return (
        <>
            <Container>
                <InputGroup className='mb-3'>
                    <InputGroup.Text>아이디</InputGroup.Text>
                    <Form.Control/>
                </InputGroup>
                <InputGroup className='mb-3'>
                    <InputGroup.Text>비밀번호</InputGroup.Text>
                    <Form.Control/>
                </InputGroup>
                <Button onClick={()=>{ navigate('/register') }}>회원가입</Button>
                <Button className='mx-1' onClick={()=>{ navigate('/register') }}>로그인</Button>
            </Container>
        </>
    )
}
export default Home;