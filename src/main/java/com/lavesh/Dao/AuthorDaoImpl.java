package com.lavesh.Dao;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.lavesh.entities.Author;
import com.lavesh.repository.AuthorRepository;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final AuthorRepository authorRepository;

	public AuthorDaoImpl(AuthorRepository authorRepository) {
		super();
		this.authorRepository = authorRepository;
	}

	@Override
	public Author getById(String id) {
		return authorRepository.findById(id).orElseThrow(() -> new RuntimeException(id + " not found"));
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		return authorRepository.findAuthorByFirstNameOrLastName(firstName, lastName)
				.orElseThrow(() -> new EntityNotFoundException(firstName + " " + lastName + " not found"));
	}

	@Override
	public Author saveNewAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	@Transactional
	public Author updateAuthor(Author author) {

		Author author2 = authorRepository.findById(author.getId())
				.orElseThrow(() -> new RuntimeException(author.getId() + " user not exist"));

		BeanUtils.copyProperties(author, author2);

		Author author3 = authorRepository.save(author2);
		return author3;

	}

	@Override
	public void deleteAuthorById(String id) {

		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(id + " not present in db"));

		authorRepository.delete(author);
	}

}
