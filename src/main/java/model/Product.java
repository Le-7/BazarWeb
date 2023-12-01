package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String image;
    private String moderateur;
    
    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Product() {
    }
    
    public Product(String productName, double productPrice, String productImage) {
        this.name = productName;
        this.price = productPrice;
        this.image = productImage;
    }
	public Product(String productName, double productPrice, String productImage, String username) {
		// TODO Auto-generated constructor stub
		this.name = productName;
        this.price = productPrice;
        this.image = productImage;
        this.moderateur=username;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

    
}