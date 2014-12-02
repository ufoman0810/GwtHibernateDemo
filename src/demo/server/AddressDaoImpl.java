package demo.server;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;

import demo.client.Address;
import demo.client.AddressDao;
import demo.client.Employee;
import demo.client.EmployeeDao;

public class AddressDaoImpl implements AddressDao {

	@SuppressWarnings("deprecation")
	SessionFactory factory = new Configuration().configure().buildSessionFactory();

	Session session;

	public static AddressDaoImpl getInstance() {
		return new AddressDaoImpl();
	}

	 

	public Address getAddressById(int id) {
		Address e = null;
		session = factory.openSession();
		e = (Address) session.get(Address.class, id);
		session.close();
		return e;
	}

	public Address add(Address address) {
		session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {

			int id = (Integer) session.save( address);
			tx.commit();
			session.close();
			return getAddressById (id) ;
		} catch (TransactionException ex) {
			tx.rollback();
		}
		return null;
	}

	public boolean remove(Address address) {
		session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(address);

			t.commit();
			session.flush();
			session.close();
			return true;
		} catch (HibernateException ex) {
			t.rollback();
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public boolean update(Address address) {

		session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(address);
			session.close();
			t.commit();
			return true;
		} catch (HibernateException ex) {
			t.rollback();
		}
		return false;
	}


 
}
