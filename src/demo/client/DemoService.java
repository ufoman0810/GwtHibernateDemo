package demo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("demo")
public interface DemoService extends RemoteService {
	String getEmployee(int id);

	Employee addEmployee(Employee employee);

	List<Employee> getAllEmployees();
	Address addAddress(Address address);
}
