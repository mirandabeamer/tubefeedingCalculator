/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.controller;

import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;
import com.sg.tubefeeding.model.FormulaType;
import com.sg.tubefeeding.service.tubefeedingService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import static jdk.nashorn.internal.objects.NativeMath.round;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author mirandabeamer
 */
@Controller
public class tubefeedingController {

    tubefeedingService service;
    String regimen = "bolus";
    Formula calculatedFormula = new Formula();
    List<Formula> formulas;
    int volume;
    int rate;
    int hours;
    int companyId;
    String errorMessage;

    @Inject
    tubefeedingController(tubefeedingService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/", "/displayHomePage"}, method = RequestMethod.GET)
    public String displayHomePage(Model model, HttpServletRequest request) {
        model.addAttribute("calculatedFormula", calculatedFormula);
        model.addAttribute("regimen", regimen);
        model.addAttribute("formulas", formulas);
        model.addAttribute("volume", volume);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("rate", rate);
        model.addAttribute("hours", hours);
        model.addAttribute("companyId", companyId);
        List<Company> companies = service.getAllCompanies();
        model.addAttribute("companies", companies);
        return "index";
    }

    @RequestMapping(value = "getCompany", method = RequestMethod.POST)
    @ResponseBody
    public List<Formula> getCompany(@RequestBody Map<String, String> companyMap) {
        Map<String, String> criteria = new HashMap<>();
        String companyIdStr = companyMap.get("company");
        int companyId = Integer.parseInt(companyIdStr);

        formulas = service.getFormulaByCompany(companyId);
        return formulas;
    }

    @RequestMapping(value = "/displayFormulary", method = RequestMethod.GET)
    public String displayFormulary(Model model) {
        //clear calculations when leaving page
        calculatedFormula.setFormulaName(null);
        List<Formula> formulas = service.getAllFormulas();
        model.addAttribute("formulas", formulas);
        return "formulary";
    }

    @RequestMapping(value = "displayAddFormulaPage", method = RequestMethod.GET)
    public String displayAddFormulaPage(Model model) {
        List<Company> companies = service.getAllCompanies();
        model.addAttribute("companies", companies);
        return "addFormula";
    }

    @RequestMapping(value = "/addFormula", method = RequestMethod.POST)
    public String addFormula(HttpServletRequest request) {
        Formula formula = new Formula();
        formula.setFormulaName(request.getParameter("formulaName"));
        String companyIdStr = request.getParameter("company");
        int companyId = Integer.parseInt(companyIdStr);
        Company company = service.getCompany(companyId);
        formula.setCompany(company);
        String concentration = request.getParameter("concentration");
        formula.setConcentration(concentration);
        String proteinStr = request.getParameter("protein");
        Double protein = Double.parseDouble(proteinStr);
        formula.setProtein(protein);
        String choStr = request.getParameter("carbs");
        Double cho = Double.parseDouble(choStr);
        formula.setCarbohydrate(cho);
        String fatStr = request.getParameter("fat");
        Double fat = Double.parseDouble(fatStr);
        formula.setFat(fat);
        String fiberStr = request.getParameter("fiber");
        Double fiber = Double.parseDouble(fiberStr);
        formula.setFiber(fiber);
        String potassiumStr = request.getParameter("potassium");
        Double potassium = Double.parseDouble(potassiumStr);
        formula.setPotassium(potassium);
        String phosStr = request.getParameter("phosphorus");
        Double phosphorus = Double.parseDouble(phosStr);
        formula.setPhosphorus(phosphorus);
        String waterStr = request.getParameter("water");
        Double water = Double.parseDouble(waterStr);
        formula.setWater(water);
        String mlToRdiStr = request.getParameter("mlToRdi");
        int mlToRdi = Integer.parseInt(mlToRdiStr);
        formula.setMlForRdi(mlToRdi);
        FormulaType ft = new FormulaType();
        ft.setContainsFiber(true);
        ft.setFormulaType(concentration);
        formula.setFormulaType(ft);
        service.addFormula(formula);
        return "redirect:displayFormulary";
    }

    @RequestMapping(value = "/setRegimenToBolus", method = RequestMethod.GET)
    public String setRegimenToBolus(HttpServletRequest request) {
        //reset any information entered. 
        rate = 0;
        hours = 0;
        regimen = "bolus";
        return "redirect:/";
    }

    @RequestMapping(value = "/setRegimenToContinuous", method = RequestMethod.GET)
    public String setRegimenToContinuous(HttpServletRequest request) {
        //reset any information entered. 
        rate = 0;
        hours = 0;
        regimen = "continuous";
        return "redirect:/";
    }

    @RequestMapping(value = "/calculateFeeds", method = RequestMethod.POST)
    public String calculateFeeds(HttpServletRequest request, Model model) {
        errorMessage = "";
        try {
            String rateStr = request.getParameter("rate");
            rate = Integer.parseInt(rateStr);
            String hourStr = request.getParameter("hours");
            hours = Integer.parseInt(hourStr);
        } catch (NumberFormatException e) {
            errorMessage = "Please enter valid number";
            return "redirect:/";
        }
        String rateStr = request.getParameter("rate");
        rate = Integer.parseInt(rateStr);
        String hourStr = request.getParameter("hours");
        hours = Integer.parseInt(hourStr);
        volume = rate * hours;
        try {
            String companyIdStr = request.getParameter("company");
            companyId = Integer.parseInt(companyIdStr);
        } catch(NumberFormatException e){
            errorMessage = "Please make valid company selection";
            return "redirect:/";
        }
        List<Formula> formulasByCompany = service.getFormulaByCompany(companyId);
        String formulaIdStr = request.getParameter("formula");
        int formulaId = Integer.parseInt(formulaIdStr);
        Formula formula = service.getFormula(formulaId);
        calculatedFormula = service.calculateContinuousFeeds(formula, rate, hours);
        model.addAttribute("calculatedFormula", calculatedFormula);
        List<Formula> formulas = service.getAllFormulas();
        model.addAttribute("formulas", formulas);
        return "redirect:/";

    }
}
