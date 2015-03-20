
package com.leantaas.rconnector;


import java.io.UnsupportedEncodingException;

import org.junit.Ignore;
import org.junit.Test;

import com.rp.rconnector.RConnectionHandler;
import com.rp.rconnector.input.RInput;
import com.rp.rconnector.output.ROutput;

public class RConnectorTest {
    
    @Ignore
    @Test
    public void connectToR() {
            RConnectionHandler handler = new RConnectionHandler();
            handler.setrServeHost("localhost");
            handler.setrServePort(6311);
            handler.setrScriptHomeDir("/opt/Rscripts/");
            handler.setTimeoutInSec(600);
            String jsonInputString = "[{\"date\":\"2011-02-26\",\"customer\":\"Rite Aid\",\"customerType\":\"Retailer\",\"pgn\":\"FS\",\"productType\":\"strips\",\"sku\":\"sku placeholder\",\"skuDesc\":\"FreeStyle Test Strips 100 ct.\",\"forecastLabel\":null,\"forecastType\":null,\"metricName\":\"avg_wkly_consumption\",\"metricValue\":12354.0,\"comment\":\"\",\"insertDate\":null,\"fiscalMonth\":2,\"fiscalYear\":2011},{\"date\":\"2011-03-26\",\"customer\":\"Rite Aid\",\"customerType\":\"Retailer\",\"pgn\":\"FS\",\"productType\":\"strips\",\"sku\":\"sku placeholder\",\"skuDesc\":\"FreeStyle Test Strips 100 ct.\",\"forecastLabel\":null,\"forecastType\":null,\"metricName\":\"avg_wkly_consumption\",\"metricValue\":12354.0,\"comment\":\"\",\"insertDate\":null,\"fiscalMonth\":3,\"fiscalYear\":2011},{\"date\":\"2011-02-26\",\"customer\":\"Rite Aid\",\"customerType\":\"Retailer\",\"pgn\":\"FS\",\"productType\":\"meters\",\"sku\":\"sku placeholder\",\"skuDesc\":\"FreeStyle Test meters 100 ct.\",\"forecastLabel\":null,\"forecastType\":null,\"metricName\":\"shipments_dollars\",\"metricValue\":12354.0,\"comment\":\"\",\"insertDate\":null,\"fiscalMonth\":2,\"fiscalYear\":2011}, {\"date\":\"2011-03-26\",\"customer\":\"Rite Aid\",\"customerType\":\"Retailer\",\"pgn\":\"FS\",\"productType\":\"meters\",\"sku\":\"sku placeholder\",\"skuDesc\":\"FreeStyle Test meters 100 ct.\",\"forecastLabel\":null,\"forecastType\":null,\"metricName\":\"shipments_dollars\",\"metricValue\":12354.0,\"comment\":\"\",\"insertDate\":null,\"fiscalMonth\":3,\"fiscalYear\":2011}]";
            String s = null;
            try {
                s = new String(jsonInputString.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            System.out.println(s);
            RInput rIn = new RInput("driver_JSONv2.R");
            rIn.getInputVariables().put("input_json_string", s);
            rIn.getOutputVars().add("forecast_refresh_output");
            try {
                ROutput output = handler.executeScript(rIn);
                if (output != null) {
                    String jsonOutPutString = output.get("forecast_refresh_output").asString();
                    if (jsonOutPutString != null) {
                        System.out.println(jsonOutPutString);
                    }
                } else {
                    System.out.println("Not successful");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

}
