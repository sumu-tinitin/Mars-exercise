package com.app.demo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.app.demo.domain.Person;

@Repository
public class PersonDaoImpl implements PersonDao {

	private final JdbcTemplate jdbcTemplate;

	public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Person person) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(PersonQueries.SQL_INSERT_PERSON,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, person.getFirstName());
			ps.setString(2, person.getLastName());
			return ps;
		}, holder);

		person.setId(holder.getKey().longValue());
	}

	@Override
	public void update(Person person) {
		jdbcTemplate.update(PersonQueries.SQL_UPDATE_PERSON, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, person.getFirstName());
				ps.setString(2, person.getLastName());
				ps.setLong(3, person.getId());
			}
		});
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(PersonQueries.SQL_DELETE_PERSON, id);
	}

	@Override
	public List<Person> getAll() {
		return jdbcTemplate.query(PersonQueries.SQL_GET_ALL_PERSONS, (rs, rowNum) -> {
			Person p = new Person();
			p.setId(rs.getLong(1));
			p.setFirstName(rs.getString(2));
			p.setLastName(rs.getString(3));
			return p;
		});
	}

	@Override
	public Person findById(Long id) {
		List<Person> list = jdbcTemplate.query(PersonQueries.SQL_FIND_PERSON_BY_ID, new Object[] { id }, (rs, rowNum) -> {
			Person p = new Person();
			p.setId(rs.getLong(1));
			p.setFirstName(rs.getString(2));
			p.setLastName(rs.getString(3));
			return p;
		});
		return (list.isEmpty() ? null : list.get(0));
	}

	@Override
	public int count() {
		return jdbcTemplate.queryForObject(PersonQueries.SQL_COUNT_PERSON, Integer.class);
	}

}
