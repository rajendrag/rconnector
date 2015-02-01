/*
 *  Any use of the Material is governed by the terms of the actual license
 *  agreement between LeanTaaS Inc. and the user.
 *  Copyright 2010 LeanTaaS Inc., Sunnyvale CA USA.
 *  All rights reserved. Any rights not expressly granted herein are
 *  reserved.
 */

package com.rp.rconnector.output;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPGenericVector;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;

import com.rp.rconnector.RTypeConverterException;


/**
 * 
 * 
 */
public class RObject {
    
    protected REXP rexp;
    
    public RObject() {}
    
    public RObject(REXP rexp) {
        this.rexp = rexp;
    }
    
    public String[][] asStringMatrix() {
        String[][] out = null;
        try {
            if(rexp instanceof REXPGenericVector) {
                out = parseAndTransposeRVector(rexp.asList());
            } else {
                throw new RTypeConverterException();
            }
        } catch (REXPMismatchException e) {
            throw new RTypeConverterException(e);
        }
        return out;
    }
    
    public String asString() {
        try {
            return rexp.asString();
        } catch (REXPMismatchException e) {
            throw new RTypeConverterException(e);
        }
    }

    public REXP getRawRObject() {
        return rexp;
    }
    
    /**
     * Coverts to basic types String, Integer, Double etc
     * @param to
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T asBasic(Class<T> to) {
        try {
            if(to.isAssignableFrom(String.class)) {
                return (T) rexp.asString();
            } 
            if(to.isAssignableFrom(Double.class)) {
                return (T) new Double(rexp.asDouble());
            }
            if(to.isAssignableFrom(Integer.class)) {
                return (T) new Integer(rexp.asInteger());
            }
            
        } catch (REXPMismatchException e) {
            throw new RTypeConverterException(e);
        }
        return null;
    }
    
    private String[][] parseAndTransposeRVector(RList bestOp) throws REXPMismatchException {
        int cols = bestOp.size();
        int rows = bestOp.at(0).length();
        String[][] s = new String[cols][];
        for (int i = 0; i < cols; i++) {
            s[i] = bestOp.at(i).asStrings();
        }
        // transposed array, (preffered speed over memory, we could try in place
        // transpose.)
        String[][] transposed = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[i][j] = s[j][i];
            }
        }
        return transposed;
    }
}
