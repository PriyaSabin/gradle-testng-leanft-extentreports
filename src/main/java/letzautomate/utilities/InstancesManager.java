package letzautomate.utilities;

import com.aventstack.extentreports.ExtentTest;


import java.util.concurrent.ConcurrentHashMap;

public class InstancesManager {


    public static ConcurrentHashMap<Long, ExtentTest> testcaseMap = new ConcurrentHashMap<Long, ExtentTest>();


}

