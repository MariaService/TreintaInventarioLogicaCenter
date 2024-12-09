package es.logicacenter.notificador.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 private String idVentario;
	    private String name;
	    private String sku;
	    private double price;
	    private double cost;
	    private int stock;
	    private String notes;
	    private int transactions;
	    private String storeId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getIdVentario() {
			return idVentario;
		}
		public void setIdVentario(String idVentario) {
			this.idVentario = idVentario;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSku() {
			return sku;
		}
		public void setSku(String sku) {
			this.sku = sku;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		public int getStock() {
			return stock;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public int getTransactions() {
			return transactions;
		}
		public void setTransactions(int transactions) {
			this.transactions = transactions;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		@Override
		public String toString() {
			return "Inventario [id=" + id + ", idVentario=" + idVentario + ", name=" + name + ", sku=" + sku
					+ ", price=" + price + ", cost=" + cost + ", stock=" + stock + ", notes=" + notes
					+ ", transactions=" + transactions + ", storeId=" + storeId + "]";
		}

	    
	    
	    
	
}
