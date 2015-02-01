/*
 *  Any use of the Material is governed by the terms of the actual license
 *  agreement between LeanTaaS Inc. and the user.
 *  Copyright 2010 LeanTaaS Inc., LOS GATOS CA USA.
 *  All rights reserved. Any rights not expressly granted herein are
 *  reserved.
 */
package com.rp.rconnector.output;

import java.util.HashMap;
import java.util.Map;

public class ROutput {

    private Map<String, RObject> outputMap = new HashMap<String, RObject>();

    public void put(String key, RObject value) {
        outputMap.put(key, value);
    }
    
    public RObject get(String key) {
        return outputMap.get(key);
    }
    @Override
    public String toString() {
        return "";//printGrid();
    }
    
/*    public String printGrid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                sb.append(schedule[i][j]).append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }*/

}
