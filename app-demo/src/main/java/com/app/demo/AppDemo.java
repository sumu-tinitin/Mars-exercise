package com.app.demo;

import com.app.demo.dao.PersonDao;
import com.app.demo.domain.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AppDemo implements CommandLineRunner {

	private static final int ADD_PERSON = 1;
	private static final int EDIT_PERSON = 2;
	private static final int DELETE_PERSON = 3;
	private static final int COUNT_PERSONS = 4;
	private static final int LIST_PERSONS = 5;
	private static final int QUIT_APPLICATION = 6;

	private final PersonDao personDao;
	private final Scanner in;

	public AppDemo(PersonDao personDao) {
		this.personDao = personDao;
		this.in = new Scanner(System.in);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			showMenu();
			int choice = getChoice();
			System.out.println();
			performOperation(choice);
		}
	}

	private void showMenu() {
		System.out.println();
		System.out.println();
		System.out.println("Option Menu");
		System.out.println("1. Add person");
		System.out.println("2. Edit person");
		System.out.println("3. Delete person");
		System.out.println("4. Count number of persons");
		System.out.println("5. List persons");
		System.out.println("6. Quit application");
	}

	private int getChoice() {
		boolean isChoiceCorrect = true;
		int choice;
		do {
			System.out.print("Enter number to perform operation: ");
			choice = 0;
			try {
				choice = Integer.parseInt(in.nextLine());
				if (choice < 1 || choice > 6) {
					System.out.println("Invalid choice.");
				} else {
					isChoiceCorrect = true;
				}
			} catch (NumberFormatException e) {
				isChoiceCorrect = false;
			}
		} while (!isChoiceCorrect);
		return choice;
	}

	private void performOperation(int operation) {
		switch (operation) {
			case ADD_PERSON:
				addPerson();
				break;
			case EDIT_PERSON:
				editPerson();
				break;
			case DELETE_PERSON:
				deletePerson();
				break;
			case COUNT_PERSONS:
				countPersons();
				break;
			case LIST_PERSONS:
				listPersons();
				break;
			case QUIT_APPLICATION:
				System.out.println("Quitting.");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid operation.");
		}
	}

	private void addPerson() {
		System.out.println("ADD PERSON");
		System.out.println("--------------");
		String firstName = getInput("Enter first name (Max 20 characters): ", 1, 20);
		String lastName = getInput("Enter last name (Max 20 characters): ", 1, 20);
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		try {
			personDao.add(person);
			System.out.println("Person added successfully.");
		} catch (Exception e) {
			System.out.println("Error occurred while adding person.");
			e.printStackTrace();
		}
	}

	private void editPerson() {
		System.out.println("EDIT PERSON");
		System.out.println("--------------");
		Person person = getPerson("Enter ID of the person to edit: ");
		if (person != null) {
			String firstName = person.getFirstName();
			if (getYesNo("Do you want to edit first name [y/n]: ")) {
				firstName = getInput("Enter first name (Max 20 characters): ", 1, 20);
			}
			String lastName = person.getLastName();
			if (getYesNo("Do you want to edit last name [y/n]: ")) {
				lastName = getInput("Enter last name (Max 20 characters): ", 1, 20);
			}
			person.setFirstName(firstName);
			person.setLastName(lastName);
			try {
				personDao.update(person);
				System.out.println("Person updated successfully.");
			} catch (Exception e) {
				System.out.println("Error occurred while updating person.");
				e.printStackTrace();
			}
		}
	}

	private void deletePerson() {
		System.out.println("DELETE PERSON");
		System.out.println("--------------");
		Person person = getPerson("Enter ID of the person to delete: ");
		if (person != null) {
			try {
				personDao.delete(person.getId());
				System.out.println("Person deleted successfully.");
			} catch (Exception e) {
				System.out.println("Error occurred while deleting person.");
				e.printStackTrace();
			}
		}
	}

	private void countPersons() {
		System.out.println("COUNT PERSONS");
		System.out.println("--------------");
		try {
			int count = personDao.count();
			System.out.println("Number of persons: " + count);
		} catch (Exception e) {
			System.out.println("Error occurred while counting persons.");
			e.printStackTrace();
		}
	}

	private void listPersons() {
		System.out.println("LIST PERSONS");
		System.out.println("--------------");
		try {
			List<Person> list = personDao.getAll();
			if (!list.isEmpty()) {
				System.out.println(String.format("%-10s | %-20s | %-20s", "ID", "First Name", "Last Name"));
				System.out.println("--------------------------------------------------------");
				for (Person person : list) {
					System.out.println(String.format("%-10d | %-20s | %-20s", person.getId(), person.getFirstName(), person.getLastName()));
				}
				System.out.println("--------------------------------------------------------");
			} else {
				System.out.println("No data found.");
			}
		} catch (Exception e) {
			System.out.println("Error occurred while getting persons.");
			e.printStackTrace();
		}
	}

	private boolean getYesNo(String label) {
		System.out.print(label);
		return in.nextLine().equals("y");
	}

	private Person getPerson(String label) {
		boolean isInputValid;
		Person person = null;
		int attempt = 3;
		do {
			System.out.print(label);
			String value = in.nextLine();
			try {
				Long id = Long.parseLong(value);
				person = personDao.findById(id);
				isInputValid = person != null;
			} catch (NumberFormatException ignored) {
				isInputValid = false;
			}
			attempt--;
			if (!isInputValid) {
				System.out.println("Invalid person ID.");
			}
		} while (!isInputValid && attempt > 0);
		return person;
	}

	private String getInput(final String label, final int minLength, final int maxLength) {
		boolean isInputValid;
		String input;
		do {
			System.out.print(label);
			input = in.nextLine();
			isInputValid = (minLength < 1 || input.length() >= minLength)
					&& (maxLength < 1 || input.length() <= maxLength);
			if (!isInputValid) {
				System.out.println("Invalid input.");
			}
		} while (!isInputValid);
		return input;
	}

}
