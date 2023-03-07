package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.dto.IndividualDto;
import org.client.entity.ContactMedium;
import org.client.entity.Documents;
import org.client.entity.Individual;
import org.client.entity.RFPassport;
import org.client.repo.IndividualRepo;
import org.client.repo.IndividualRepo;
import org.client.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.UUID;

@Component
@AllArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    @Autowired
    IndividualRepo individualRepo;

    public IndividualServiceImpl() {}

    @Transactional
    @Override
    public void addClient(String icp, String contactsUuid, String documentsUuid, UUID rfPassportUuid,
                          Date birthDate, String countryOfBirth, String fullName, String gender,
                          String name, String patronymic, String placeOfBirth, String surname) {

        individualRepo.createUser(UUID.randomUUID().toString(), birthDate, countryOfBirth, fullName, gender, icp, name, patronymic,
                placeOfBirth, surname, contactsUuid, documentsUuid, rfPassportUuid);
    }

    @Override
    public IndividualDto getClient(String icp) { //инфа о клиенте из табл individual
        Individual individual = individualRepo.findAllFieldsByIcp(icp);
        System.out.println(individual.getSurname() + individual.getName()+ "  "+ individual.getPatronymic() + individual.getPlaceOfBirth()+"  ***");
        IndividualDto individualDto = new IndividualDto();
        individualDto.setUuid(individual.getUuid());
        individualDto.setBirthDate(individual.getBirthDate());
        individualDto.setCountryOfBirth(individual.getCountryOfBirth());
        individualDto.setFullName(individual.getFullName());
        individualDto.setGender(individual.getGender());
        individualDto.setIcp(individual.getIcp());
        individualDto.setName(individual.getName());
        individualDto.setPlaceOfBirth(individual.getPlaceOfBirth());
        individualDto.setPatronymic(individual.getPatronymic());
        individualDto.setSurname(individual.getSurname());
        individualDto.setSurname(individual.getSurname());

        ContactMedium cont = individualRepo.findContactByIndivIcp(icp);
        String contactUuuid =  cont.getUuid(); // находим uuid  ContactMedium для этого юзера

        RFPassport passp = individualRepo.findPassportUuidByIndividIcp(icp);
        UUID passpUuid = passp.getUuid(); // находим uuid паспорта для этого юзера

        Documents docum = individualRepo.findDocumentUuidByIndividIcp(icp);
        String documentuid = docum.getUuid(); // находим uuid documents для этого юзера

        individualDto.setContactsUuid(contactUuuid);
        individualDto.setDocumentsUuid(documentuid);
        individualDto.setRfPassportUuid(passpUuid);

        return individualDto;
    }

    @Override
    public List<Individual> getAll(){
       return individualRepo.findAll();
    }

    @Override
    public IndividualDto getClientByPhoneNum(String value) {
        Individual individual = individualRepo.findByPhNum(value);
        System.out.println(individual.getIcp() + "@@@");
        IndividualDto individualDto = IndividualDto.builder().icp(individual.getIcp()).name(individual.getName()).
                uuid(individual.getUuid()).build();
        return individualDto;
    }

    @Transactional
    @Override  //
    public void editClient(String icp, Date birthDate2, String countryOfBirth2, String fullName2, String gender2,
                          String name2, String patronymic2, String placeOfBirth2, String surname2) {
        Individual userFromDB = individualRepo.findAllFieldsByIcp(icp); //нашли пользователя в базе,
        System.out.println(userFromDB.getCountryOfBirth() + userFromDB.getUuid() +"  @@@@@");

        ContactMedium cont = individualRepo.findContactByIndivIcp(icp);
        String contactUuuid =  cont.getUuid(); // находим uuid  первичный ключ для ContactMedium для этого юзера

        RFPassport passp = individualRepo.findPassportUuidByIndividIcp(icp);
        UUID passpUuid = passp.getUuid(); // находим uuid первичный ключ для паспорта для этого юзера

        Documents docum = individualRepo.findDocumentUuidByIndividIcp(icp);
        String documentuid = docum.getUuid(); // находим uuid первичный ключ для documents для этого юзера

        userFromDB = Individual.builder().icp(icp).uuid(userFromDB.getUuid()).birthDate(birthDate2).
                countryOfBirth(countryOfBirth2).fullName(fullName2).gender(gender2).name(name2).
                patronymic(patronymic2).placeOfBirth(placeOfBirth2).surname(surname2).build();

        individualRepo.save(userFromDB);

        //пересохраняем айдишники  таблиц контактs , пасспорт, документы  в балице индивидуал через sql запрос
        // (средствами только spring data jpa у меня это сделать не получилось
        individualRepo.rewriteContactDocPassp(contactUuuid, documentuid, passpUuid, userFromDB.getUuid());
    }

    @Override
    public void deleteIndivid(String icp) {
        Individual ind = individualRepo.findIndividualByIcp(icp).orElse(new Individual());
        individualRepo.deleteById(ind.getUuid());
    }

}
