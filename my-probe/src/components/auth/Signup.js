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
  SuccessMessage,
} from '../common/TerminalStyles';

const Signup = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (formData.password !== formData.confirmPassword) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }

    try {
      await axios.post('/api/users/signup', {
        username: formData.username,
        email: formData.email,
        password: formData.password,
      });

      setSuccess('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다...');
      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } catch (err) {
      setError(err.response?.data?.message || '회원가입에 실패했습니다.');
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
        <TerminalPrompt>Welcome to Probe - Sign Up</TerminalPrompt>
        <form onSubmit={handleSubmit}>
          <div>
            <TerminalPrompt>$ enter username:</TerminalPrompt>
            <TerminalInput
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              placeholder="username"
            />
          </div>
          <div>
            <TerminalPrompt>$ enter email:</TerminalPrompt>
            <TerminalInput
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="email"
            />
          </div>
          <div>
            <TerminalPrompt>$ enter password:</TerminalPrompt>
            <TerminalInput
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="password"
            />
          </div>
          <div>
            <TerminalPrompt>$ confirm password:</TerminalPrompt>
            <TerminalInput
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              placeholder="confirm password"
            />
          </div>
          {error && <ErrorMessage>{error}</ErrorMessage>}
          {success && <SuccessMessage>{success}</SuccessMessage>}
          <TerminalButton2 type="submit">SIGN UP</TerminalButton2>
        </form>
        <div style={{ marginTop: '20px', color: '#666' }}>
          Already have an account? <Link to="/login" style={{ color: '#00FF00' }}>Login here</Link>
        </div>
      </TerminalBody>
    </TerminalWindow>
  );
};

export default Signup; 