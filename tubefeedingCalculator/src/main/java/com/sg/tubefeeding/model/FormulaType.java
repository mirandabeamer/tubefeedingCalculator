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
public class FormulaType {
    private int formulaTypeId;
    private String formulaType;
    private boolean containsFiber;

    public int getFormulaTypeId() {
        return formulaTypeId;
    }

    public void setFormulaTypeId(int formulaTypeId) {
        this.formulaTypeId = formulaTypeId;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    public boolean isContainsFiber() {
        return containsFiber;
    }

    public void setContainsFiber(boolean containsFiber) {
        this.containsFiber = containsFiber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.formulaTypeId;
        hash = 41 * hash + Objects.hashCode(this.formulaType);
        hash = 41 * hash + (this.containsFiber ? 1 : 0);
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
        final FormulaType other = (FormulaType) obj;
        if (this.formulaTypeId != other.formulaTypeId) {
            return false;
        }
        if (this.containsFiber != other.containsFiber) {
            return false;
        }
        if (!Objects.equals(this.formulaType, other.formulaType)) {
            return false;
        }
        return true;
    }
    
    
}
