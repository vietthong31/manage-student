package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Faculty {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Class> classList = FXCollections.observableArrayList();
	
	public Faculty() {
		// TODO Auto-generated constructor stub
	}
	
	public Faculty(int id, String name) {
		setId(id);
		setName(name);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public static void main(String[] args) {
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	

	public final StringProperty nameProperty() {
		return this.name;
	}
	

	public final String getName() {
		return this.nameProperty().get();
	}
	

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ObservableList<Class> getClassList() {
		return classList;
	}
	
	
}
