package com.example.todo.controller;

import java.util.List;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String list(Model model) {
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "todo/list";
    }

    @GetMapping("/new")
    public String newPage() {
        return "todo/form";
    }

    @PostMapping("/confirm")
    public String confirmPage(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(name = "done", required = false, defaultValue = "false") Boolean done,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("done", done);
        return "todo/confirm";
    }

    @PostMapping("/complete")
    public String completePage(@RequestParam("title") String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todoService.create(todo);
        return "redirect:/todo";
    }
}
