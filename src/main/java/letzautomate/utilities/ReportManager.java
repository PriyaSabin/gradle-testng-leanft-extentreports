package letzautomate.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.hp.lft.sdk.java.Window;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportManager {

    public static final Logger LOGGER = Logger.getLogger(ReportManager.class);

   /* public String takeScreenshot(WebDriver driver, String screenshotName) {
        String destination = null;
        String imgPath = null;
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSSS").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try{
            imgPath = "TestsScreenshots\\" + screenshotName + dateName + ".png";
            destination = System.getProperty("user.dir") + "\\build\\extent-reports\\" + imgPath;
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            LOGGER.info("Screenshot destination :: " + destination);
            return imgPath;
        }catch(Exception e){
            LOGGER.error("Take screenshot exception :: " + e.getMessage());

        }
        LOGGER.info("Destination after Exception " + destination);
        return imgPath;
    }*/

    public String captureLeanFTScreenshot(Window window, String screenshotName) {

        String destination = null;
        String imgPath = null;
        String dateStamp = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        try{
            RenderedImage img = window.getSnapshot();
            imgPath = screenshotName + dateStamp +".png";
            destination = System.getProperty("user.dir") + "\\build\\extent-reports\\" + imgPath;

            File finalDestination = new File(destination);
            finalDestination.getParentFile().mkdir();
            ImageIO.write(img, "png", finalDestination);
            LOGGER.info("Screenshot Destination :: " + destination);
            return imgPath;
        }catch(Exception e) {
            LOGGER.error("takeScreenshot Exception :: " + e.getMessage() );
            Assert.fail("Exception while taking the screenshot");
        }
        LOGGER.info("Destination after Exception " + destination);
        return imgPath;

    }

    public ExtentTest getExtentTestcase() {
        return InstancesManager.testcaseMap.get(Thread.currentThread().getId());
    }

    public void reportJavaLeanFT(Window javaWindow, String status, String message) {
        ExtentTest extentTestcase = getExtentTestcase();
        SoftAssert softAssert = new SoftAssert();
        try{
            if(status.equalsIgnoreCase("info")){
                String screenshotPath = captureLeanFTScreenshot(javaWindow, "INFO_" + randomString(5) + "_");
                extentTestcase.log(Status.INFO, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("pass")){
                String screenshotPath = captureLeanFTScreenshot(javaWindow, "PASS_" + randomString(5) + "_");
                extentTestcase.log(Status.PASS, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("fail")){
                String screenshotPath = captureLeanFTScreenshot(javaWindow, "FAIL_" + randomString(5) + "_");
                extentTestcase.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("skip")){
                String screenshotPath = captureLeanFTScreenshot(javaWindow, "SKIP_" + randomString(5) + "_");
                extentTestcase.log(Status.SKIP, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else {
                String screenshotPath = captureLeanFTScreenshot(javaWindow, "INFO_" + randomString(5) + "_");
                extentTestcase.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                softAssert.fail(message, new Throwable());
            }
        }catch(Exception e){
            LOGGER.error("Report Method Exception :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reportLink(String status, String linkText, String linkUrl) {
        ExtentTest extentTest = getExtentTestcase();
        ExtentLink extentLink = new ExtentLink();
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
