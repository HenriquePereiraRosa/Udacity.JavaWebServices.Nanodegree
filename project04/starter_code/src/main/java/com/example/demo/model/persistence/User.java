package com.example.demo.model.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long id;

	@Column(nullable = false, unique = true)
	@JsonProperty
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, unique = true)
	private String password;
	
	@OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
	@JsonIgnore
    private Cart cart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;

		if (id != null && user.getId() != null) {
			return id.equals(user.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
