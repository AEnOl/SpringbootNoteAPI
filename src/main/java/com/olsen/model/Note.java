package com.olsen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Class representing the database entity Note, which corresponds to a database table.
 *
 * @author Anders Engen Olsen
 * @see java.io.Serializable
 */

@Entity
@Table(name = "notes")
// JPA annotation. Needed to automatically populate the table.
// NB! Also requires @EnableJpaAuditing in the main class.
@EntityListeners(AuditingEntityListener.class)
// Jackson annotation. Clients should not be able to supply dateCreated and dateUpdated.
@JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"},
        allowGetters = true)
public class Note implements Serializable {

    // Primary key in DB-table. AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Not null, and not empty String.
    @NotBlank
    private String noteTitle;

    @NotBlank
    private String noteContent;

    // Properties for the table column. Can't be set to null, or updated.
    @Column(nullable = false, updatable = false)
    // @Temporal = JPA-annotation, converting timestamp <-> java.util.Date.
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dateCreated;

    // Properties for the table column. Can't be null, but can be updated.
    @Column(nullable = false)
    // @Temporal = JPA-annotation, converting timestamp <-> java.util.Date.
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date dateUpdated;

    // --- Getters and setters --- //

    /**
     * @return note id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setting the notes id
     *
     * @param id note id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return note noteTitle
     */
    public String getNoteTitle() {
        return noteTitle;
    }

    /**
     * Setting note noteTitle
     *
     * @param noteTitle note noteTitle
     */
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    /**
     *
     * @return note noteContent
     */
    public String getNoteContent() {
        return noteContent;
    }

    /**
     *
     * @param noteContent note noteContent
     */
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    /**
     *
     * @return created date
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated created date
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return updated date
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     *
     * @param dateUpdated updated date
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
