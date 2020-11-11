/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.service;

import com.sg.tubefeeding.dao.tubefeedingDao;
import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;

import java.util.List;

/**
 *
 * @author mirandabeamer
 */
public class tubefeedingServiceImpl implements tubefeedingService {
    tubefeedingDao dao;
    
    public tubefeedingServiceImpl(tubefeedingDao dao){
        this.dao = dao;
    }

    @Override
    public Formula addFormula(Formula formula) {
        return dao.addFormula(formula);
    }

    @Override
    public void editFormula(Formula formula) {
        dao.editFormula(formula);
    }

    @Override
    public void deleteFormula(int formulaId) {
        dao.deleteFormula(formulaId);
    }

    @Override
    public Formula getFormula(int formulaId) {
        return dao.getFormula(formulaId);
    }

    @Override
    public List<Formula> getAllFormulas() {
        return dao.getAllFormulas();
    }

    @Override
    public Company addCompany(Company company) {
        return dao.addCompany(company);
    }

    @Override
    public void editCompany(Company company) {
        dao.editCompany(company);
    }

    @Override
    public void deleteCompany(int companyId) {
        dao.deleteCompany(companyId);
    }

    @Override
    public Company getCompany(int companyId) {
        return dao.getCompany(companyId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return dao.getAllCompanies();
    }

    @Override
    public List<Formula> getFormulaByCompany(int companyId) {
        return dao.getFormulaByCompany(companyId);
    }

    @Override
    public Formula calculateContinuousFeeds(Formula formula, Integer rate, Integer hours) {
        double concentration = Double.parseDouble(formula.getFormulaType().getFormulaType());
        double calories = concentration * rate * hours;
        String calStr = String.valueOf(calories);
        int ml = (rate * hours);
        Double liters = Double.valueOf(ml);
        liters = liters/1000;
        formula.setConcentration(calStr);
        double fat = Math.round(formula.getFat() * liters * 100)/100;
        formula.setFat(fat);
        double fiber = Math.round(formula.getFiber() * liters * 100)/100;
        formula.setFiber(fiber);
        double phos = Math.round(formula.getPhosphorus()  * liters * 100)/100;
        formula.setPhosphorus(phos);
        double potassium = Math.round(formula.getPotassium() * liters * 100)/100;
        formula.setPotassium(potassium);
        double protein = Math.round(formula.getProtein() * liters * 100)/100;
        formula.setProtein(protein);
        double water = Math.round(formula.getWater() * liters * 100)/100;
        formula.setWater(water);
        double carbs = Math.round(formula.getCarbohydrate() * liters *100)/100;
        formula.setCarbohydrate(carbs);
        return formula;
        
    }

    
}
