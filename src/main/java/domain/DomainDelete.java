package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DomainDelete {

    @Id
    private String domainName;

    @Column
    private String createdOn;

    private DomainDelete() {

    }

    public DomainDelete(String domainName, String createdOn) {
        this.domainName = domainName;
        this.createdOn = createdOn;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}
