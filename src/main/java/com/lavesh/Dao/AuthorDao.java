package com.lavesh.Dao;

import com.lavesh.entities.Author;

public interface AuthorDao {

	Author getById(String id);

	Author findAuthorByName(String firstName, String lastName);

	Author saveNewAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(String id);
}
