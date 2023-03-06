package org.client.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Individual {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String uuid;

    private String icp;
    private String name;
    private String surname;
    private String patronymic;
    private String fullName;
    private String gender;
    private String placeOfBirth;
    private String countryOfBirth;
    private Date birthDate;





    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentID")
    private Documents documents;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rfPassport")
    private RFPassport rfPassport;

    //Двусторонний OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactID")
    private ContactMedium contacts;


}
