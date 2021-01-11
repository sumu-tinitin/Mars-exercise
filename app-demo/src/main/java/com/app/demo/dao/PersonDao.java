package com.app.demo.dao;

import com.app.demo.domain.Person;

import java.util.List;

public interface PersonDao {

	void add(Person person);

	void update(Person person);

	void delete(Long id);

	List<Person> getAll();

	Person findById(Long id);

	int count();

}
