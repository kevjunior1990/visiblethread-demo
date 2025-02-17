package com.example.visiblethreaddemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@SuperBuilder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "VT_DOCUMENT")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Document extends DateEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "VT_DOCUMENT_SEQ", sequenceName = "VT_DOCUMENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "VT_DOCUMENT_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "WORD_COUNT", nullable = false)
    private Long wordCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
