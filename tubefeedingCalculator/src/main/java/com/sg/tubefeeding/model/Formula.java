/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.tubefeeding.model;

import java.util.Objects;

/**
 *
 * @author mirandabeamer
 */
public class Formula {
    private int formulaId;
    private String formulaName;
    private String concentration;
    private double protein;
    private double carbohydrate;
    private double fat;
    private double water; 
    private double potassium;
    private double phosphorus;
    private int mlForRdi;
    private double fiber;
    private Company company;
    private FormulaType formulaType;

    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public int getMlForRdi() {
        return mlForRdi;
    }

    public void setMlForRdi(int mlForRdi) {
        this.mlForRdi = mlForRdi;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public FormulaType getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(FormulaType formulaType) {
        this.formulaType = formulaType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.formulaId;
        hash = 59 * hash + Objects.hashCode(this.formulaName);
        hash = 59 * hash + Objects.hashCode(this.concentration);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.protein) ^ (Double.doubleToLongBits(this.protein) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.carbohydrate) ^ (Double.doubleToLongBits(this.carbohydrate) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.fat) ^ (Double.doubleToLongBits(this.fat) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.water) ^ (Double.doubleToLongBits(this.water) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.potassium) ^ (Double.doubleToLongBits(this.potassium) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.phosphorus) ^ (Double.doubleToLongBits(this.phosphorus) >>> 32));
        hash = 59 * hash + this.mlForRdi;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.fiber) ^ (Double.doubleToLongBits(this.fiber) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.company);
        hash = 59 * hash + Objects.hashCode(this.formulaType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Formula other = (Formula) obj;
        if (this.formulaId != other.formulaId) {
            return false;
        }
        if (Double.doubleToLongBits(this.protein) != Double.doubleToLongBits(other.protein)) {
            return false;
        }
        if (Double.doubleToLongBits(this.carbohydrate) != Double.doubleToLongBits(other.carbohydrate)) {
            return false;
        }
        if (Double.doubleToLongBits(this.fat) != Double.doubleToLongBits(other.fat)) {
            return false;
        }
        if (Double.doubleToLongBits(this.water) != Double.doubleToLongBits(other.water)) {
            return false;
        }
        if (Double.doubleToLongBits(this.potassium) != Double.doubleToLongBits(other.potassium)) {
            return false;
        }
        if (Double.doubleToLongBits(this.phosphorus) != Double.doubleToLongBits(other.phosphorus)) {
            return false;
        }
        if (this.mlForRdi != other.mlForRdi) {
            return false;
        }
        if (Double.doubleToLongBits(this.fiber) != Double.doubleToLongBits(other.fiber)) {
            return false;
        }
        if (!Objects.equals(this.formulaName, other.formulaName)) {
            return false;
        }
        if (!Objects.equals(this.concentration, other.concentration)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        if (!Objects.equals(this.formulaType, other.formulaType)) {
            return false;
        }
        return true;
    }



    

}
