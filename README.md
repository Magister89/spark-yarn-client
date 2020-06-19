`spark-shell --jars com.brain.yarn-spark-client-assembly-0.1.jar \
--files \
core-site.xml,\
hadoop-env.sh,\
hdfs-site.xml,\
hive-env.sh,\
hive-site.xml,\
mapred-site.xml,\
ssl-client.xml,\
yarn-site.xml,\
user.keytab`

`import com.brain.yarnsparkclient._
val conf = new YarnConfiguration
conf.init()
val ugi = new KerberosAuthentication("datalabs@CORP.BRAIN.COM", "datalabs.keytab", conf).getUgi()
val yarnClient = new YarnKerberosClient(conf, ugi, spark)
yarnClient.init()
yarnClient.start()
val ds = yarnClient.getApplications
val types = Set("SPARK")
val dsFiltered = yarnClient.getApplications(types)
ds.count()
dsFiltered.count()`