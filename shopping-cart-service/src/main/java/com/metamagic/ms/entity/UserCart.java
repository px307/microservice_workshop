package com.metamagic.ms.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.metamagic.ms.exception.IllegalArgumentCustomException;
import com.metamagic.ms.validation.CommonValidation;

/**
 * @author sagar THIS CLASS IS USED FOR USER CART DOCUMENTS
 */
@PersistenceCapable(table = "usercart", detachable = "true")
@DatastoreIdentity(customStrategy = "uuid")
public class UserCart implements CommonValidation {

	@PrimaryKey
	@Persistent(column = "_id", customValueStrategy = "uuid")
	public String id;

	@Persistent
	private String userId;

	@Persistent(defaultFetchGroup = "true")
	private List<LineItem> lineItems;

	@Persistent
	private double total;

	@Persistent
	private String status;

	public UserCart() {

	}

	public UserCart(String id, String userId, List<LineItem> lineItems) {
		super();
		this.id = id;
		this.userId = userId;
		this.lineItems = lineItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) throws IllegalArgumentCustomException {
		if (!this.isValid(userId)) {
			throw new IllegalArgumentCustomException("User Id should not be null.");
		} else {
			this.userId = userId;
		}
	}

	public List<LineItem> getlineItems() {
		return lineItems;
	}

	public void setlineItems(List<LineItem> lineItems) throws IllegalArgumentCustomException {
		if (!this.isValid(lineItems)) {
			throw new IllegalArgumentCustomException("Line Items should not be null.");
		} else {
			this.lineItems = lineItems;
		}
	}

	public void addOrUpdateProduct(String id, String name, Integer quantity, Double price) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<LineItem>();
		}

		boolean add = true;
		for (Iterator<LineItem> iterator = lineItems.iterator(); iterator.hasNext();) {
			LineItem userProduct = (LineItem) iterator.next();
			if (userProduct.getId().equals(id)) {
				// userProduct.setName(name);
				// userProduct.setQuantity(quantity);
				// userProduct.setPrice(price);
				add = false;
				iterator.remove();
			}
		}

		if (add) {
			LineItem userProduct = new LineItem(id, name, quantity, price);
			this.lineItems.add(userProduct);
			this.addToTotal(userProduct);
		}
	}

	private void addToTotal(LineItem userProduct) {
		this.total = total + userProduct.getPrice();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) throws IllegalArgumentCustomException {
		if (!this.isValid(total)) {
			throw new IllegalArgumentCustomException("Total should not be null.");
		} else {
			this.total = total;
		}
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) throws IllegalArgumentCustomException {
		if (!this.isValid(status)) {
			throw new IllegalArgumentCustomException("Status should not be null.");
		} else {
			this.status = status;
		}
	}

	public void addProduct(String id, String name, Integer quantity, Double price) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<LineItem>();
		}
		LineItem userProduct = new LineItem(id, name, quantity, price);
		this.lineItems.add(userProduct);
		this.addToTotal(userProduct);
	}

	@Override
	public String toString() {
		return "UserCart [id=" + id + ", userId=" + userId + ", lineItems=" + lineItems + "]";
	}

}
