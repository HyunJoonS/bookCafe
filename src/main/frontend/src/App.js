import logo from './logo.svg';
import './App.css';
import { Route, Routes, useNavigate, Outlet } from 'react-router-dom'
import Member_register from './web/order/register'
import Login from './web/Login'
import Navbar from './web/navbar'
import MenuList from './web/order/menuList';
import Home from './web/order/Home';
import BookMain from './web/book/bookMain';
import BookDetail from './web/book/detail';
import BookSeach from './web/book/search';
import AdminPage from './web/admin/adminPage';
import OpenerDetour from 'web/order/openerDetour';


import Chat from 'web/Chat';
import { configureStore } from "@reduxjs/toolkit";
import { Provider, useSelcetor, useDispathc, connect } from 'react-redux';

function reducer(currentState, action) {
  if (currentState === undefined) {
    return {
      newOrder: 0,
    }
  }

  const newState = { ...currentState };

  if(action.type === 'plus'){
    newState.newOrder +=1;
  }
  if(action.type ==='init'){
    newState.newOrder = 0;
  }

  return newState;

}
const store = configureStore({ reducer });
function App() {
  return (
    <>
    <div className='App'>

    
      <Provider store={store}>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/book" element={<BookMain />} />
          <Route path="/book/search" element={<BookSeach />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Member_register />} />
          <Route path="/book/detail/:id" element={<BookDetail />} />
          <Route path="/adminpage" element={<AdminPage />} />
          <Route path="/menu" element={<MenuList />} />
          <Route path="/openerdetour/:id" element={<OpenerDetour />} />
          <Route path="/chat" element={<Chat />} />
        </Routes>
      </Provider>
      </div>
    </>
  );
}

export default App;
