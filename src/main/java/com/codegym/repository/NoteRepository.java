package com.codegym.repository;

import com.codegym.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository  extends PagingAndSortingRepository<Note,Long> {
    Page<Note> findAllByTitleContaining(String title, Pageable pageable);
}
