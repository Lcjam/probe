import styled from '@emotion/styled';

export const TerminalWindow = styled.div`
  background-color: #2D2D2D;
  border-radius: 6px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.3);
  width: 600px;
  margin: 50px auto;
  overflow: hidden;
`;

export const TerminalHeader = styled.div`
  background: #E0E0E0;
  height: 30px;
  padding: 0 8px;
  display: flex;
  align-items: center;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
`;

export const TerminalButton = styled.div`
  width: 12px;
  height: 12px;
  margin-right: 8px;
  border-radius: 50%;
  background-color: ${props => props.color};
`;

export const TerminalBody = styled.div`
  padding: 20px;
  color: #FFFFFF;
  font-family: 'Menlo', 'Monaco', 'Courier New', monospace;
`;

export const TerminalInput = styled.input`
  background-color: transparent;
  border: none;
  border-bottom: 1px solid #4D4D4D;
  color: #FFFFFF;
  font-family: 'Menlo', 'Monaco', 'Courier New', monospace;
  font-size: 14px;
  width: 100%;
  padding: 8px 0;
  margin: 8px 0;
  outline: none;

  &:focus {
    border-bottom-color: #00FF00;
  }
`;

export const TerminalButton2 = styled.button`
  background-color: #4D4D4D;
  color: #FFFFFF;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  font-family: 'Menlo', 'Monaco', 'Courier New', monospace;
  font-size: 14px;
  cursor: pointer;
  margin-top: 16px;

  &:hover {
    background-color: #666666;
  }
`;

export const TerminalPrompt = styled.div`
  color: #00FF00;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 8px;

  &:before {
    content: '>';
    color: #00FF00;
  }
`;

export const ErrorMessage = styled.div`
  color: #FF0000;
  margin-top: 8px;
  font-size: 12px;
`;

export const SuccessMessage = styled.div`
  color: #00FF00;
  margin-top: 8px;
  font-size: 12px;
`; 