package domain;

public class DomainDeleteSum {

    private String date;

    private Long count;

    private DomainDeleteSum() {
    }

    public DomainDeleteSum(String date, Long count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "DomainDeleteSum{" +
                "date='" + date + '\'' +
                ", count=" + count +
                '}';
    }
}
