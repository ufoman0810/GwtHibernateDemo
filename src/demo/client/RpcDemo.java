package demo.client;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class RpcDemo implements EntryPoint {
	DemoServiceAsync service = GWT.create(DemoService.class);
	final TextBox txtFullName = new TextBox();
	final TextBox txtAddress = new TextBox();

	Button btnGetEmployee = new Button("Çalýþaný getir");
	Button btnAddEmployee = new Button("Ekle");

	final ListBox lst = new ListBox();

	public void onModuleLoad() {

		RootPanel.get().add(new HTML("Calisan Bilgisi"));
		RootPanel.get().add(txtFullName);
		RootPanel.get().add(txtAddress);

		RootPanel.get().add(btnAddEmployee);

		RootPanel.get().add(new HTML("<br>"));
		RootPanel.get().add(lst);
		RootPanel.get().add(btnGetEmployee);

		btnAddEmployee.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				Address address = new Address("street", txtAddress.getText(), "stat", "heb");

				service.addAddress(address, new AsyncCallback<Address>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Address result) {
						String[] arr = txtFullName.getText().split(" ");
						String name = arr[0];
						String lastName = arr[1];

						Employee employee = new Employee(name, lastName, 2000, result);
						service.addEmployee(employee, new AsyncCallback<Employee>() {

							public void onSuccess(Employee result) {

								listAllEmployees();
								Window.alert(result.getFirstName() + " " + result.getAddress().getCity());
							}

							public void onFailure(Throwable caught) {
							}
						});

					}
				});

			}
		});

		btnGetEmployee.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				service.getEmployee(Integer.parseInt(lst.getSelectedValue()), new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(String result) {
						Window.alert(result);
					}
				});

			}
		});

		listAllEmployees();

	}

	private void listAllEmployees() {
		lst.clear();
		service.getAllEmployees(new AsyncCallback<List<Employee>>() {

			public void onSuccess(List<Employee> result) {
				for (Iterator item = result.iterator(); item.hasNext();) {
					Employee e = (Employee) item.next();
					lst.addItem(e.getFirstName(), e.getId() + "");
				}
			}

			public void onFailure(Throwable caught) {
			}
		});

	}
}
