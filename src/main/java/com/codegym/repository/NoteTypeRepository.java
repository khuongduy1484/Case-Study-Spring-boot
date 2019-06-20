package com.codegym.repository;

import com.codegym.model.NoteType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTypeRepository extends PagingAndSortingRepository<NoteType,Long> {
}
