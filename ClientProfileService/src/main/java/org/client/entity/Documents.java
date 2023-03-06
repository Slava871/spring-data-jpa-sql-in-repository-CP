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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uuid;

    private String value;

    @OneToOne(mappedBy = "documents", cascade = CascadeType.ALL)
    private Individual individual;

}
