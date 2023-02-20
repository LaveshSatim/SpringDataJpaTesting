package com.lavesh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.lavesh.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {

	Optional<Author> findAuthorByFirstNameOrLastName(String firstName, String lastName);

	@Query("SELECT b From Author b WHERE b.firstName =?1")
	Author findqureyforauthername(String firstname);

	@Query("SELECT b From Author b WHERE b.firstName =:firstname")
	Author findqureyforauthernamenamed(@Param("firstname") String firstname);

	@Query(value = "SELECT * FROM author WHERE first_name = :firstname", nativeQuery = true)
	Author findqureyforauthernameNATIVE(@Param("firstname") String firstname);

	@Query(value = "SELECT * FROM author", nativeQuery = true)
	Optional<List<Author>> findAlll();

	Author jpanamed(@Param("firstname") String firstname);
}
