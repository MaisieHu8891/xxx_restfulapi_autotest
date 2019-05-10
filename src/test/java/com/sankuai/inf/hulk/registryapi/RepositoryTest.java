package com.sankuai.inf.hulk.registryapi;
/**
 * RegistryApi-Repository相关测试
 * Created by hujunxiang on 2019-04-28
 */
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RepositoryTest {
    Repository repository;

    @BeforeClass
    public void setRepository(){
        if(repository == null){
            repository = new Repository();
        }
    }

    @Test(description = "api/repository/path/status")
    @Description("提交digest状态")
    public void testPathStatus() {
        HashMap<String,Object> map = new HashMap();
        repository.pathStatus(map).then().statusCode(200).body("code",equalTo(0));
    }

    @Test(description = "api/repository/path/list")
    @Issue("4873/workItem/defect/detail/2565076")
    @TmsLink("4873/case/testCase")
    public void testPathList() {
        repository.pathList("sha256:0de43cde2c4b864a8e4a84bbd9958e47c5d851319f118203303d040b0a74f159",0).then().statusCode(200).body("code",equalTo(0));
    }
}