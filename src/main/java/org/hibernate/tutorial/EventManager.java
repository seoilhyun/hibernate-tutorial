package org.hibernate.tutorial;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EventManager mgr = new EventManager();
		
		mgr.createAndStoreEvent("My Event", new Date());
	
		List<Event> events = mgr.listEvents();
		
		System.out.println("================================================================================================");
		events.forEach(theEvent -> System.out.println( "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate() ));
		System.out.println("================================================================================================");
		
		/*
        for (int i = 0; i < events.size(); i++) {
            Event theEvent = (Event) events.get(i);
            System.out.println( "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate() );
        }
        */
        
        
        Long eventId = mgr.createAndStoreEvent("My New Event", new Date());
        Long personId = mgr.createAndStorePerson("Foo", "Bar");
        
        mgr.addPersonToEvent(personId, eventId);
        System.out.println("Added person " + personId + " to event " + eventId);

		List<Person> persons = mgr.listPersons();

		System.out.println("================================================================================================");
		persons.forEach(aPerson -> 
			{
				System.out.println( "Person: " + aPerson.getFirstname() + " " + aPerson.getLastname() );
				aPerson.getEvents().forEach(theEvent -> System.out.println( "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate() ));
			}
		);
		System.out.println("================================================================================================");
		
		/*
        for (int i = 0; i < persons.size(); i++) {
        	Person aPerson = (Person) persons.get(i);
            System.out.println( "Person: " + aPerson.getFirstname() + " " + aPerson.getLastname() );
            Set<Event> theEvents = aPerson.getEvents();
            for(Event event : theEvents) {
            	System.out.println( "Event: " + event.getTitle() + " Time: " + event.getDate() );
            }
        }
        */
		
        mgr.printPersons();
        
		HibernateUtil.getSessionFactory().close();
	}
	
	private Long createAndStoreEvent(String title, Date theDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		Event theEvent = new Event();
		theEvent.setTitle(title);
		theEvent.setDate(theDate);
		
		session.save(theEvent);
		
		session.getTransaction().commit();
		
		return theEvent.getId();
	}
	
	@SuppressWarnings("rawtypes")
	private List listEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		return result;
	}
	

	private Long createAndStorePerson(String firstname, String lastname){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		Person aPerson = new Person();
		aPerson.setFirstname(firstname);
		aPerson.setLastname(lastname);
		
		session.save(aPerson);
		
		session.getTransaction().commit();
		
		return aPerson.getId();
	}
	
	@SuppressWarnings("rawtypes")
	private List listPersons() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createQuery("from Person").list();
		
		for(int i = 0 ; i < result.size() ; i++) {
			Person aPerson = (Person)result.get(i);
			Hibernate.initialize(aPerson.getEvents());
		}
		
		session.getTransaction().commit();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private void printPersons() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createQuery("from Person").list();
		
		for(int i = 0 ; i < result.size() ; i++) {
			Person aPerson = (Person)result.get(i);
			System.out.println( "Person: " + aPerson.getFirstname() + " " + aPerson.getLastname() );
            Set<Event> theEvents = aPerson.getEvents();
            for(Event event : theEvents) {
            	System.out.println( "Event: " + event.getTitle() + " Time: " + event.getDate() );
            }
		}
		
		session.getTransaction().commit();
	}
	
	private void addPersonToEvent(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Person aPerson = (Person) session.load(Person.class, personId);
		Event anEvent = (Event) session.load(Event.class, eventId);
		
		aPerson.getEvents().add(anEvent);
		
		session.getTransaction().commit();
	}
}