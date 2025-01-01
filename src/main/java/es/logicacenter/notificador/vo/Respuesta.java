package es.logicacenter.notificador.vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Respuesta {
    private List<Transaction> transactions;
    private BalanceReport balanceReport;

    @JsonProperty("transactions")
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @JsonProperty("balanceReport")
    public BalanceReport getBalanceReport() {
        return balanceReport;
    }

    public void setBalanceReport(BalanceReport balanceReport) {
        this.balanceReport = balanceReport;
    }
}
