package letzautomate.pages.common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.hp.lft.report.Reporter;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.java.Button;
import com.hp.lft.sdk.java.Editor;
import com.hp.lft.sdk.java.Window;
import letzautomate.utilities.ExtentLink;
import letzautomate.utilities.ExtentManager;
import letzautomate.utilities.InstancesManager;
import letzautomate.utilities.LeanFTManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.security.SecureRandom;


public class LeanFTBasePage extends LeanFTManager {

    public final static Logger LOGGER = Logger.getLogger(LeanFTBasePage.class);


    ExtentTest extentTest;
    ExtentLink extentLink;
    ExtentManager extentManager = new ExtentManager();


    public void enterTextEditor(Editor editor, String textToEnter) {

        try {
            LOGGER.info("Befor Enter Text in :: " + editor.getAttachedText());
            editor.setText(textToEnter);
            LOGGER.info("after Enter Text in :: "+ editor.getAttachedText());
        }catch(Exception e) {
            LOGGER.info("There was an exception in entering text");
            e.printStackTrace();
        }
    }

    public void buttonClick(Button button) {
        try {
            LOGGER.info("Before click the button :: " + button.getLabel());
            button.click();
            LOGGER.info("After click the button :: "+  button.getLabel());
        }catch(Exception e) {
            LOGGER.info("There was an exception in clicking the button");
            e.printStackTrace();
        }
    }

    public ExtentTest getExtentTest() {
        Long threadID = Thread.currentThread().getId();
        return InstancesManager.testcaseMap.get(threadID);
    }
    public void report(Window window, String status, String message){

        SoftAssert softAssert = new SoftAssert();
        extentTest = getExtentTest();

        try{
            if(status.equalsIgnoreCase("info")){
                String screenshotPath = extentManager.takeScreenshot(window, "INFO_" + randomString(5) + "_");
                extentTest.log(Status.INFO, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("pass")){
                String screenshotPath = extentManager.takeScreenshot(window, "PASS_" + randomString(5) + "_");
                extentTest.log(Status.PASS, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("fail")){
                String screenshotPath = extentManager.takeScreenshot(window, "FAIL_" + randomString(5) + "_");
                extentTest.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("skip")){
                String screenshotPath = extentManager.takeScreenshot(window, "SKIP_" + randomString(5) + "_");
                extentTest.log(Status.SKIP, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else {
                String screenshotPath = extentManager.takeScreenshot(window, "FAIL_" + randomString(5) + "_");
                extentTest.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                softAssert.fail(message, new Throwable());
            }
        }catch(Exception e){
            LOGGER.error("Report Method Exception :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reportLink(String status, String linkText, String linkUrl) {
        extentTest = getExtentTest();
        extentLink = new ExtentLink();
        if(linkUrl.contains("extent-reports")){
            linkUrl = linkUrl.split("extent-reports\\\\")[1];
        }
        extentLink.setLink(linkText, linkUrl);

        if (status.equalsIgnoreCase("info")){
            extentTest.log(Status.INFO, extentLink);
        }else if(status.equalsIgnoreCase("error")){
            extentTest.log(Status.ERROR, extentLink);
            Assert.assertTrue(false, linkText);
        }else {
            extentTest.log(Status.DEBUG, extentLink);
        }


    }

    public String randomString(int length){
        LOGGER.info("Before Random String creation");
        String str = "012345679ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i< length; i++ ){
            sb.append(str.charAt(rnd.nextInt(str.length())));
        }
        LOGGER.info("After Random String creation :: " + sb.toString());
        return sb.toString();
    }

}
