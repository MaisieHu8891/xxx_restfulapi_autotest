package com.xxx.inf.xxx.model2;
/**
 * model2xxxApi-Repository相关测试
 * Created by hujunxiang on 2019-04-28
 */
import data.model1DataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

public class xxxmodel2Test {
    xxxmodel2 xxxmodel2;

    @BeforeClass
    public void setRepository(){
        if(xxxmodel2 == null){
            xxxmodel2 = new xxxmodel2();
        }
    }

    @Test(description = "api/xxxmodel2/path/status")
    @Description("提交digest状态")
    public void testPathStatus() {
        HashMap<String,Object> map = new HashMap();
        xxxmodel2.pathStatus(map).then().statusCode(200).body("code",equalTo(0));
    }

    @Test(dataProvider = "get_image_digest", dataProviderClass = model1DataProvider.class,description = "api/xxxmodel2/path/list")
    @Issue("4873/workItem/defect/detail/2565076")
    @TmsLink("4873/case/testCase")
    public void testPathList(String digest, Integer syncType) {
        xxxmodel2.pathList(digest,syncType).then().statusCode(200).body("code",equalTo(0));
    }

}