package es.logicacenter.notificador.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceReport {
    private int sales;
    private int spendings;
    private int utility;

    @JsonProperty("sales")
    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @JsonProperty("spendings")
    public int getSpendings() {
        return spendings;
    }

    public void setSpendings(int spendings) {
        this.spendings = spendings;
    }

    @JsonProperty("utility")
    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }
}
