package org.client.service;

import org.client.dto.IndividualDto;
import org.client.entity.ContactMedium;
import org.client.entity.Individual;
import org.client.entity.RFPassport;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IndividualService {

    void addClient(String icp, String contactsuuid, String documentsUuid, UUID rfpasportsUuid,
                   Date birthdate, String countryOfbirth, String fullName, String gender,
                   String name, String patronymic, String placeOfBirth, String surname);

    IndividualDto getClient(String icp);

    IndividualDto getClientByPhoneNum(String value);

    List<Individual> getAll();


}
