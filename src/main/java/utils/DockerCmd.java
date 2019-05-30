package utils;
/**
 * docker命令类
 * Created by hujunxiang on 2019-05-08
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DockerCmd extends ExecCmd {
    private static final Logger logger = LoggerFactory.getLogger(DockerCmd.class);

    public boolean dockerExist(){
        if(getExecRes("docker -vison").contains("command not found")){
            logger.error("Docker 未安装,请先安装docker");
            return false;
        }else {
            logger.debug("Docker 已经安装");
            return true;
        }
    }

    public void pullImage(String imagename) {
        execShell("src/shell/pullimage.sh", imagename);
    }

    public void tagImage(String image, String tagName) {
        execCmd("docker tag " + image + " " + tagName);
    }

    /**
     * 只试用于docker命令的结果内容格式是列表样式的，比如docker ps -a, docker images --digests之类
     *
     * @param cmd docker命令
     * @return
     */
    public List<HashMap<String, String>> getDockerMsgList(String cmd) {
        String msg = getExecRes(cmd);
        logger.debug(msg);
        try{
            String[] msgList = msg.split("\n");
            String[] msgKeys = msgList[0].split("\\s{2,}");
            List<HashMap<String, String>> resList = new ArrayList<>();
            for (int i = 1; i < msgList.length; i++) {
                HashMap<String, String> map = new HashMap<>();
                String[] msgValue = msgList[i].split("\\s{2,}");
                for (int j = 0; j < msgKeys.length; j++) {
                    map.put(msgKeys[j], msgValue[j]);
                }
                resList.add(map);
            }
            return resList;

        }catch (Exception e){
            logger.error("获取失败，docker命令错误或不支持该命令");
            logger.error(e.getMessage());
            return null;
        }


    }


}
