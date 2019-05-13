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

    @Test(dataProvider = "get_image_digest", dataProviderClass = data.RegistryDataProvider.class,description = "api/repository/path/list")
    @Issue("4873/workItem/defect/detail/2565076")
    @TmsLink("4873/case/testCase")
    public void testPathList(String digest, Integer syncType) {
        repository.pathList(digest,syncType).then().statusCode(200).body("code",equalTo(0));
    }


    public void testChangePathStatus(){

    }
}