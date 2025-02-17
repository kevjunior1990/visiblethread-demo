package com.example.visiblethreaddemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@SuperBuilder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "VT_TEAM")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Team {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "VT_TEAM_SEQ", sequenceName = "VT_TEAM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "VT_TEAM_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
//
//    @ManyToMany(mappedBy = "teams")
//    private Set<User> users = new HashSet<>();
}
