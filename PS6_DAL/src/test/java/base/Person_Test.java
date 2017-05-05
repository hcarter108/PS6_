package base;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.PersonDomainModel;

public class Person_Test {
	private static PersonDomainModel per1 = new PersonDomainModel();
	private static PersonDomainModel per2 = new PersonDomainModel();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		per1.setBirthday(new Date(0));
		per1.setCity("Townsend");
		per1.setFirstName("Bert");
		per1.setLastName("Gibbons");
		per1.setPostalCode(19734);
		per1.setStreet("214 Labrador Lane");
		
		per2.setBirthday(new Date(0));
		per2.setCity("Town");
		per2.setFirstName("Bob");
		per2.setLastName("Goodman");
		per2.setPostalCode(19734);
		per2.setStreet("214 Labrador");
		
		PersonDAL.addPerson(per1);
		PersonDomainModel per3 = PersonDAL.getPerson(per1.getPersonID());
		assertNotNull(per3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Seemed a good way to clear the table at the end, since getPersons is an arraylist, for each
		if (PersonDAL.getPersons().size()!=0)
		{
		for (PersonDomainModel p: PersonDAL.getPersons()){
			PersonDAL.deletePerson(p.getPersonID());
		}
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddGetPerson() {
		//Already tested add essentially, but here is add with a get, using a second person
		PersonDAL.addPerson(per2);
		assertEquals(per2,PersonDAL.getPerson(per2.getPersonID()));
	}

	@Test
	public void testgetPersons() {
		assertEquals(PersonDAL.getPersons().size(),2);
		// Just in case ordering is different, at least check its getting a right person
		assert(PersonDAL.getPersons().get(0)==(per1) || PersonDAL.getPersons().get(0)==(per2));
	}
	@Test
	public void testdeletePerson() {
		// Test the method by checking the size of the returned array before and after
		int size = PersonDAL.getPersons().size();
		PersonDAL.deletePerson(per2.getPersonID());
		assertEquals(PersonDAL.getPersons().size(),(size-1));
	}
	@Test
	public void testupdatePerson() {
		// Check before so as to see it changes afterward
		PersonDomainModel per4 = per1;
		assertEquals(PersonDAL.getPerson(per1.getPersonID()).getFirstName(),"Bert");
		per4.setFirstName("Johnny");
		PersonDAL.updatePerson(per4);
		assertEquals(PersonDAL.getPerson(per1.getPersonID()).getFirstName(),"Johnny");
		
	}

}
