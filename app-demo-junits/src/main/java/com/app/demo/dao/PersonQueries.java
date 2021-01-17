package com.app.demo.dao;

public class PersonQueries {

	public static final String SQL_INSERT_PERSON = "" +
			"INSERT INTO PERSON(FIRST_NAME, LAST_NAME) " +
			" VALUES (?, ?)";
	public static final String SQL_UPDATE_PERSON = "UPDATE PERSON SET " +
			" FIRST_NAME=?, LAST_NAME=? " +
			" WHERE PERSON_ID=?";
	public static final String SQL_DELETE_PERSON = "DELETE FROM PERSON WHERE PERSON_ID=?";
	public static final String SQL_GET_ALL_PERSONS = "SELECT * FROM PERSON";
	public static final String SQL_FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE PERSON_ID = ?";
	public static final String SQL_COUNT_PERSON = "SELECT COUNT(*) FROM PERSON";

}
