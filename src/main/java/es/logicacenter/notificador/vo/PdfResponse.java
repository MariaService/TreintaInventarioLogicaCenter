package es.logicacenter.notificador.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PdfResponse {

	
	 @JsonProperty("filePath")
	    private String filePath;
	    
	    @JsonProperty("sales")
	    private int sales;
	    
	    @JsonProperty("salesPayments")
	    private int salesPayments;
	    
	    @JsonProperty("expenses")
	    private int expenses;
	    
	    @JsonProperty("expensesPayments")
	    private int expensesPayments;
	    
	    @JsonProperty("totalTransactions")
	    private int totalTransactions;

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public int getSales() {
			return sales;
		}

		public void setSales(int sales) {
			this.sales = sales;
		}

		public int getSalesPayments() {
			return salesPayments;
		}

		public void setSalesPayments(int salesPayments) {
			this.salesPayments = salesPayments;
		}

		public int getExpenses() {
			return expenses;
		}

		public void setExpenses(int expenses) {
			this.expenses = expenses;
		}

		public int getExpensesPayments() {
			return expensesPayments;
		}

		public void setExpensesPayments(int expensesPayments) {
			this.expensesPayments = expensesPayments;
		}

		public int getTotalTransactions() {
			return totalTransactions;
		}

		public void setTotalTransactions(int totalTransactions) {
			this.totalTransactions = totalTransactions;
		}

		@Override
		public String toString() {
			return "PdfResponse [filePath=" + filePath + ", sales=" + sales + ", salesPayments=" + salesPayments
					+ ", expenses=" + expenses + ", expensesPayments=" + expensesPayments + ", totalTransactions="
					+ totalTransactions + "]";
		}
	    
	    
}
