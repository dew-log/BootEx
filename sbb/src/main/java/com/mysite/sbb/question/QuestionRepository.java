package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    // 쿼리 메서드
    // Question findBySubject(String subject);
    // Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubject(String subject);

    List<Question> findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}