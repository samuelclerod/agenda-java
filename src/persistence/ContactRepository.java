package persistence;

import models.Contact;

public interface ContactRepository {
	
	public void insert(Contact contact);
	
	public void remove(int id);
	
	public Contact[] findBy(String fieldName, String value);
	
	public Contact[] findAll();
	
	public void update(Contact contact);
}
