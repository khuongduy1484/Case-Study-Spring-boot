package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.model.NoteType;
import com.codegym.service.Impl.NoteSeviceImpl;
import com.codegym.service.Impl.NoteTypeSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class NoteController {
    @Autowired
    private NoteSeviceImpl noteSeviceImpl;
    @Autowired
    private NoteTypeSeviceImpl noteTypeSeviceImpl;

    @ModelAttribute("notetype")
    public Iterable<NoteType> noteTypes() {
        return noteTypeSeviceImpl.fillAll();
    }

    @GetMapping("note")
    public ModelAndView listNote(@RequestParam("search") Optional<String> search, @PageableDefault(value = 5) Pageable pageable) {
        Page<Note> notes;
        if (search.isPresent()) {
            notes = noteSeviceImpl.findAllByTitleContaining(search.get(), pageable);
        } else {
            notes = noteSeviceImpl.fillAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("notes/list");
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    @GetMapping("create-note")
    public ModelAndView createNOte() {
        ModelAndView modelAndView = new ModelAndView("notes/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("create-note")
    public ModelAndView createNote(@Validated @ModelAttribute("note") Note note, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("notes/create");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("notes/create");
            noteSeviceImpl.save(note);
            return modelAndView;
        }
    }

    @GetMapping("edit-note/{id}")
    public ModelAndView editNote(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("notes/edit");
        modelAndView.addObject("note", noteSeviceImpl.findById(id));
        return modelAndView;
    }
    @PostMapping("edit-note")
    public ModelAndView updateNote(@Validated @ModelAttribute("note") Note note, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("notes/edit");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("notes/edit");
            noteSeviceImpl.save(note);
            return modelAndView;
        }
    }
    @GetMapping("delete-note/{id}")
    public ModelAndView deleteNote(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("notes/delete");
        modelAndView.addObject("note", noteSeviceImpl.findById(id));
        return modelAndView;
    }
    @PostMapping("delete-note")
    public String deleteNotes(@ModelAttribute("note") Note note){
        noteSeviceImpl.delete(note);
        ModelAndView modelAndView = new ModelAndView("notes/delete");
        return "redirect:note";
    }

}
