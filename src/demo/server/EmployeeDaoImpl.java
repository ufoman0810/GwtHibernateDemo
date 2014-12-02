package demo.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;

import demo.client.Certificate;
import demo.client.Employee;
import demo.client.EmployeeDao;

public class EmployeeDaoImpl implements EmployeeDao {

	SessionFactory factory = new Configuration().configure().buildSessionFactory();

	Session session;

	public static EmployeeDaoImpl getInstance() {
		return new EmployeeDaoImpl();
	}

	public List<Employee> getAllEmployees() {
		session = factory.openSession();
		List<Employee> employees = session.createQuery("FROM Employee").list();
		List<Employee> employeesdto = new ArrayList<Employee>(employees!=null?employees.size():0);
		if(employees!=null){
			for(Employee e : employees){
				employeesdto.add( createEmployeeDTO(e));
			}
		}
		
		
		session.close();
		return employeesdto;
	}
	
	private Employee createEmployeeDTO(Employee e){
		
		 Employee employeeInstance  = new Employee(e.getFirstName(), e.getLastName(), e.getSalary(), e.getAddress());
		 employeeInstance.setId(e.getId());
		 Set<Certificate> certificates = e.getCertificates();
		 Set<Certificate> certificateInstance= new HashSet<Certificate>(certificates !=null ? certificates.size():0);
		 if(certificates!=null){ 
			 for (Iterator iterator = certificateInstance.iterator(); iterator.hasNext();) {
				Certificate certificate = (Certificate) iterator.next();
				certificateInstance.add(certificate);
			}
		 }
		 
		 employeeInstance.setCertificates(certificateInstance);
		 return employeeInstance;
		
	}

	public Employee getEmployeById(int id) {
		Employee e = null;
		session = factory.openSession();
		e = (Employee) session.get(Employee.class, id);
		session.close();
		return e;
	}

	public Employee add(Employee employee) {
		session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {

			int id = (Integer) session.save( employee);
			tx.commit();
			session.close();
			return getEmployeById(id) ;
		} catch (TransactionException ex) {
			tx.rollback();
		}
		return null;
	}

	public boolean remove(Employee employee) {
		session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(employee);

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

	public boolean update(Employee employee) {

		session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(employee);
			session.close();
			t.commit();
			return true;
		} catch (HibernateException ex) {
			t.rollback();
		}
		return false;
	}
}
