/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.dao;

import com.sg.tubefeeding.model.Company;
import com.sg.tubefeeding.model.Formula;
import com.sg.tubefeeding.model.FormulaType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author mirandabeamer
 */
public class TubeFeedingDaoDbImpl implements TubeFeedingDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static String SQL_INSERT_FORMULA
            = "insert into formula (formula_name, protein, carbohydrates, fat, water, potassium, phosphorus, ml_to_rdi, formula_type_id, company_id, fiber) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String SQL_INSERT_COMPANY
            = "insert into company (company_name) values (?)";

    private static String SQL_INSERT_FORMULA_TYPE
            = "insert into formula_type (formula_type, contains_fiber) values (?, ?)";
    
    private static String SQL_UPDATE_FORMULA
            ="update formula set formula_name = ?, protein = ?, carbohydrates = ?, fat = ?, water = ?, potassium = ?, "
            + "phosphorus =?, ml_to_rdi = ?, formula_type_id = ?, company_id = ?, fiber = ? where formula_id=?";
    
    private static String SQL_UPDATE_COMPANY
            ="update company set company_name = ? where company_id = ?";
    
    private static String SQL_DELETE_FORMULA
            ="delete from formula where formula_id = ?";
    
    private static String SQL_DELETE_COMPANY
            ="delete from company where company_id = ?";

    private static String SQL_GET_FORMULA
            = "select formula_id, formula_name, protein, carbohydrates, fat, water, "
            + "potassium, phosphorus, ml_to_rdi, formula_type_id, company_id, fiber from formula where formula_id = ?";

    private static String SQL_GET_ALL_FORMULAS
            = "select * from formula";

    private static String SQL_GET_COMPANY
            = "select company_id, company_name from company where company_id =?";

    private static String SQL_SELECT_COMPANY_BY_FORMULA_ID
            = "select c.company_id, company_name from company c join formula f on "
            + "f.company_id = c.company_id where f.formula_id = ?";

    private static String SQL_SELECT_FORMULA_TYPE_BY_FORMULA_ID
            = "select ft.formula_type_id, formula_type, contains_fiber from formula_type ft "
            + "join formula f on f.formula_type_id = ft.formula_type_id where formula_id = ?";

    private static String SQL_GET_ALL_COMPANIES
            = "select * from company";

    private static String SQL_SELECT_FORMULAS_BY_COMPANY
            = "select formula_id, formula_name, protein, carbohydrates, fat, water, potassium, "
            + "phosphorus, ml_to_rdi, fiber from formula f join company c on c.company_id = "
            + "f.company_id where c.company_id = ?";

    @Override
    public Formula getFormula(int formulaId) {
        try {
            Formula formula = jdbcTemplate.queryForObject(SQL_GET_FORMULA, new formulaMapper(), formulaId);
            Company company = jdbcTemplate.queryForObject(SQL_SELECT_COMPANY_BY_FORMULA_ID, new companyMapper(), formulaId);
            formula.setCompany(company);
            FormulaType ft = jdbcTemplate.queryForObject(SQL_SELECT_FORMULA_TYPE_BY_FORMULA_ID, new formulaTypeMapper(), formulaId);
            formula.setFormulaType(ft);
            return formula;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Formula> getAllFormulas() {
        List<Formula> formulas = jdbcTemplate.query(SQL_GET_ALL_FORMULAS, new formulaMapper());
        return associateFormulasWithCompanyAndType(formulas);
    }

    @Override
    public Company getCompany(int companyId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_COMPANY, new companyMapper(), companyId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        return jdbcTemplate.query(SQL_GET_ALL_COMPANIES, new companyMapper());
    }

    @Override
    public List<Formula> getFormulaByCompany(int companyId) {
        List<Formula> formulas = jdbcTemplate.query(SQL_SELECT_FORMULAS_BY_COMPANY, new formulaMapper(), companyId);
        return associateFormulasWithCompanyAndType(formulas);
    }

    @Override
    public Formula addFormula(Formula formula) {
        jdbcTemplate.update(SQL_INSERT_FORMULA_TYPE,
                formula.getFormulaType().getFormulaType(),
                formula.getFormulaType().isContainsFiber());

        int formulaTypeId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        formula.getFormulaType().setFormulaTypeId(formulaTypeId);
        jdbcTemplate.update(SQL_INSERT_FORMULA,
                formula.getFormulaName(),
                formula.getProtein(),
                formula.getCarbohydrate(),
                formula.getFat(),
                formula.getWater(),
                formula.getPotassium(),
                formula.getPhosphorus(),
                formula.getMlForRdi(),
                formula.getFormulaType().getFormulaTypeId(),
                formula.getCompany().getCompanyId(),
                formula.getFiber());
        int formulaId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        formula.setFormulaId(formulaId);
        return formula;
    }

    @Override
    public void editFormula(Formula formula) {
        jdbcTemplate.update(SQL_UPDATE_FORMULA, 
                formula.getFormulaName(),
                formula.getProtein(),
                formula.getCarbohydrate(),
                formula.getFat(),
                formula.getWater(),
                formula.getPotassium(),
                formula.getPhosphorus(),
                formula.getMlForRdi(),
                formula.getFormulaType().getFormulaTypeId(),
                formula.getCompany().getCompanyId(),
                formula.getFiber(),
                formula.getFormulaId());
    }

    @Override
    public void deleteFormula(int formulaId) {
        jdbcTemplate.update(SQL_DELETE_FORMULA, formulaId);
    }

    @Override
    public Company addCompany(Company company) {
        jdbcTemplate.update(SQL_INSERT_COMPANY, 
                company.getCompanyName());
        int companyId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        company.setCompanyId(companyId);
        return company;
    }

    @Override
    public void editCompany(Company company) {
        jdbcTemplate.update(SQL_UPDATE_COMPANY, 
                company.getCompanyName(), 
                company.getCompanyId());
    }

    @Override
    public void deleteCompany(int companyId) {
        jdbcTemplate.update(SQL_DELETE_COMPANY, companyId);
    }

    private static final class formulaMapper implements RowMapper<Formula> {

        @Override
        public Formula mapRow(ResultSet rs, int i) throws SQLException {
            Formula formula = new Formula();
            formula.setFormulaId(rs.getInt("formula_id"));
            formula.setFormulaName(rs.getString("formula_name"));
            formula.setProtein(rs.getDouble("protein"));
            formula.setCarbohydrate(rs.getDouble("carbohydrates"));
            formula.setFat(rs.getDouble("fat"));
            formula.setWater(rs.getDouble("water"));
            formula.setPotassium(rs.getDouble("potassium"));
            formula.setPhosphorus(rs.getDouble("phosphorus"));
            formula.setMlForRdi(rs.getInt("ml_to_rdi"));
            formula.setFiber(rs.getDouble("fiber"));
            return formula;
        }
    }

    private static final class companyMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet rs, int i) throws SQLException {
            Company company = new Company();
            company.setCompanyId(rs.getInt("company_id"));
            company.setCompanyName(rs.getString("company_name"));
            return company;
        }
    }

    private static final class formulaTypeMapper implements RowMapper<FormulaType> {

        @Override
        public FormulaType mapRow(ResultSet rs, int i) throws SQLException {
            FormulaType ft = new FormulaType();
            ft.setFormulaTypeId(rs.getInt("formula_type_id"));
            ft.setFormulaType(rs.getString("formula_type"));
            ft.setContainsFiber(rs.getBoolean("contains_fiber"));
            return ft;
        }

    }

    private List<Formula> associateFormulasWithCompanyAndType(List<Formula> formulas) {
        for (Formula formula : formulas) {
            Company company = jdbcTemplate.queryForObject(SQL_SELECT_COMPANY_BY_FORMULA_ID, new companyMapper(), formula.getFormulaId());
            formula.setCompany(company);
            FormulaType ft = jdbcTemplate.queryForObject(SQL_SELECT_FORMULA_TYPE_BY_FORMULA_ID, new formulaTypeMapper(), formula.getFormulaId());
            formula.setFormulaType(ft);
        }
        return formulas;
    }

}
