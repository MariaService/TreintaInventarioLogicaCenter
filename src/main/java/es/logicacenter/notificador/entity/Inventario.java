package es.logicacenter.notificador.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "venta")
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
	    private String categories;
	    private String imageUrl;
	    private boolean isVisible;
	    private boolean isOffline;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private String storeId;
	    private Integer measurementId; // Puede ser null, por eso es Integer
	    private Integer measureId;     // Puede ser null, por eso es Integer
	    private int originId;
	    private Object images; // Puedes ajustar el tipo si esperas una estructura específica
	    private String storeCategoriesId;
	    private boolean isComposition;
	    private boolean isProductAndCommodity;
	    private LocalDateTime deletedAt; // Puede ser null
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
		public String getCategories() {
			return categories;
		}
		public void setCategories(String categories) {
			this.categories = categories;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public boolean isVisible() {
			return isVisible;
		}
		public void setVisible(boolean isVisible) {
			this.isVisible = isVisible;
		}
		public boolean isOffline() {
			return isOffline;
		}
		public void setOffline(boolean isOffline) {
			this.isOffline = isOffline;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		public Integer getMeasurementId() {
			return measurementId;
		}
		public void setMeasurementId(Integer measurementId) {
			this.measurementId = measurementId;
		}
		public Integer getMeasureId() {
			return measureId;
		}
		public void setMeasureId(Integer measureId) {
			this.measureId = measureId;
		}
		public int getOriginId() {
			return originId;
		}
		public void setOriginId(int originId) {
			this.originId = originId;
		}
		public Object getImages() {
			return images;
		}
		public void setImages(Object images) {
			this.images = images;
		}
		public String getStoreCategoriesId() {
			return storeCategoriesId;
		}
		public void setStoreCategoriesId(String storeCategoriesId) {
			this.storeCategoriesId = storeCategoriesId;
		}
		public boolean isComposition() {
			return isComposition;
		}
		public void setComposition(boolean isComposition) {
			this.isComposition = isComposition;
		}
		public boolean isProductAndCommodity() {
			return isProductAndCommodity;
		}
		public void setProductAndCommodity(boolean isProductAndCommodity) {
			this.isProductAndCommodity = isProductAndCommodity;
		}
		public LocalDateTime getDeletedAt() {
			return deletedAt;
		}
		public void setDeletedAt(LocalDateTime deletedAt) {
			this.deletedAt = deletedAt;
		}
		@Override
		public String toString() {
			return "Inventario [id=" + id + ", idVentario=" + idVentario + ", name=" + name + ", sku=" + sku
					+ ", price=" + price + ", cost=" + cost + ", stock=" + stock + ", notes=" + notes
					+ ", transactions=" + transactions + ", categories=" + categories + ", imageUrl=" + imageUrl
					+ ", isVisible=" + isVisible + ", isOffline=" + isOffline + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + ", storeId=" + storeId + ", measurementId=" + measurementId
					+ ", measureId=" + measureId + ", originId=" + originId + ", images=" + images
					+ ", storeCategoriesId=" + storeCategoriesId + ", isComposition=" + isComposition
					+ ", isProductAndCommodity=" + isProductAndCommodity + ", deletedAt=" + deletedAt + "]";
		}
	
	
}
