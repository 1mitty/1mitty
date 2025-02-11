package communitygarden;

public class Donation {
    private String type;
    private String amount;

    public Donation(String type, String amount) {
        this.type = type;
        this.amount = amount;
    }
//get and set
    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Donation Type: " + type + ", Amount: " + amount;
    }
}
