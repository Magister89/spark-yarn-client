`spark-shell --jars com.brain.yarn-spark-client-assembly-0.1.jar \`<br>
`--files \`<br>
`core-site.xml,\`<br>
`hadoop-env.sh,\`<br>
`hdfs-site.xml,\`<br>
`hive-env.sh,\`<br>
`hive-site.xml,\`<br>
`mapred-site.xml,\`<br>
`ssl-client.xml,\`<br>
`yarn-site.xml,\`<br>
`user.keytab`<br>

`import com.brain.yarnsparkclient._`<br>
`val conf = new YarnConfiguration`<br>
`conf.init()`<br>
`val ugi = new KerberosAuthentication("datalabs@CORP.BRAIN.COM", "datalabs.keytab", conf).getUgi()`<br>
`val yarnClient = new YarnKerberosClient(conf, ugi, spark)`<br>
`yarnClient.init()`<br>
`yarnClient.start()`<br>
`val ds = yarnClient.getApplications`<br>
`val types = Set("SPARK")`<br>
`val dsFiltered = yarnClient.getApplications(types)`<br>
`ds.show()`<br>
`dsFiltered.show()`<br>