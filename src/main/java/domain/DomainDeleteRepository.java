package domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainDeleteRepository extends JpaRepository<DomainDelete, String>,
        DomainDeleteRepositoryCustom {
}
