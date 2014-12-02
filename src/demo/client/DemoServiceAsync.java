package demo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DemoServiceAsync {

	void getEmployee(int id, AsyncCallback<String> callback);

	void addEmployee(Employee employee, AsyncCallback<Employee> callback);

	void getAllEmployees(AsyncCallback<List<Employee>> callback);

	void addAddress(Address address, AsyncCallback<Address> callback);
}
