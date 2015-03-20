/*
 *  Any use of the Material is governed by the terms of the actual license
 *  agreement between LeanTaaS Inc. and the user.
 *  Copyright 2010 LeanTaaS Inc., LOS GATOS CA USA.
 *  All rights reserved. Any rights not expressly granted herein are
 *  reserved.
 */
package com.rp.rconnector;

import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import com.rp.rconnector.input.RInput;
import com.rp.rconnector.output.RObject;
import com.rp.rconnector.output.ROutput;

public class RConnectionHandler {

    private String              rServeHost;
    private Integer             rServePort;
    private String              rScriptHomeDir;
    private Integer             timeoutInSec;

    /**
     * Calls the R script by assigning all the input variables, after R is done with the execution, it will read all the variables and wrap them in RObject.
     * The r scripts will timeout if the timeoutInSec is a positive non zero integer (if value is -ve, the request waits until R is done with the process).
     * The timeout kills the child Rserve process created for this call, so it is safe.
     *  
     * 
     * @param inputData
     * @return
     * @throws RTimedoutException if the timeout is set and request is taking longer than the specified time
     */
    public ROutput executeScript(RInput rInput) {
        RConnection r = null;
        ROutput output = new ROutput();
        Integer pid = -1;
        try {
            r = new RConnection(rServeHost, rServePort);
            pid = r.eval("Sys.getpid()").asInteger();
            for(Entry<String, String> input : rInput.getInputVariables().entrySet()) {
                r.assign(input.getKey(), input.getValue());
            }
            output = callR(rInput, r);
            return output;
        } catch (TimeoutException te) {
            //close the Rserv process on the server disgracefully
            closeRservProcess(pid);
            throw new RTimedoutException(te);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            r.close();
        }
    }

    /**
     * Closes the Rserve process (child process) with the given pid disgracefully.
     * 
     * @param pid
     */
    private void closeRservProcess(Integer pid) {
        if(pid != -1) {
            try {
              RConnection r1 = new RConnection(rServeHost, rServePort);
              r1.eval("tools::pskill("+pid+")");
              r1.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
        }
    }

    /**
     * @param scriptName
     * @param outputVariable
     * @param extraOutpuVariables
     * @param r
     * @return
     * @throws TimeoutException
     */
    private ROutput callR(final RInput rInput, final RConnection r) throws TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<ROutput> handler = executor.submit(new Callable<ROutput>() {
            
            public ROutput call() throws Exception {
                ROutput output = new ROutput();
                r.eval("source(\"" + rScriptHomeDir + rInput.getScriptName() + "\")");
                if (rInput.getOutputVars()!=null && rInput.getOutputVars().size() != 0) {
                    for (String extra : rInput.getOutputVars()) {
                        REXP eval = r.eval(extra);
                        RObject obj = new RObject(eval);
                        output.put(extra, obj);
                    }
                }
                return output;
            }
        });
        ROutput output = null;
        try {
            if (null != timeoutInSec && timeoutInSec > 0) {
                output = handler.get(timeoutInSec, TimeUnit.SECONDS);
            } else {
                output = handler.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return output;
    }

    public String getrServeHost() {
        return rServeHost;
    }

    public void setrServeHost(String rServeHost) {
        this.rServeHost = rServeHost;
    }

    public Integer getrServePort() {
        return rServePort;
    }

    public void setrServePort(Integer rServePort) {
        this.rServePort = rServePort;
    }

    public String getrScriptHomeDir() {
        return rScriptHomeDir;
    }

    public void setrScriptHomeDir(String rScriptHomeDir) {
        this.rScriptHomeDir = rScriptHomeDir;
    }

    /**
     * @return Returns the timeout.
     */
    public Integer getTimeoutInSec() {
        return timeoutInSec;
    }

    /**
     * @param timeout The timeout to set.
     */
    public void setTimeoutInSec(Integer timeoutInSec) {
        this.timeoutInSec = timeoutInSec;
    }
    
}
