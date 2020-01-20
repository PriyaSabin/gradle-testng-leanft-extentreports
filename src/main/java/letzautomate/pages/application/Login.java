package letzautomate.pages.application;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.java.Button;
import letzautomate.appmodel.ApplicationName;
import letzautomate.pages.common.LeanFTBasePage;
import letzautomate.utilities.ReportManager;

import java.io.File;

public class Login extends LeanFTBasePage {

    ReportManager reportManager = new ReportManager();
    ApplicationName applicationName;
    private ApplicationName.CalculatorWindow calculatorWindow;
    private Button button6;
    private Button button4;
    public Login() {
        try{
            applicationName = new ApplicationName();
            calculatorWindow = applicationName.new CalculatorWindow(applicationName);
            button6 = calculatorWindow.button6();
            button4 = calculatorWindow.button4();
        }catch(GeneralLeanFtException e){
            e.printStackTrace();
        }
    }

    public void launchApplication() {
        System.out.println(System.getProperty("user.dir")+"\\leanft\\src\\test\\resources\\application\\runapplication.bat");
       ProcessBuilder processBuilder = new ProcessBuilder(System.getProperty("user.dir")+"\\src\\test\\resources\\application\\runapplication.bat").inheritIO();
       processBuilder.directory(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\application\\"));
       try{
           processBuilder.start();
           Thread.sleep(5000);
           report(calculatorWindow, "PASS", "Application is Launched");
       }catch(Exception e){
           e.printStackTrace();

        }

    }

    public void addNumbers(){
        try{
            //button4.click();a
            buttonClick(button6);
            report(calculatorWindow, "PASS", "Button is Clicked");

        }catch(Exception e){
            e.printStackTrace();
            reportManager.reportJavaLeanFT(calculatorWindow, "FAIL", "Button was NOT Clicked");
        }

    }

    public boolean closeApplication() {
        try{
            LOGGER.info("Before Closing the window");
            calculatorWindow.close();
            LOGGER.info("After Closing the window");
        }catch(Exception e){
            LOGGER.info("There was an error in closing the window");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
