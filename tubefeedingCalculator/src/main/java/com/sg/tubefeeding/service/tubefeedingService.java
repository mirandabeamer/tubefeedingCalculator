/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.service;

import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;
import java.util.List;

/**
 *
 * @author mirandabeamer
 */
public interface tubefeedingService {

    public Formula addFormula(Formula formula);

    public void editFormula(Formula formula);

    public void deleteFormula(int formulaId);

    public Formula getFormula(int formulaId);

    public List<Formula> getAllFormulas();

    public Company addCompany(Company company);

    public void editCompany(Company company);

    public void deleteCompany(int companyId);

    public Company getCompany(int companyId);

    public List<Company> getAllCompanies();

    public List<Formula> getFormulaByCompany(int companyId);
    
    public Formula calculateContinuousFeeds(Formula formula, Integer rate, Integer hours);
    
}
