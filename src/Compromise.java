import java.math.BigDecimal;
import java.time.LocalDate;

public class Compromise {

    private String bank;
    private String payeeName;
    private LocalDate dueDate;
    private BigDecimal amount;
    private String payerName;
    private String paymentDescription;

    public Compromise() {}

    public Compromise(final String bank, final String payeeName, final LocalDate dueDate, final BigDecimal amount,
                      final String payerName, final String paymentDescription) {
        this.bank = bank;
        this.payeeName = payeeName;
        this.dueDate = dueDate;
        this.amount = amount;
        this.payerName = payerName;
        this.paymentDescription = paymentDescription;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(final String bank) {
        this.bank = bank;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(final String payeeName) {
        this.payeeName = payeeName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(final LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(final String payerName) {
        this.payerName = payerName;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(final String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }
}
