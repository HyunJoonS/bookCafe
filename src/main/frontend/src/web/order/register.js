import { Button, Form, Container, Row, Col } from 'react-bootstrap';
import { useState } from 'react'
import axios from 'axios'

function Member_register() {
    let [userID, setUserID] = useState('')
    let [userPW, setUserPW] = useState('')

    const changeUserId = (e) => {
        setUserID(e);
    }
    const changeUserPw = (e) => {
        setUserPW(e);
    }

    const postRegister = () => {
        axios.post('/register',{
            "userId" : userID,
            "password" : userPW
        }).then(e=>console.log(e))
        .catch(e=>console.log(e));
    }

    return (
        <div className='wrap'>
            <Container>
                <Form>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>아이디</Form.Label>
                        <Form.Control type="text" placeholder="로그인 아이디"
                            onChange={(e) => { changeUserId(e.target.value); }}
                        />
                        <Form.Text className="text-muted">
                            {userID}
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control type="password" placeholder="비밀번호"
                            onChange={(e) => { changeUserPw(e.target.value); }}
                        />
                        <Form.Text className="text-muted">
                            {userPW}
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>매장 이름</Form.Label>
                        <Form.Control type="password" placeholder="매장 이름" />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>휴대폰 번호</Form.Label>
                        <Row>
                            <Col>
                                <Form.Control className='mb-3' type="password" placeholder="010" />
                            </Col>
                            <Col>
                                <Form.Control className='mb-3' type="password" placeholder="0000" />
                            </Col>
                            <Col>
                                <Form.Control className='mb-3' type="password" placeholder="0000" />
                            </Col>
                        </Row>
                    </Form.Group>
                    <Button variant="primary" onClick={postRegister}>
                        가입하기
                    </Button>
                </Form>
            </Container>
        </div>
    )
}
export default Member_register;