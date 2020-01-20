package letzautomate;


import letzautomate.pages.application.Login;
import letzautomate.utilities.TestcaseManager;
import org.testng.annotations.Test;

public class TC002 extends TestcaseManager {

    @Test(groups={"regression", "TC002"})
    public void tc002() {

        setTestcaseName("Login Test two");
        //LoginPage loginPage = new LoginPage();
        Login login = new Login();
        //loginPage.login();
        login.launchApplication();
        login.addNumbers();
        login.closeApplication();
    }
}
