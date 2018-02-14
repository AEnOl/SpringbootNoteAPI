package com.olsen.controller;

import com.olsen.model.Note;
import com.olsen.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for the REST API, providing CRUD-operations for a Note.
 *
 * @author Anders Engen Olsen
 */

/* @RestController = @Controller + @ResponseBody
 * @Controller = This is a Controller.
 * @ResponseBody = The return value of methods should be used as the response body of a request.
 * @CrossOrigin = Enabling CORS requests
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/api")

public class NoteController {

    // Automatically instantiated at runtime
    @Autowired
    private NoteRepository noteRepository;

    /**
     * Getting all notes in the database.
     *
     * @return All the notes in the database.
     */
    @GetMapping("/notes")
    public Iterable<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Creating a new note in the database.
     *
     * @param note a new note
     * @return 400 BadRequest if not @Valid.
     */
    @PostMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        noteRepository.save(note);

        return ResponseEntity.ok().body(note);
    }

    /**
     * Getting a note by id from the database.
     *
     * @param noteId the id for the note
     * @return HTTP status code, and Note if found
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);

        // Not found, returning 404 Not Found
        if (note == null)
            return ResponseEntity.notFound().build();

        // Found
        return ResponseEntity.ok().body(note);
    }

    /**
     * Updating a note with the given id
     *
     * @param noteId      The id of the note to update
     * @param noteDetails The content to be updated
     * @return HTTP status code
     */
    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody Note noteDetails) {
        Note note = noteRepository.findOne(noteId);

        // Not found
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        // Found
        note.setNoteTitle(noteDetails.getNoteTitle());
        note.setNoteContent(noteDetails.getNoteContent());

        Note updatedNote = noteRepository.save(note);
        return ResponseEntity.ok(updatedNote);
    }

    /**
     * Deleting a given note by id
     *
     * @param noteId The id of the note to delete
     * @return HTTP status code
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }

}
