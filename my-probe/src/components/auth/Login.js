import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import {
  TerminalWindow,
  TerminalHeader,
  TerminalButton,
  TerminalBody,
  TerminalInput,
  TerminalButton2,
  TerminalPrompt,
  ErrorMessage,
} from '../common/TerminalStyles';

const Login = () => {
  const [usernameOrEmail, setUsernameOrEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/auth/login', {
        usernameOrEmail,
        password,
      });

      localStorage.setItem('token', response.data.accessToken);
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || '로그인에 실패했습니다.');
    }
  };

  return (
    <TerminalWindow>
      <TerminalHeader>
        <TerminalButton color="#FF5F56" />
        <TerminalButton color="#FFBD2E" />
        <TerminalButton color="#27C93F" />
      </TerminalHeader>
      <TerminalBody>
        <TerminalPrompt>Welcome to Probe - Login</TerminalPrompt>
        <form onSubmit={handleLogin}>
          <div>
            <TerminalPrompt>$ enter username or email:</TerminalPrompt>
            <TerminalInput
              type="text"
              value={usernameOrEmail}
              onChange={(e) => setUsernameOrEmail(e.target.value)}
              placeholder="username/email"
            />
          </div>
          <div>
            <TerminalPrompt>$ enter password:</TerminalPrompt>
            <TerminalInput
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="password"
            />
          </div>
          {error && <ErrorMessage>{error}</ErrorMessage>}
          <TerminalButton2 type="submit">LOGIN</TerminalButton2>
        </form>
        <div style={{ marginTop: '20px', color: '#666' }}>
          New user? <Link to="/signup" style={{ color: '#00FF00' }}>Sign up here</Link>
        </div>
      </TerminalBody>
    </TerminalWindow>
  );
};

export default Login; 