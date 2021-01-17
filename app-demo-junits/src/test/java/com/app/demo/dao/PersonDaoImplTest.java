package com.app.demo.dao;

import org.junit.Assert;
import org.junit.Test;

public class PersonDaoImplTest {

	@Test
	public void test() {
		String str = "jitendra";
		String upperCase = str.toUpperCase();
		Assert.assertEquals("JITENDR", upperCase);
	}

}
