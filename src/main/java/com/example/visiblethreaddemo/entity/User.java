package com.example.visiblethreaddemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@SuperBuilder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "VT_USER")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User extends DateEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "VT_USER_SEQ", sequenceName = "VT_USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "VT_USER_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Transient
    private List<String> teamNames;

    @ManyToMany
    @JoinTable(
            name = "VT_USER_TEAM",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEAM_ID")
    )
    private Set<Team> teams = new HashSet<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Document> documents = new HashSet<>();
}
