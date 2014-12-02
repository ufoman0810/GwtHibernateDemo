package demo.client;

import java.util.List;

public interface EmployeeDao {

	List<Employee> getAllEmployees();
	Employee getEmployeById(int id);
	boolean remove(Employee employee);
	boolean update(Employee employee);
}
