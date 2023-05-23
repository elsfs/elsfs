```shell

docker run -p 9100:9100 -p 9091:9091  \
  --name minio   -d \
  --restart=always \
 -e "MINIO_ACCESS_KEY=minio"    \
  -e "MINIO_SECRET_KEY=WNpYxHWzww6Lf"     \
  -v /usr/local/minio/data:/data     \
 minio/minio:RELEASE.2022-07-15T03-44-22Z  server  /data --console-address ":9091" -address ":9100"

```

# 数据迁移

1. 下载mc

```shell
# github源码 地址： https://github.com/minio/mc
# win  https://dl.min.io/client/mc/release/windows-amd64/mc.exe
# linux 64-bit Intel	  https://dl.min.io/client/mc/release/linux-amd64/mc
#  64-bit PPC	  https://dl.min.io/client/mc/release/linux-ppc64le/mc
# docker run -it --entrypoint=/bin/sh minio/mc

wget https://dl.min.io/client/mc/release/linux-amd64/mc
chmod +x mc
./mc --help
```


2. 迁移数据

```shell
#全量迁移,重名文件不覆盖,bucket不存在会自动创建
mc mirror minio1 minio2
#只是迁移某个bucket,以test为例,目标的bucket需要提前建好
mc mirror minio1/test minio2/test
#覆盖重名文件,加--overwrite
mc mirror --overwrite minio1/test minio2/test

```
五、其他命令

1. Copyls 列出文件和文件夹。
2. mb 创建一个存储桶或一个文件夹。
3. cat 显示文件和对象内容。
4. pipe 将一个STDIN重定向到一个对象或者文件或者STDOUT。
5. share 生成用于共享的URL。
6. cp 拷贝文件和对象。
7. mirror 给存储桶和文件夹做镜像。
8. find 基于参数查找文件。
9. diff 对两个文件夹或者存储桶比较差异。
10. rm 删除文件和对象。
11. events 管理对象通知。
12. watch 监听文件和对象的事件。
13. policy 管理访问策略。
14. session 为cp命令管理保存的会话。
15. config 管理mc配置文件。
16. update 检查软件更新。
17. version 输出版本信息。

获取集群信息
```shell
# 执行命令测试能否连通，如有文件或至少存在一个 Bucket，会在回显中列出：
mc ls minio1
```
```shell
# 执行命令获取集群数据大小，如果有文件或存在至少一个 Bucket，会在最后一行打印当前集群所有文件总大小：
mc du minio1
```
对拷（镜像）

```shell
#使用命令进行 Bucket 对拷,mc 无法实现集群全量对拷，单条命令只能逐个 Bucket 进行操作：
mc mirror $SrcCluster/$srcBucket $DestCluster
#例如：
mc mirror clusterA/bucketa clusterB
mc mirror 命令可以不指定 Dest 的 Bucket，如果 $DestCluster 不存在对应名称 Bucket，对拷过程中会自动创建。

#若要实现不停机对拷，可以使用以下命令：
mc mirror -w $srcCluster/Bucket $destCluster
#-w 参数可以让 mirror 命令持续监控某一目录。由于 mirror 只能对拷单Bucket，如果使用 Shell 脚本进行for 轮询实现集群对拷时，一旦Bucket 过多，此命令可能会造成大量监控线程，增加负载。


```
检查是否完全结束


```shell
#使用命令检查是否有未完整传输的文件,如果有未传输完全的文件，会在回显中列出：
mc ls --incomplete minio1

#如果有必要，可以删除某个桶中的残缺文件：
mc rm --incomplete $hostName/$bucket
```




```shell
docker run -it --name nacos  -e MODE=standalone  -v C:/Users/zeng/Desktop/maicaii/nacos-docker/nacos/nacos/init.d/application.properties:/home/nacos/init.d/application.properties   -p 8848:8848  nacos/nacos-server:v2.1.1
```

