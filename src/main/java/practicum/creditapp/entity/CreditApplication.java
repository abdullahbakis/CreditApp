package practicum.creditapp.entity;

import jakarta.persistence.*;
import practicum.creditapp.creditenum.CreditResult;

import java.time.LocalDate;

@Entity
@Table(name = "credit_applications")
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // unique identifier for the credit application

    @Column(name = "identity_number")
    private String identityNumber;  // unique identifier for the applicant

    @Column(name = "name_surname")
    private String nameSurname; // name and surname of the applicant

    @Column(name = "monthly_income")
    private double monthlyIncome;   // monthly income of the applicant

    @Column(name = "phone_number")
    private String phoneNumber; // phone number of the applicant

    @Column(name = "birth_date")
    private LocalDate birthDate;    // birthdate of the applicant

    @Column(name = "collateral_amount")
    private Double collateralAmount;    // collateral amount provided by the applicant, if any


    @Column(name = "credit_score")
    private Double creditScore; // credit score of the applicant

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_result")
    private CreditResult creditResult;  // result of the credit application (approval or rejection)

    @Column(name = "credit_limit")
    private Double creditLimit; // credit limit approved for the applicant

    public CreditApplication() {
        // Required by Hibernate
    }

    // constructor with arguments
    public CreditApplication(String identityNumber, String nameSurname,
                             double monthlyIncome, String phoneNumber, LocalDate birthDate,
                             Double collateralAmount, Double creditScore) {
        this.identityNumber = identityNumber;
        this.nameSurname = nameSurname;
        this.monthlyIncome = monthlyIncome;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.collateralAmount = collateralAmount;
        this.creditScore= creditScore;
    }

    // getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getCollateralAmount() {
        return collateralAmount;
    }

    public void setCollateralAmount(Double collateralAmount) {
        this.collateralAmount = collateralAmount;
    }

    public Double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Double creditScore) {
        this.creditScore = creditScore;
    }

    public CreditResult getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(CreditResult creditResult) {
        this.creditResult = creditResult;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
}