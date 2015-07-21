# rconnector
Easy to use API to connect and execute R scripts from Java. Also handles the timeouts well.

Usage: Add dist/rconnector as a dependency in project

Define spring bean or initialize it as a stand alone class

    <bean class="com.leantaas.rconnector.RConnectionHandler" id="rConnectionHandler"> 
        <property name="rServeHost" value="${r.serve.host:localhost}"/>
        <property name="rServePort" value="${r.serve.port:6311}"/> 
        <property name="rScriptHomeDir" value="{r.scripts.home.directory:/opt/Rscripts/rProject/}"/>
        <property name="timeoutInSec" value="${r.script.timeout:900}"></property>
    </bean>

Inject in your Service You could either Autowire it or provide a setter method (or create a new instance)

    @Autowired private RConnectionHandler rConnectionHandler;

Ready to use

    //create input with input and output variables 
    RInput rIn = new RInput("opt_all.r"); 
    rIn.getInputVarables().put("model_data", modelData); 
    rIn.getOutputVars().add("best_op"); 
    rIn.getOutputVars().add("RELAXED"); 
    rIn.getOutputVars().add("json_opt"); 
    try {
        //call R       
        ROutput rOut = rConnectionHandler.executeScript(rIn);
        //read output       
        String[][] output = rOut.get("best_op").asStringMatrix();
        Boolean relaxed = rOut.get("RELAXED").asBasic(Boolean.class);
        String jsonOpt = rOut.get("json_opt").asString();
        //process output 
    } catch (RTimedoutException rte) {
        // this is a RuntimeException, catch if you want to do something with timed out requests
        logger.warn("Request Timedout for: {}", rIn);
    }

Note:
	Avoid using "setwd" in R scripts because Rserve start its own R session and working directory. "rconnector" paasses the "base_dir" with rScriptHomeDir as a value, we can make use of it for relative paths.
	 
Coming soon:

    YourPojo obj = rOut.get(“json_out”).asObject(YourPojo.class);


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/rajendrag/rconnector/trend.png)](https://bitdeli.com/free "Bitdeli Badge")



[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/rajendrag/rconnector/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

