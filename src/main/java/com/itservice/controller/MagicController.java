package com.itservice.controller;

import com.itservice.db.Calculation;
import com.itservice.model.FoundForm;
import com.itservice.model.MagicSquare;
import com.itservice.model.StringForm;
import com.itservice.repository.CalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MagicController {

    @Autowired
    private MagicSquare magicSquare;
    @Autowired
    CalculationRepository calculationRepository;

    @GetMapping("/magic_square")
    public String mainMQ(Model model){
        model.addAttribute("mq",new StringForm());

        return "/magic_square";
    }

    @PostMapping("/calculateMQ")
    public String calculate(@ModelAttribute("mq") StringForm valueMQ, Model model){

        StringForm stringForm = magicSquare.start(valueMQ.getValue());
        stringForm.setValue(valueMQ.getValue());
        model.addAttribute("mq", stringForm);
        return "/magic_square";
    }

    @PostMapping("/saveBD")
    public String saveBD(@ModelAttribute("mq") StringForm valueMQ, Model model){
        System.out.println(valueMQ);
        Calculation calculation = new Calculation();
        Date data = new Date();
        calculation.setStrings(Arrays.asList(valueMQ.getValue().split(",")));
        calculation.setType("MagicSquare");
        calculation.setDate(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));

        calculationRepository.save(calculation);
        return "/magic_square";
    }
    @GetMapping("/loadBD")
    public String loadBd(Model model){
        model.addAttribute("foundForm",new FoundForm());
        return "/found";
    }

    @GetMapping("/foundBD")
    public String foundBD(@ModelAttribute("foundForm") FoundForm foundForm, Model model){

        List<Calculation> list = new ArrayList<>();
        list = calculationRepository.findAllByDateOrType(foundForm.data, foundForm.type);
        //list.add(calculationRepository.findById(foundForm.id));
        model.addAttribute("listForm", list);
        return "/found_list";
    }

    @GetMapping("/list/{id}")
    //  @PreAuthorize("hasAuthority('developers:read')")
    public String listMQ(@PathVariable Long id, Model model){
        Calculation calculation = calculationRepository.findById(id).get();
        StringForm stringForm = new StringForm();
        stringForm.setValue(String.join(",", calculation.getStrings()));
        model.addAttribute("mq", stringForm);
        return "/magic_square";
    }
}
