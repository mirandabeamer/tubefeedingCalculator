/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.dao;

import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;
import com.sg.tubefeeding.model.FormulaType;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mirandabeamer
 */
public class tubefeedingDaoTest {

    tubefeedingDao dao;
    Formula form;

    public tubefeedingDaoTest() {
        this.form = new Formula();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("tfDao", tubefeedingDao.class);

        List<Formula> formulas = dao.getAllFormulas();
        for (Formula formula : formulas) {
            dao.deleteFormula(formula.getFormulaId());
        }
        List<Company> companies = dao.getAllCompanies();
        for (Company company : companies) {
            dao.deleteCompany(company.getCompanyId());
        }

        form.setFormulaName("Jevity 1.2");
        form.setCarbohydrate(169.4);
        form.setFat(39.3);
        form.setFiber(17);
        form.setMlForRdi(1000);
        form.setPhosphorus(1200);
        form.setPotassium(2390);
        form.setProtein(55.5);
        form.setWater(807);
        form.setFormulaId(0);

        Company company = new Company();
        company.setCompanyName("Abbott");
        dao.addCompany(company);

        form.setCompany(company);

        FormulaType ft = new FormulaType();
        ft.setFormulaType("Standard");
        ft.setContainsFiber(true);

        form.setFormulaType(ft);
        form = dao.addFormula(form);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFormula method, of class tubefeedingDao.
     */
    @Test
    public void testEditFormula() {
        form.setCarbohydrate(170);
        dao.editFormula(form);
        Formula fromDao = dao.getFormula(form.getFormulaId());
        assertEquals(form, fromDao);

    }

    /**
     * Test of getAllFormulas method, of class tubefeedingDao.
     */
    @Test
    public void testGetAllFormulas() {
        Formula f = new Formula();
        f.setFormulaName("FibersourceHN");

        Company company = new Company();
        company.setCompanyName("Abbott");
        dao.addCompany(company);
        f.setCompany(company);

        FormulaType ft = new FormulaType();
        ft.setFormulaType("Standard");
        ft.setContainsFiber(true);
        f.setFormulaType(ft);
        f = dao.addFormula(f);

        List<Formula> formulas = dao.getAllFormulas();
        assertEquals(2, formulas.size());
    }

    /**
     * Test of getAllCompanies method, of class tubefeedingDao.
     */
    @Test
    public void testDeleteFormula() {
        Formula f = new Formula();
        f.setFormulaName("Fibersource HN");

        Company company = new Company();
        company.setCompanyName("Abbott");
        dao.addCompany(company);
        f.setCompany(company);

        FormulaType ft = new FormulaType();
        ft.setFormulaType("Standard");
        ft.setContainsFiber(true);
        f.setFormulaType(ft);
        f = dao.addFormula(f);
       //first ensure the formula was added correctly 
        Formula fromDao = dao.getFormula(f.getFormulaId());
        assertEquals(fromDao, f);
        
        //then delete and ensure that it is now null
        dao.deleteFormula(f.getFormulaId());
        assertNull(dao.getFormula(f.getFormulaId()));
        
    }

    /**
     * Test of getAllCompanies method, of class tubefeedingDao.
     */
    @Test
    public void testAddGetAllCompanies() {
        Company com = new Company();
        com.setCompanyName("Nestle");
        dao.addCompany(com);
        List<Company> companies = dao.getAllCompanies();
        assertEquals(2, companies.size());
    }
    
        /**
     * Test of getAllCompanies method, of class tubefeedingDao.
     */
    @Test
    public void testEditCompany() {
        Company com = new Company();
        com.setCompanyName("Nestle");
        dao.addCompany(com);
        
        com.setCompanyName("Nestle Nutrition Company");
        dao.editCompany(com);
        Company fromDao = dao.getCompany(com.getCompanyId());
        assertEquals(com, fromDao);
    }
    
            /**
     * Test of getAllCompanies method, of class tubefeedingDao.
     */
    @Test
    public void testDeleteCompany() {
        Company com = new Company();
        com.setCompanyName("Nestle");
        dao.addCompany(com);
        
        Company fromDao = dao.getCompany(com.getCompanyId());
        assertEquals(com, fromDao);
        
        dao.deleteCompany(com.getCompanyId());
        assertNull(dao.getCompany(com.getCompanyId()));
    }

    /**
     * Test of getFormulaByCompany method, of class tubefeedingDao.
     */
    @Test
    public void testGetFormulaByCompany() {
        Formula f = new Formula();
        f.setFormulaName("FibersourceHN");

        Company com = new Company();
        com.setCompanyName("Nestle");
        dao.addCompany(com);
        f.setCompany(com);

        FormulaType ft = new FormulaType();
        ft.setFormulaType("Standard");
        ft.setContainsFiber(true);
        f.setFormulaType(ft);
        f = dao.addFormula(f);

        List<Formula> nestleFormulas = dao.getFormulaByCompany(com.getCompanyId());

        //should not contain jevity 1.2 
        assertEquals(1, nestleFormulas.size());

    }

}
