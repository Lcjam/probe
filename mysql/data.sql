-- 프로브 커뮤니티 초기 데이터

USE probe;

-- 관리자 계정 생성 (비밀번호: admin123)
INSERT INTO users (username, email, password, is_first_login, is_onboarding_completed)
VALUES ('admin', 'admin@probe.com', '$2a$10$1qAz2wSx3eDc4rFv5tGb5edDmJKhknMyes8bQUBOdlnfVOj/6imjK', FALSE, TRUE);

-- 샘플 프롬프트 엔지니어링 추가
INSERT INTO prompts (user_id, prompt_engineering, question, answer)
VALUES (
    1, 
    '당신은 프롬프트 엔지니어링 전문가입니다. 질문에 대해 가능한 자세하고 정확한 정보를 제공해주세요.',
    '프롬프트 엔지니어링이란 무엇인가요?',
    '프롬프트 엔지니어링은 AI 모델(특히 대형 언어 모델)에게 효과적인 지시를 제공하여 원하는 출력을 얻어내는 기술과 방법론을 의미합니다. 이는 질문이나 요청의 방식을 최적화하여 AI가 더 정확하고, 유용하며, 맥락에 맞는 응답을 생성할 수 있도록 하는 과정입니다. 좋은 프롬프트는 명확한 지시, 필요한 맥락 정보, 원하는 출력 형식 등을 포함하며, AI의 성능을 최대한 활용할 수 있게 합니다.'
);

-- 샘플 게시글 추가
INSERT INTO posts (user_id, title, content, prompt_id)
VALUES (
    1, 
    '프롬프트 엔지니어링 입문자를 위한 팁',
    '안녕하세요! 프롬프트 엔지니어링을 시작하시는 분들을 위한 몇 가지 팁을 공유합니다.\n\n1. 구체적인 지시어 사용하기\n2. 단계별로 나누어 요청하기\n3. 예시 제공하기\n4. 원하는 출력 형식 명시하기\n\n여러분의 프롬프트 엔지니어링 경험도 댓글로 공유해주세요!',
    1
);

-- 샘플 댓글 추가
INSERT INTO comments (user_id, post_id, content)
VALUES (1, 1, '추가로 제안하자면, AI의 역할을 명확히 설정해주는 것도 좋은 방법입니다. 예를 들어 "당신은 수학 교사입니다"처럼 구체적인 역할을 부여하면 더 적절한 톤과 스타일로 응답을 받을 수 있습니다.');
