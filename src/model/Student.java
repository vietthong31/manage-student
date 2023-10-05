package model;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Student {

	private StringProperty id = new SimpleStringProperty();
	private StringProperty firstName = new SimpleStringProperty();
	private StringProperty lastName = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
	private StringProperty gender = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty className = new SimpleStringProperty();
	private StringProperty facultyName = new SimpleStringProperty();
	
	public Student() {}
	
	public Student(String id, String firstName, String lastName, LocalDate date, String gender) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setDateOfBirth(date);
		setGender(gender);
	}
	
	public Student(String id, String firstName, String lastName, LocalDate date, String gender, String facultyName, String className) {
		this(id, firstName, lastName, date, gender);
		setFacultyName(facultyName);
		setClassName(className);		
	}
	
	public Student(Student s) {
		this(s.getId(), s.getFirstName(), s.getLastName(), s.getDateOfBirth(), s.getGender(), s.getFacultyName(), s.getClassName());
	}
	
	@Override
	public String toString() {
		String desc = String.format("[%s %s - %s]", getLastName(), getFirstName(), getClassName());
		return desc;
	}
	
	
	public final StringProperty idProperty() {
		return this.id;
	}
	
	public final String getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	public final String getFirstName() {
		return this.firstNameProperty().get();
	}
	
	public final void setFirstName(final String firstName) {
		this.firstNameProperty().set(firstName);
	}
	
	public final StringProperty lastNameProperty() {
		return this.lastName;
	}
	
	public final String getLastName() {
		return this.lastNameProperty().get();
	}
	
	public final void setLastName(final String lastName) {
		this.lastNameProperty().set(lastName);
	}
	
	public final SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
		return this.dateOfBirth;
	}
	
	public final LocalDate getDateOfBirth() {
		return this.dateOfBirthProperty().get();
	}
	
	public final void setDateOfBirth(final LocalDate dateOfBirth) {
		this.dateOfBirthProperty().set(dateOfBirth);
	}
	
	public final StringProperty genderProperty() {
		return this.gender;
	}
	
	public final String getGender() {
		return this.genderProperty().get();
	}
	
	public final void setGender(final String gender) {
		this.genderProperty().set(gender);
	}
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}


	public final StringProperty classNameProperty() {
		return this.className;
	}

	public final String getClassName() {
		return this.classNameProperty().get();
	}
	
	public final void setClassName(final String className) {
		this.classNameProperty().set(className);
	}
	
	public final StringProperty facultyNameProperty() {
		return this.facultyName;
	}
	
	public final String getFacultyName() {
		return this.facultyNameProperty().get();
	}
	
	public final void setFacultyName(final String facultyName) {
		this.facultyNameProperty().set(facultyName);
	}
	
	
	
}
