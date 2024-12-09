package es.logicacenter.notificador.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventarioResponse {
	 @JsonProperty("id")
	    private String id;

	    @JsonProperty("name")
	    private String name;

	    @JsonProperty("sku")
	    private String sku;

	    @JsonProperty("price")
	    private double price;

	    @JsonProperty("cost")
	    private double cost;

	    @JsonProperty("stock")
	    private int stock;

	    @JsonProperty("notes")
	    private String notes;

	    @JsonProperty("transactions")
	    private int transactions;

	    @JsonProperty("categories")
	    private String categories;

	    @JsonProperty("imageUrl")
	    private String imageUrl;

	    @JsonProperty("isVisible")
	    private int isVisible;

	    @JsonProperty("isOffline")
	    private int isOffline;

	    @JsonProperty("createdAt")
	    private String createdAt; // Puedes usar LocalDateTime si lo parseas manualmente.

	    @JsonProperty("updatedAt")
	    private String updatedAt; // Igual que `createdAt`.

	    @JsonProperty("storeId")
	    private String storeId;

	    @JsonProperty("measurementId")
	    private String measurementId;

	    @JsonProperty("measureId")
	    private String measureId;

	    @JsonProperty("originId")
	    private int originId;

	    @JsonProperty("images")
	    private String images;

	    @JsonProperty("storeCategoriesId")
	    private String storeCategoriesId;

	    @JsonProperty("isComposition")
	    private boolean isComposition;

	    @JsonProperty("isProductAndCommodity")
	    private boolean isProductAndCommodity;

	    @JsonProperty("deletedAt")
	    private String deletedAt;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public int getIsVisible() {
			return isVisible;
		}

		public void setIsVisible(int isVisible) {
			this.isVisible = isVisible;
		}

		public int getIsOffline() {
			return isOffline;
		}

		public void setIsOffline(int isOffline) {
			this.isOffline = isOffline;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public String getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}

		public String getStoreId() {
			return storeId;
		}

		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

		public String getMeasurementId() {
			return measurementId;
		}

		public void setMeasurementId(String measurementId) {
			this.measurementId = measurementId;
		}

		public String getMeasureId() {
			return measureId;
		}

		public void setMeasureId(String measureId) {
			this.measureId = measureId;
		}

		public int getOriginId() {
			return originId;
		}

		public void setOriginId(int originId) {
			this.originId = originId;
		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
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

		public String getDeletedAt() {
			return deletedAt;
		}

		public void setDeletedAt(String deletedAt) {
			this.deletedAt = deletedAt;
		}

		@Override
		public String toString() {
			return "InventarioResponse [id=" + id + ", name=" + name + ", sku=" + sku + ", price=" + price + ", cost="
					+ cost + ", stock=" + stock + ", notes=" + notes + ", transactions=" + transactions
					+ ", categories=" + categories + ", imageUrl=" + imageUrl + ", isVisible=" + isVisible
					+ ", isOffline=" + isOffline + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
					+ ", storeId=" + storeId + ", measurementId=" + measurementId + ", measureId=" + measureId
					+ ", originId=" + originId + ", images=" + images + ", storeCategoriesId=" + storeCategoriesId
					+ ", isComposition=" + isComposition + ", isProductAndCommodity=" + isProductAndCommodity
					+ ", deletedAt=" + deletedAt + "]";
		}
	    
	    
	    
	    
}
