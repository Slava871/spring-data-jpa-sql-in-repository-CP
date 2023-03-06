package org.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.client.entity.Contacts.Email;
import org.client.entity.Contacts.PhoneNumber;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContactMedium {  //усредненный контакт?

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uuid;  // на один ай ди может ссылаться  и phone_number, и email

    //Двусторонний OneToOne
    @OneToOne(mappedBy = "contacts", cascade = CascadeType.ALL)
    private Individual individual;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // один ContactMedium.ContactMedium может "иметь" много имейлов
    @JoinColumn(name = "contactMediumId")
    private Collection<Email> emails;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // один ContactMedium.ContactMedium может "иметь" много тлф-номеров
    @JoinColumn(name = "contactMediumId")
    private Collection<PhoneNumber> phoneNumbers;

}
