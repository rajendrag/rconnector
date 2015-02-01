/*
 *  Any use of the Material is governed by the terms of the actual license
 *  agreement between LeanTaaS Inc. and the user.
 *  Copyright 2010 LeanTaaS Inc., Sunnyvale CA USA.
 *  All rights reserved. Any rights not expressly granted herein are
 *  reserved.
 */

package com.rp.rconnector.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RInput {
    
    private String scriptName;
    
    private Map<String, String> inputVarables = new HashMap<String, String>();
    
    private List<String> outputVars = new ArrayList<String>();
    
    public RInput() {
        
    }
    
    public RInput(String scriptName) {
        this.scriptName = scriptName;
    }
    
    public RInput(String scriptName, Map<String, String> inputs, List<String> outputVars) {
        this.scriptName = scriptName;
        this.inputVarables = inputs;
        this.outputVars = outputVars;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    /**
     * @return Returns the outputVars.
     */
    public List<String> getOutputVars() {
        return outputVars;
    }

    /**
     * @param outputVars The outputVars to set.
     */
    public void setOutputVars(List<String> outputVars) {
        this.outputVars = outputVars;
    }

    /**
     * @return Returns the inputVarables.
     */
    public Map<String, String> getInputVarables() {
        return inputVarables;
    }

    /**
     * @param inputVarables The inputVarables to set.
     */
    public void setInputVarables(Map<String, String> inputVarables) {
        this.inputVarables = inputVarables;
    }

}
