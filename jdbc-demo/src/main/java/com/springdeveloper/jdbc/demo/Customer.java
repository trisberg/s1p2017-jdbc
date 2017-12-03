package com.springdeveloper.jdbc.demo;

import java.util.Date;

public class Customer {

	private Long id;

	private String name;

	private String companyName;

	private Date customerSince;

	public Customer() {
	}

	public Customer(String name, String companyName, Date customerSince) {
		this.name = name;
		this.companyName = companyName;
		this.customerSince = customerSince;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCustomerSince() {
		return customerSince;
	}

	public void setCustomerSince(Date customerSince) {
		this.customerSince = customerSince;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", companyName='" + companyName + '\'' +
				", customerSince=" + customerSince +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Customer customer = (Customer) o;

		if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
		if (companyName != null ? !companyName.equals(customer.companyName) : customer.companyName != null)
			return false;
		return customerSince != null ? customerSince.equals(customer.customerSince) : customer.customerSince == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
		result = 31 * result + (customerSince != null ? customerSince.hashCode() : 0);
		return result;
	}
}
