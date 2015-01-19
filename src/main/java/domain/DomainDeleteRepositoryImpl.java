package domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class DomainDeleteRepositoryImpl implements DomainDeleteRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public DomainDeleteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<DomainDeleteSum> getSum() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DomainDeleteSum> query = builder.createQuery(DomainDeleteSum.class);
        Root<DomainDelete> root = query.from(DomainDelete.class);
        query.select(
                builder.construct(DomainDeleteSum.class,
                        builder.substring(root.<String>get("createdOn"), 0, 10),
                        builder.count(root.<String>get("domainName"))
                )
        ).groupBy(
//                builder.function("to_char", String.class, root.<Date>get("createdOn"), builder.literal("YYYY-MM-DD"))
                builder.substring(root.<String>get("createdOn"), 0, 10)
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> getSum2() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<DomainDelete> root = query.from(DomainDelete.class);
        query.multiselect(
                builder.function("to_char", String.class, root.<Date>get("createdOn"), builder.literal("YYYY-MM-DD")),
                builder.count(root.<String>get("domainName"))
        ).groupBy(
                root
        );

        return entityManager.createQuery(query).getResultList();
    }
}
