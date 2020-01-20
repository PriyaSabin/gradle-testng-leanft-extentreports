package letzautomate.utilities;

import com.hp.lft.report.Reporter;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.unittesting.TestNgUnitTestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URI;

public class LeanFTManager {
    public static final Logger LOGGER = Logger.getLogger(LeanFTManager.class);

    @BeforeSuite(alwaysRun=true)
    public void initializeLeanFT() {

        ModifiableSDKConfiguration modifiableSDKConfiguration = null;
            try{
                modifiableSDKConfiguration = new ModifiableSDKConfiguration();
                modifiableSDKConfiguration.setServerAddress(new URI("ws://localhost:5095"));
                LOGGER.info("Initialize Lean FT SDK with   default configuration.");
                SDK.init(modifiableSDKConfiguration);
                LOGGER.info("Initialization complete.");
            }catch(Exception e){
                LOGGER.info("There was an error in initializing SDK");
                e.printStackTrace();
            }

        }

        /*@AfterSuite(alwaysRun=true)
     public void cleanUpLeanFT() {
            try{
                SDK.cleanup();
                LOGGER.info("Cleanup Lean FT SDK is Completed");
                LOGGER.info("Initialization complete.");
            }catch(Exception e){
                LOGGER.info("There was an error in cleanup LeanFT SDK");
                e.printStackTrace();
            }
        }*/
    }

