package com.csi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custId;

    @Pattern(regexp = "[A-Za-z]*", message = "Name should not contain space and special characters")
    private String custName;

    @NotNull
    private String custAddress;

    private double custAccBalance;


    @Column(unique = true)

    @Range(min = 1000000000, max = 9999999999L, message = "Contact No should be 10 digit")
    private long custContactNo;

    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date custDOB;

    @Column(unique = true)
    @Email(message = "Email must be valid")
    private String custEmailId;

    @Size(min = 4, message = "Passowrd must be 4 characters")
    private String custPassword;
}
