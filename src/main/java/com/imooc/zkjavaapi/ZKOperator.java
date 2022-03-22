package com.imooc.zkjavaapi;

import com.imooc.zkjavaapi.callback.DeleteCallBack;
import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * 描述：     演示对节点的操作，包含创建、读取、删除等。
 */
public class ZKOperator implements Watcher {

    public static final String SERVER_PATH = "192.168.199.131:2181";

    public static final Integer TIMEOUT = 5000;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        /**
         * 客户端和服务端他们是异步连接，连接成功之后，客户端会收到watcher通知。
         * connectString：服务器的IP+端口号，比如127.0.0.1:2181
         * sessionTimeout：超时时间
         * watcher：通知事件
         */
        ZooKeeper zk = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKOperator());
        System.out.println("客户端开始连接ZK服务器了");
        System.out.println(zk.getState());
        Thread.sleep(10000);

        /**
         * path:创建的路径
         * data：存储的数据
         * acl：权限，开放
         * createMode：永久、临时、顺序。
         */
        zk.create("/imooc-create-node", "imooc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.setData("/imooc-create-node", "imooc3".getBytes(), 1);
//        byte[] data = zk.getData("/imooc-create-node", null, null);
        String ctx = "删除成功";
        zk.delete("/imooc-create-node", 0, new DeleteCallBack(), ctx);
        Thread.sleep(2000); //程序休眠原因：因为DeleteCallBack 是一个异步函数
        //System.out.println(new String(data));
    }

    @Override
    public void process(WatchedEvent event) {
    }
}
