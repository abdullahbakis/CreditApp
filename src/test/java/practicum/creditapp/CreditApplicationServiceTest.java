package practicum.creditapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import practicum.creditapp.entity.CreditApplication;
import practicum.creditapp.service.CreditApplicationService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditApplicationServiceTest {

    @Autowired
    private CreditApplicationService creditApplicationService;

    @Test
    public void testApplyForCredit() {

        // Create a new CreditApplication with the required information
        CreditApplication creditApplication = new CreditApplication("45134236901",
                "Abdullah Bakış", 10000.0, "+905319341672",
                LocalDate.of(1997,10,25), 1000.0, 1000.0);

        // Call the applyForCredit method to save the CreditApplication
        CreditApplication savedCreditApplication = creditApplicationService.applyForCredit(creditApplication);

        // Check that the CreditApplication has been saved and its fields match the expected values
        assertNotNull(savedCreditApplication.getId());
        assertEquals(creditApplication.getIdentityNumber(), savedCreditApplication.getIdentityNumber());
        assertEquals(creditApplication.getNameSurname(), savedCreditApplication.getNameSurname());
        assertEquals(creditApplication.getMonthlyIncome(), savedCreditApplication.getMonthlyIncome(), 0.0);
        assertEquals(creditApplication.getPhoneNumber(), savedCreditApplication.getPhoneNumber());
        assertEquals(creditApplication.getBirthDate(), savedCreditApplication.getBirthDate());
        assertNotNull(savedCreditApplication.getCreditResult());
        assertNotNull(savedCreditApplication.getCreditLimit());

        // Print out the saved CreditApplication for reference
        System.out.println("testApplyForCredit: Saved credit application:");
        System.out.println("id: " + savedCreditApplication.getId());
        System.out.println("identityNumber: " + savedCreditApplication.getIdentityNumber());
        System.out.println("nameSurname: " + savedCreditApplication.getNameSurname());
        System.out.println("monthlyIncome: " + savedCreditApplication.getMonthlyIncome());
        System.out.println("phoneNumber: " + savedCreditApplication.getPhoneNumber());
        System.out.println("birthDate: " + savedCreditApplication.getBirthDate());
        System.out.println("creditResult: " + savedCreditApplication.getCreditResult());
        System.out.println("creditLimit: " + savedCreditApplication.getCreditLimit());
    }

}
