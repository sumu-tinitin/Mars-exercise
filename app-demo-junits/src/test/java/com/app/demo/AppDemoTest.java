package com.app.demo;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.app.demo.dao.PersonDao;

public class AppDemoTest {

	@Mock
	private PersonDao personDao;
	private AppDemo appDemo;

	private final InputStream sysIn = System.in;
	private final PrintStream sysOut = System.out;

	private ByteArrayInputStream testIn;
	private ByteArrayOutputStream testOut;

	@Before
	public void initTest() {
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}

	@After
	public void restoreSystemInputOutput() {
		System.setIn(sysIn);
		System.setOut(sysOut);
	}

	private void provideInput(String data) {
		testIn = new ByteArrayInputStream(data.getBytes());
		System.setIn(testIn);
	}

	private AppDemo getAppDemoInstance(String data) {
		provideInput(data);
		return new AppDemo(personDao, testIn);
	}

	private String getOutput() {
		return testOut.toString();
	}

	@Test
	public void testInput_minLength() {
		String label = "Enter first name: ";
		String data = "ram\n"
				+ "krishna";

		appDemo = getAppDemoInstance(data);
		String actualInput = appDemo.getInput(label, 5, 10);

		assertEquals("krishna", actualInput);
	}

	@Test
	public void testInput_maxLength() {
		String label = "Enter first name: ";
		String data = "krishna\n"
				+ "ram";

		appDemo = getAppDemoInstance(data);
		String actualInput = appDemo.getInput(label, 1, 5);

		assertEquals("ram", actualInput);
	}

	@Test
	public void testInput_minLengthNoMaxLength() {
		String label = "Enter first name: ";
		String data = "ram\n"
				+ "krishna";

		appDemo = getAppDemoInstance(data);
		String actualInput = appDemo.getInput(label, 5, 0);

		assertEquals("krishna", actualInput);
	}

	@Test
	public void testInput_noMinLengthMaxLength() {
		String label = "Enter first name: ";
		String data = "krishna\n"
				+ "ram";

		appDemo = getAppDemoInstance(data);
		String actualInput = appDemo.getInput(label, 0, 5);

		assertEquals("ram", actualInput);
	}

}
