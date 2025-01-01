package es.logicacenter.notificador.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
	private String id;
	private String uid;
	private String date;
	private String description;
	private String lastReminder;
	private int isOffline;
	private String transactionCount;
	private String orderAddress;
	private String deliveryPrice;
	private String createdAt;
	private String updatedAt;
	private String deletedAt;
	private String userId;
	private String storeId;
	private int paymentTypeId;
	private int transactionStatusId;
	private int transactionTypeId;
	private String contactId;
	private int originId;
	private String deliveryTypeId;
	private double value;
	private String spendCategoryId;
	private String source;
	private String contactName;

	// Getters y Setters
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@JsonProperty("date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("lastReminder")
	public String getLastReminder() {
		return lastReminder;
	}

	public void setLastReminder(String lastReminder) {
		this.lastReminder = lastReminder;
	}

	@JsonProperty("isOffline")
	public int getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(int isOffline) {
		this.isOffline = isOffline;
	}

	@JsonProperty("transactionCount")
	public String getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(String transactionCount) {
		this.transactionCount = transactionCount;
	}

	@JsonProperty("orderAddress")
	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	@JsonProperty("deliveryPrice")
	public String getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(String deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	@JsonProperty("createdAt")
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("updatedAt")
	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonProperty("deletedAt")
	public String getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	@JsonProperty("userId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("storeId")
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@JsonProperty("paymentTypeId")
	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	@JsonProperty("transactionStatusId")
	public int getTransactionStatusId() {
		return transactionStatusId;
	}

	public void setTransactionStatusId(int transactionStatusId) {
		this.transactionStatusId = transactionStatusId;
	}

	@JsonProperty("transactionTypeId")
	public int getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(int transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	@JsonProperty("contactId")
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@JsonProperty("originId")
	public int getOriginId() {
		return originId;
	}

	public void setOriginId(int originId) {
		this.originId = originId;
	}

	@JsonProperty("deliveryTypeId")
	public String getDeliveryTypeId() {
		return deliveryTypeId;
	}

	public void setDeliveryTypeId(String deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}

	@JsonProperty("value")
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@JsonProperty("spendCategoryId")
	public String getSpendCategoryId() {
		return spendCategoryId;
	}

	public void setSpendCategoryId(String spendCategoryId) {
		this.spendCategoryId = spendCategoryId;
	}

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty("contactName")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
}
