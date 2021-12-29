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
public class MainController {

    @Autowired
    private MagicSquare magicSquare;
    @Autowired
    CalculationRepository calculationRepository;

    @GetMapping("/form_calculation")
    public String mainMQ(Model model){
        model.addAttribute("stringForm",new StringForm());
        List<String> types = Arrays.asList("magicSquare", "subString");
        model.addAttribute("types", types);
        return "form_calculation";
    }

    @PostMapping("/calculate")
    public String calculate(@ModelAttribute("stringForm") StringForm form,
                            Model model){
        StringForm stringForm = new StringForm();
       if ((form.getType() == null)){
            model.addAttribute("message", "Выберите тип задачи");
        } else
        if(form.getType().equals("magicSquare")) {
            stringForm = magicSquare.start(form.getValue());
      }else if(form.getType().equals("subString")) {
            stringForm = magicSquare.start(form.getValue()); //////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
          stringForm.setValue(form.getValue());
          stringForm.setType(form.getType());
          List<String> types = Arrays.asList("magicSquare", "subString");
          model.addAttribute("types", types);
          model.addAttribute("stringForm", stringForm);
          return "/form_calculation";

    }

    @PostMapping("/saveBD")
    public String saveBD(@ModelAttribute("stringForm") StringForm form, Model model){
        List<String> types = Arrays.asList("magicSquare", "subString");
        model.addAttribute("types", types);
        Calculation calculation = new Calculation();
        //Date data = new Date();
        calculation.setStrings(Arrays.asList(form.getValue().split(",")));
        if(form.getType().equals("magicSquare")) {
            calculation.setType("magicSquare");
        } else {
            calculation.setType("subString");
        }
        calculation.setDate(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));
        calculationRepository.save(calculation);
        return "form_calculation";
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
    public String listBD(@PathVariable Long id, Model model){
        Calculation calculation = calculationRepository.findById(id).get();
        StringForm stringForm = new StringForm();
        stringForm.setValue(String.join(",", calculation.getStrings()));
        stringForm.setType(calculation.getType());
        List<String> types = Arrays.asList("magicSquare", "subString");
        model.addAttribute("types", types);
        model.addAttribute("stringForm", stringForm);
        return "form_calculation";
    }
}
