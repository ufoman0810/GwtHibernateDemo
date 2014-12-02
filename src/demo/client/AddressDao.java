package demo.client;

public interface AddressDao {
	Address getAddressById(int id);

	boolean remove(Address address);

	boolean update(Address address);

	Address add(Address address);
}
