package com.dain.controller;

import com.dain.model.ToDo;
import com.dain.service.Paginator;
import com.dain.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class ViewController {

    @Autowired
    private ToDoListService toDoListService;

    @GetMapping(value = "/todos")
    public ModelAndView list(@RequestParam(defaultValue = "1") int currentPage,
                             @RequestParam(defaultValue = "10") int display) {

        List<ToDo> list = this.toDoListService.list(currentPage, display);
        int totalCount = this.toDoListService.getTotalCount();

        Paginator.Pagination pagination = Paginator.paging(currentPage, display, totalCount);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("list", list);
        modelAndView.setViewName("todolist");

        return modelAndView;
    }

}
