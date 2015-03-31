
package com.leantaas.rconnector;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.rp.rconnector.RConnectionHandler;
import com.rp.rconnector.input.RInput;
import com.rp.rconnector.output.ROutput;

public class RConnectorTest {
    public static void main(String s[]) {
        RConnectionHandler handler = new RConnectionHandler();
        handler.setrScriptHomeDir("/opt/rp/Rscripts/test/");
        handler.setrServeHost("localhost");
        handler.setrServePort(6311);
        executeScript(handler);
    }

    private static String readFile(String fileName) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    private static void executeScript(RConnectionHandler handler) {
        String json = readFile("FLOAT_POOL.json");
        //System.out.println(json);
        RInput rIn = new RInput("floatPoolWithSkills.r");
        rIn.getInputVariables().put("float_pool_data", json);
        rIn.getOutputVars().add("op_file");
        rIn.getOutputVars().add("type_df");
        ROutput output = handler.executeScript(rIn);
        String[][] s = output.get("op_file").asStringMatrix();
        String[][] types = output.get("type_df").asStringMatrix();
        for(String[] r : s) {
            System.out.println(Arrays.toString(r));
        }
    }

}
