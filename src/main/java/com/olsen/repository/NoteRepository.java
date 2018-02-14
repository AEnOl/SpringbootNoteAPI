package com.olsen.repository;

import com.olsen.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to access the node's from the database.
 * JpaRepository defines CRUD operations on the entity
 * SimpleJpaRepository is plugged in by Spring at runtime ( save(), findOne(), findAll(), count(), delete() etc.)
 *
 * @author Anders Engen Olsen
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository
 */

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
