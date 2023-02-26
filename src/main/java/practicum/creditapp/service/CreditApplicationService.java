package practicum.creditapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicum.creditapp.creditenum.CreditResult;
import practicum.creditapp.entity.CreditApplication;
import practicum.creditapp.repository.CreditApplicationRepository;

import java.time.LocalDate;

@Service
public class CreditApplicationService {

    // The credit limit multiplier used in the credit application calculation
    private static final double CREDIT_LIMIT_MULTIPLIER = 4.0;

    // The repository used to access credit applications in the database
    @Autowired
    private CreditApplicationRepository creditApplicationRepository;

    /**
     * Calculates and stores a credit application in the database based on the provided data.
     * @param creditApplication the credit application to be processed
     * @return the processed credit application
     */
    public CreditApplication applyForCredit(CreditApplication creditApplication) {
        // Retrieve credit score from a credit score service or database
        double creditScore = creditApplication.getCreditScore();

        // Determine credit result and credit limit
        double creditLimit;
        double collateralPercentage;
        CreditResult creditResult;
        if (creditScore < 500) {
            creditResult = CreditResult.REJECTION;
            creditLimit = 0.0;
        } else if (creditScore >= 500 && creditScore < 1000 && creditApplication.getMonthlyIncome() < 5000) {
            creditResult = CreditResult.APPROVAL;
            creditLimit = 10000.0;
            collateralPercentage = 0.1;
            creditLimit += creditApplication.getCollateralAmount() * collateralPercentage;
        } else if (creditScore >= 500 && creditScore < 1000 && creditApplication.getMonthlyIncome() >= 5000 && creditApplication.getMonthlyIncome() < 10000) {
            creditResult = CreditResult.APPROVAL;
            creditLimit = 20000.0;
            collateralPercentage = 0.2;
            creditLimit += creditApplication.getCollateralAmount() * collateralPercentage;
        } else if (creditScore >= 500 && creditScore < 1000 && creditApplication.getMonthlyIncome() >= 10000) {
            creditResult = CreditResult.APPROVAL;
            creditLimit = creditApplication.getMonthlyIncome() * CREDIT_LIMIT_MULTIPLIER / 2;
            collateralPercentage = 0.25;
            creditLimit += creditApplication.getCollateralAmount() * collateralPercentage;
        } else {
            creditResult = CreditResult.APPROVAL;
            creditLimit = creditApplication.getMonthlyIncome() * CREDIT_LIMIT_MULTIPLIER;
            collateralPercentage = 0.5;
            creditLimit += creditApplication.getCollateralAmount() * collateralPercentage;
        }

        // Create and store credit application in the database
        creditApplication.setCreditResult(creditResult);
        creditApplication.setCreditLimit(creditLimit);

        // Store credit application in the database using a CreditApplicationRepository
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);

        // Return the saved credit application
        return savedCreditApplication;
    }

    /**
     * Retrieves a credit application from the database by its identity number and birthdate.
     * @param identityNumber the identity number of the credit application to retrieve
     * @param birthDate the birthdate of the credit application to retrieve
     * @return the retrieved credit application
     * @throws RuntimeException if the credit application is not found in the database
     */
    public CreditApplication getCreditApplicationByIdentityNumberAndBirthDate(String identityNumber, LocalDate birthDate) {
        // Retrieve credit application from the database using a CreditApplicationRepository
        CreditApplication creditApplication = creditApplicationRepository.findByIdentityNumberAndBirthDate(identityNumber, birthDate);

        // Check if the credit application exists and return it
        if (creditApplication != null) {
            return creditApplication;
        } else {
            throw new RuntimeException("Credit application not found");
        }
    }

}

