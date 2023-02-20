package com.lavesh;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.lavesh.Dao.AuthorDao;
import com.lavesh.Dao.AuthorDaoImpl;
import com.lavesh.entities.Author;
import com.lavesh.repository.AuthorRepository;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan({ "com.lavesh.Dao" })
@Import({ AuthorDaoImpl.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringDatajpaFirstUpasnaApplicationTests {

	@Autowired
	AuthorDao authorDao;

	@Autowired
	AuthorRepository authorRepository;

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author newAuthor = authorDao.saveNewAuthor(author);

		assertThat(newAuthor.getFirstName()).isEqualTo("lavesh");
	}

	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		authorDao.deleteAuthorById(author2.getId());

		assertThrows(RuntimeException.class, () -> {
			Author author3 = authorDao.getById(author2.getId());
		});
	}

	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("lavesh");

		Author saved = authorDao.saveNewAuthor(author);

		saved.setFirstName("tanmay");

		Author updateAuthor = authorDao.updateAuthor(saved);

		assertEquals("tanmay", updateAuthor.getFirstName());

	}

	@Test
	void testGetByIdAuthor() {
		Author author = new Author();

		author.setFirstName("lavesh");
		author.setLastName("satim");
		Author saved = authorDao.saveNewAuthor(author);

		Author getbyid = authorDao.getById(saved.getId());

		assertThat(getbyid.getFirstName()).isEqualTo(saved.getFirstName());
	}

	@Test
	void testFindByFirstNameorLastName() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		Author author3 = authorDao.findAuthorByName("lavesh", "satim");

		assertEquals(author2.getId(), author3.getId());
	}

	@Test
	void testExceptionTestFindByFirstNameorLastName() {

		assertThrows(EntityNotFoundException.class, () -> {
			authorDao.findAuthorByName("not", "not");
		});
	}

//	@Test
//	void testForNullable() {
//		Author author = new Author();
//		author.setFirstName("lavesh");
//		author.setLastName("satim");
//		assertDoesNotThrow(() -> {
//			authorRepository.getby("Dd");
//		});
//	}

	@Test
	void testForQuery() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		Author author3 = authorRepository.findqureyforauthername("lavesh");

		assertEquals(author2.getId(), author3.getId());
	}

	@Test
	void testForQueryWithNamedParam() {
		Author author = new Author();
		author.setFirstName("lavesg");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		Author author3 = authorRepository.findqureyforauthernamenamed("lavesg");

		assertThat(author3.getId()).isEqualTo(author2.getId());
	}

	@Test
	void testNativeQuery() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");
		Author author2 = authorDao.saveNewAuthor(author);

		Author author3 = authorRepository.findqureyforauthernameNATIVE("lavesh");

		assertEquals(author2.getId(), author3.getId());
	}

	@Test
	void testForAllList() {
		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		Optional<List<Author>> findAlll = authorRepository.findAlll();

		List<Author> list = findAlll.get();

		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	void testForJpaNamedQuery() {

		Author author = new Author();
		author.setFirstName("lavesh");
		author.setLastName("satim");

		Author author2 = authorDao.saveNewAuthor(author);

		Author author3 = authorRepository.jpanamed("lavesh");

		assertEquals(author2.getId(), author3.getId());
	}
}
