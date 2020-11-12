/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.controller;

import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;
import com.sg.tubefeeding.model.FormulaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sg.tubefeeding.service.TubeFeedingService;

/**
 *
 * @author mirandabeamer
 */
@Controller
public class TubeFeedingController {
    
    TubeFeedingService service;
    
    List<Formula> formulas;
    
    @Inject
    TubeFeedingController(TubeFeedingService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/", "/displayHomePage"}, method = RequestMethod.GET)
    public String displayHomePage(Model model, HttpServletRequest request) {
        model.addAttribute("formulas", formulas);
        formulas = service.getAllFormulas();
        List<Company> companies = service.getAllCompanies();
        model.addAttribute("companies", companies);
        return "index";
    }

    @RequestMapping(value = "getCompany", method = RequestMethod.POST)
    @ResponseBody
    public List<Formula> getCompany(@RequestBody Map<String, String> companyMap) {
        Map<String, String> criteria = new HashMap<>();
        String companyIdStr = companyMap.get("company");
        if (companyIdStr.equals("default")) {
            formulas = service.getAllFormulas();
            return formulas;
        }
        int companyId = Integer.parseInt(companyIdStr);
        formulas = service.getFormulaByCompany(companyId);
        return formulas;
    }

    @RequestMapping(value = "/formulas", method = RequestMethod.GET)
    @ResponseBody
    public List<Formula> getAllFormulas() {
        formulas = service.getAllFormulas();
        return formulas;
    }

    @RequestMapping(value = "/displayFormulary", method = RequestMethod.GET)
    public String displayFormulary(Model model) {
        List<Formula> formulas = service.getAllFormulas();
        model.addAttribute("formulas", formulas);
        return "formulary";
    }
//TO DO - add administrative page
//    @RequestMapping(value = "displayAddFormulaPage", method = RequestMethod.GET)
//    public String displayAddFormulaPage(Model model) {
//        List<Company> companies = service.getAllCompanies();
//        model.addAttribute("companies", companies);
//        return "addFormula";
//    }
//
//    @RequestMapping(value = "/addFormula", method = RequestMethod.POST)
//    public String addFormula(HttpServletRequest request) {
//        Formula formula = new Formula();
//        formula.setFormulaName(request.getParameter("formulaName"));
//        String companyIdStr = request.getParameter("company");
//        int companyId = Integer.parseInt(companyIdStr);
//        Company company = service.getCompany(companyId);
//        formula.setCompany(company);
//        String concentration = request.getParameter("concentration");
//        formula.setConcentration(concentration);
//        String proteinStr = request.getParameter("protein");
//        Double protein = Double.parseDouble(proteinStr);
//        formula.setProtein(protein);
//        String choStr = request.getParameter("carbs");
//        Double cho = Double.parseDouble(choStr);
//        formula.setCarbohydrate(cho);
//        String fatStr = request.getParameter("fat");
//        Double fat = Double.parseDouble(fatStr);
//        formula.setFat(fat);
//        String fiberStr = request.getParameter("fiber");
//        Double fiber = Double.parseDouble(fiberStr);
//        formula.setFiber(fiber);
//        String potassiumStr = request.getParameter("potassium");
//        Double potassium = Double.parseDouble(potassiumStr);
//        formula.setPotassium(potassium);
//        String phosStr = request.getParameter("phosphorus");
//        Double phosphorus = Double.parseDouble(phosStr);
//        formula.setPhosphorus(phosphorus);
//        String waterStr = request.getParameter("water");
//        Double water = Double.parseDouble(waterStr);
//        formula.setWater(water);
//        String mlToRdiStr = request.getParameter("mlToRdi");
//        int mlToRdi = Integer.parseInt(mlToRdiStr);
//        formula.setMlForRdi(mlToRdi);
//        FormulaType ft = new FormulaType();
//        ft.setContainsFiber(true);
//        ft.setFormulaType(concentration);
//        formula.setFormulaType(ft);
//        service.addFormula(formula);
//        return "redirect:displayFormulary";
//    }


    @RequestMapping(value = "calculateFeeds", method = RequestMethod.POST)
    @ResponseBody
    public Formula calculateFeeds(@RequestBody Map<String, String> inputMap) {
        String hoursStr = inputMap.get("hours");
        String volumeStr = inputMap.get("rate");
        String formulaIdStr = inputMap.get("formulaId");
        int hours = Integer.parseInt(hoursStr);
        int rate = Integer.parseInt(volumeStr);
        int formulaId = Integer.parseInt(formulaIdStr);
        Formula formula = service.getFormula(formulaId);
        Formula calculatedFormula = service.calculateContinuousFeeds(formula, rate, hours);
        return calculatedFormula;
    }
}
