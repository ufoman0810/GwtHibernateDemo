package demo.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import demo.client.Address;
import demo.client.DemoService;
import demo.client.Employee;

public class DemoServiceImpl extends RemoteServiceServlet implements DemoService {

	private static final long serialVersionUID = 1L;

	public String getEmployee(int id) {
		Employee e = EmployeeDaoImpl.getInstance().getEmployeById(id);
		return e.getFirstName() + " - " + e.getLastName() + " adres:" + e.getAddress().getCity();

	}

	public List<Employee> getAllEmployees() {

		return EmployeeDaoImpl.getInstance().getAllEmployees();
	}

	public Employee addEmployee(Employee employee) {
		return EmployeeDaoImpl.getInstance().add(employee);

	}

	public Address addAddress(Address address) {
		return AddressDaoImpl.getInstance().add(address);
	}

}
