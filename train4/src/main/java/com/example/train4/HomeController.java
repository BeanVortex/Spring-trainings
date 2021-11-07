package com.example.train4;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.File;
import java.util.LinkedHashMap;

@Controller
public class HomeController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        var trimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmer);
    }

    LinkedHashMap<String, String> countryOptions = new LinkedHashMap<>();

    @PostConstruct
    public void initCountries() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var file = new File(ResourceUtils.getFile("classpath:static/country.json").getAbsolutePath());
            var country = mapper.readValue(file, Country[].class);
            for (var c : country)
                countryOptions.put(c.getAbbreviation(), c.getCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/")
    public String showPage(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("countryOptions", countryOptions);
        return "index";
    }

    @RequestMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student student, Model model) {
        System.out.println(student);
        model.addAttribute("student", student);
        model.addAttribute("countryOptions", countryOptions);
        return "hello";
    }

    @RequestMapping("/customer-form")
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @RequestMapping("/processCustomer")
    public String processCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
        System.out.println(result.hasErrors());
        System.out.println(result.getModel());
        System.out.println(customer.getFirstName() == null);
        System.out.println(customer.getLastName() == null);
        System.out.println(customer.getFreePass() == null);
        if (result.hasErrors())
            return "customer-form";
        else
            return "customer-confirmation";
    }
}
