package org.client.repo;

import org.client.entity.ContactMedium;
import org.client.entity.Documents;
import org.client.entity.Individual;
import org.client.entity.RFPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepo extends JpaRepository<Individual, String>{

    Optional<Individual> findIndividualByIcp(String icp); //находит только имя, фио, uuid. Остальные поля не находит...

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // СОЗДАНИЕ НОВОГО ПОЛЬЗОВАТЕЛЯ, используем sql-запрос с параметрами
            value = "insert into public.individual values " +
                    "(:uuid, :birthDate, :countryOfBirth, :fullName, :gender, :icp, :name, :patronymic, " +
                    ":placeOfBirth, :surname, :contactsUuid, :documentsuuid, :rfPassportUuid)",
            nativeQuery = true)//
    void createUser(@Param("uuid") String uuid, @Param("birthDate") Date birthDate, @Param("countryOfBirth") String countryOfBirth,
                    @Param("fullName") String fullName, @Param("gender") String gender, @Param("icp") String icp, @Param("name") String name,
                    @Param("patronymic") String patronymic, @Param("placeOfBirth") String placeOfBirth, @Param("surname") String surname,
                    @Param("contactsUuid") String contactsUuid, @Param("documentsuuid") String documentsuuid, @Param("rfPassportUuid") UUID rfPassportUuid);

    //  ищем пользователя по номеру телефона  (джоин из трех таблиц через jpql)
    @Query("from Individual as indiv join fetch indiv.contacts as cont join fetch cont.phoneNumbers as phnum  where phnum.value = :number")
    Individual findByPhNum(@Param("number") String number);

    //  ищем все поля пользователя по icp
    @Query(value = "SELECT * FROM public.individual where icp = :icp", nativeQuery = true)
    Individual findAllFieldsByIcp(@Param("icp") String icp);

    //  ищем uuid contact_medium юзера по icp юзера
    @Query("from ContactMedium as cont join fetch cont.individual as indiv where indiv.icp = :icp")
    ContactMedium findContactByIndivIcp(@Param("icp") String icp);

    //  ищем uuid passport юзера по icp юзера
    @Query("from RFPassport as passp join fetch passp.individual as indiv where indiv.icp = :icp")
    RFPassport findPassportUuidByIndividIcp(@Param("icp") String icp);

    //  ищем uuid document юзера по icp юзера
    @Query("from Documents as doc join fetch doc.individual as indiv where indiv.icp = :icp")
    Documents findDocumentUuidByIndividIcp(@Param("icp") String icp);

    //  пересохраняем поля: контакты, документы, паспорт в табл. индивидуал
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update public.individual set contactid = :contactUuid, documentid = :documentUuid, rf_passport = :passpId where uuid = :individUuid",
            nativeQuery = true)
    void rewriteContactDocPassp(@Param("contactUuid") String contactUuid, @Param("documentUuid") String documentUuid,
                                      @Param("passpId") UUID passpId, @Param("individUuid") String individUuid);

    //  test method
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update public.individual set name = ? where uuid = ?",
            nativeQuery = true)
    void testMeth(String name, String uuid);

}
