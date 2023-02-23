package practicum.creditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practicum.creditapp.entity.CreditApplication;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
    /**
     * Finds a credit application by its identity number and birthdate.
     * @param identityNumber the identity number of the credit application
     * @param birthDate the birthdate of the credit application
     * @return the credit application with the given identity number and birthdate, or null if not found
     */
    @Query("SELECT c FROM CreditApplication c WHERE c.identityNumber = :identityNumber AND c.birthDate = :birthDate")
    CreditApplication findByIdentityNumberAndBirthDate(@Param("identityNumber") String identityNumber, @Param("birthDate") LocalDate birthDate);
}
