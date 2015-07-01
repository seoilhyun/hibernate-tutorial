package org.hibernate.tutorial.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		try{
			/*
			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory( new StandardServiceRegistryBuilder().build() );
			
			Exception in thread "main" java.lang.ExceptionInInitializerError
				at org.hibernate.tutorial.util.HibernateUtil.buildSessionFactory(HibernateUtil.java:36)
				at org.hibernate.tutorial.util.HibernateUtil.<clinit>(HibernateUtil.java:10)
				at org.hibernate.tutorial.EventManager.createAndStoreEvent(EventManager.java:20)
				at org.hibernate.tutorial.EventManager.main(EventManager.java:14)
			Caused by: org.hibernate.HibernateException: Access to DialectResolutionInfo cannot be null when 'hibernate.dialect' not set
				at org.hibernate.engine.jdbc.dialect.internal.DialectFactoryImpl.determineDialect(DialectFactoryImpl.java:104)
				at org.hibernate.engine.jdbc.dialect.internal.DialectFactoryImpl.buildDialect(DialectFactoryImpl.java:71)
				at org.hibernate.engine.jdbc.internal.JdbcServicesImpl.configure(JdbcServicesImpl.java:209)
				at org.hibernate.boot.registry.internal.StandardServiceRegistryImpl.configureService(StandardServiceRegistryImpl.java:111)
				at org.hibernate.service.internal.AbstractServiceRegistryImpl.initializeService(AbstractServiceRegistryImpl.java:234)
				at org.hibernate.service.internal.AbstractServiceRegistryImpl.getService(AbstractServiceRegistryImpl.java:206)
				at org.hibernate.cfg.Configuration.buildTypeRegistrations(Configuration.java:1887)
				at org.hibernate.cfg.Configuration.buildSessionFactory(Configuration.java:1845)
				at org.hibernate.tutorial.util.HibernateUtil.buildSessionFactory(HibernateUtil.java:15)
				... 3 more
			*/
			
			
			// Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
             
            //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
			
		}catch(Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}