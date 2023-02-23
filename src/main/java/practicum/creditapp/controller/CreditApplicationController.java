package practicum.creditapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practicum.creditapp.creditenum.CreditResult;
import practicum.creditapp.entity.CreditApplication;
import practicum.creditapp.service.CreditApplicationService;
import practicum.creditapp.service.SMSService;

import java.time.LocalDate;

/**
 * This class is the controller for the Credit Application web pages.
 * It receives the incoming requests, invokes the appropriate service methods,
 * and returns the corresponding views with the model data.
 */
@Controller
public class CreditApplicationController {

    @Autowired
    private CreditApplicationService creditApplicationService;

    @Autowired
    private SMSService smsService;

    /**
     * This method is used to display the Credit Application form page.
     * @param creditApplication an object to store the form data
     * @return the name of the Thymeleaf view to be rendered
     */
    @GetMapping("/credit-application")
    public String showCreditApplicationForm(CreditApplication creditApplication) {
        return "credit-application";
    }

    /**
     * This method is used to process the submitted Credit Application form.
     * It invokes the `applyForCredit` method of the `CreditApplicationService`
     * to determine the credit result, and sends an SMS notification to the user
     * using the `SMSService`. Then it returns the corresponding view with the result data.
     * @param application an object containing the form data
     * @param model the model to be populated with the view data
     * @return the name of the Thymeleaf view to be rendered
     */
    @PostMapping("/credit-application")
    public String submitCreditApplication(@ModelAttribute("application") CreditApplication application, Model model) {
        CreditApplication result = creditApplicationService.applyForCredit(application);
        if (result.getCreditResult() == CreditResult.APPROVAL) {
            // Send SMS notification to user
            smsService.sendSMS(result.getPhoneNumber(),
                    "Congratulations! Your credit application has been approved with a limit of " + result.getCreditLimit());

            model.addAttribute("creditResult", "APPROVED");
            model.addAttribute("creditLimit", result.getCreditLimit());
            model.addAttribute("id", result.getId());
            model.addAttribute("identityNumber", result.getIdentityNumber());
            model.addAttribute("nameSurname", result.getNameSurname());
            model.addAttribute("monthlyIncome", result.getMonthlyIncome());
            model.addAttribute("phoneNumber", result.getPhoneNumber());
            model.addAttribute("birthDate", result.getBirthDate());
            model.addAttribute("creditResultString", result.getCreditResult().toString());
        } else {
            // Send SMS notification to user
            smsService.sendSMS(result.getPhoneNumber(),
                    "We regret to inform you that your credit application has been rejected.");

            model.addAttribute("creditResult", "REJECTED");
        }
        return "credit-application-result";
    }

    /**
     * This method is used to display the details of a Credit Application
     * by identity number and birth date.
     * @param identityNumber the identity number of the applicant
     * @param birthDate the birth date of the applicant
     * @param model the model to be populated with the view data
     * @return the name of the Thymeleaf view to be rendered
     */
    @GetMapping("/credit-application/{identityNumber}/{birthDate}")
    public String viewCreditApplication(@PathVariable String identityNumber, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate, Model model) {
        CreditApplication creditApplication = creditApplicationService.getCreditApplicationByIdentityNumberAndBirthDate(identityNumber, birthDate);
        if (creditApplication == null) {
            return "credit-application-not-found";
        } else {
            model.addAttribute("creditApplication", creditApplication);
            return "view-credit-application";
        }
    }

    /**
     * This method is used to display the Credit Application search form page.
     * @param model the model to be populated with the credit application object for the form and error message if any.
     * @return the name of the view to be displayed
     */
    @GetMapping("/search-credit-application")
    public String showSearchCreditApplicationForm(Model model) {
        model.addAttribute("creditApplication", new CreditApplication());
        return "search-credit-application";
    }

    /**
     * This method is used to search the credit application by identity number and birth date.
     * If the credit application is found, the Credit Application details view is displayed.
     * If the credit application is not found, the search credit application form is displayed with an error message.
     * @param creditApplication the credit application object with identity number and birth date to search for.
     * @param model the model to be populated with the credit application object if found and error message if any.
     * @return the name of the view to be displayed
     */
    @PostMapping("/search-credit-application")
    public String searchCreditApplication(@ModelAttribute("creditApplication") CreditApplication creditApplication, Model model) {
        try {
            CreditApplication result = creditApplicationService.getCreditApplicationByIdentityNumberAndBirthDate(creditApplication.getIdentityNumber(), creditApplication.getBirthDate());
            model.addAttribute("creditApplication", result);
            return "view-credit-application";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", "Credit application not found.");
            return "search-credit-application";
        }
    }

}
