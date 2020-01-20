package letzautomate;


import letzautomate.pages.application.Login;
import letzautomate.utilities.TestcaseManager;
import org.testng.annotations.Test;

public class TC001 extends TestcaseManager {

    @Test(groups={"regression", "TC001"})
    public void tc001() {

        setTestcaseName("Login Test one");
        //LoginPage loginPage = new LoginPage();
        Login login = new Login();
        //loginPage.login();
        login.launchApplication();
        login.addNumbers();
        login.closeApplication();
    }
}
