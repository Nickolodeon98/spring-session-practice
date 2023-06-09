package com.example.springsessionpractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringBootSessionController {

  @PostMapping("/addNote")
  public String addNote(@RequestParam("note") String note, HttpServletRequest request) {
    List<String> notes = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
    if (notes == null) {
      notes = new ArrayList<>();
      request.getSession().setAttribute("NOTES_SESSION", notes);
    }
    notes.add(note);
    request.getSession().setAttribute("NOTES_SESSION", notes);

    return "redirect:/home";
  }

  @GetMapping("/home")
  public String home(Model model, HttpSession session) {
    List<String> notes = (List<String>) session.getAttribute("NOTES_SESSION");
    model.addAttribute("notesSession", notes != null ? notes : new ArrayList<>());
    return "home";
  }

  @PostMapping("/invalidate/session")
  public String destroySession(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/home";
  }
}
