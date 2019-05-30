package data;

import org.testng.annotations.DataProvider;
import utils.DockerCmd;

/**
 * testng数据驱动: 提供docker仓库镜像有关的数据
 * Created by hujunxiang on 2019-05-09
 */
public class model1DataProvider {
    static DockerCmd dockerCmd = new DockerCmd();

    @DataProvider(name = "get_image_digest")
    public static Object[][] getImageDigest() {
        String digest = dockerCmd.getDockerMsgList("docker images --digests").get(1).get("DIGEST");
        return new Object[][] {
                new Object[] {digest,0}
        };
    }
}