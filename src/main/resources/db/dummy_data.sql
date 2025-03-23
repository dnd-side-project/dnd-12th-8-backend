USE dnd_DB;

DELIMITER $$

DROP PROCEDURE IF EXISTS InsertDummyProjects $$
CREATE PROCEDURE InsertDummyProjects()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= 1000000 DO
    INSERT INTO project (
      member_id,
      title,
      description,
      due_date,
      target_job,
      target_level,
      is_advertised,
      logo_img_url,
      thumbnail_img_url,
      delete_yn,
      created_at,
      updated_at,
      favorite_count,
      participant_count,
      project_status,
      project_member_emails
    )
    VALUES (
      '3914122404',  -- 고정된 member_id 사용
      CONCAT('AI 추천 시스템 프로젝트 ', i),
      '이 프로젝트는 AI 기반 추천 시스템을 개발하는 프로젝트입니다.',
      DATE_ADD(NOW(), INTERVAL FLOOR(RAND() * 365) DAY), -- 랜덤 미래 날짜
      'DEVELOPER',
      'LEARNER',
      TRUE,
      'https://your-s3-bucket.s3.amazonaws.com/uploads/logo.png',
      'https://your-s3-bucket.s3.amazonaws.com/uploads/thumbnail.png',
      'N', -- delete_yn 기본값 (삭제되지 않음)
      NOW(), -- created_at 자동 입력
      NOW(), -- updated_at 자동 입력
      0, -- favorite_count 기본값
      0, -- participant_count 기본값
      1, -- project_status 기본값 (1 = OPEN)
      '[ "kim@example.com", "lee@example.com" ]' -- project_member_emails JSON 형식으로 저장
    );

    SET i = i + 1;
  END WHILE;
END $$

DELIMITER ;

CALL InsertDummyProjects();
