package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void testGet() {
		List<Question> all = this.questionRepository.findAll();
		all.forEach(q -> log.info(q.getSubject()));
	}

	@Test
	void testFindById() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			log.info(q.getSubject());
		}
	}

	@Test
	void testFindBySubject() {
		// Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		// log.info(q.getId());
		List<Question> qList = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		qList.forEach(q -> log.info(q));
	}

	@Test
	void testFindSubAndContent() {
		// Question q = this.questionRepository.findBySubjectAndContent
		// ("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		// log.info(q.getId());
		List<Question> qList = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		qList.forEach(q -> log.info(q));
	}

	@Test
	void testFindSubjectLike() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		qList.forEach(q -> log.info(q));
	}

	@Test
	void testUpdateSubject() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testDeleteById() {
		log.info(this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		log.info(this.questionRepository.count());
	}

	@Test
	void testInsertAnswer(){
		// Question의 자식으로 등록하기 위해 Question을 가져온다.
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());		// 잘 가져왔는지, 없으면 테스트 에러 발생
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);		// q가 부모
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void testSelectAnswer(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		Question q = a.getQuestion();

		log.info(a);
		log.info(q);
	}

	@Transactional
	@Test
	void testSelectQuestion(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		log.info(q);
		answerList.forEach(a->log.info(a));
	}
}