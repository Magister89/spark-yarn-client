package com.brain.yarnsparkclient

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.SparkFiles
import org.apache.spark.sql.SparkSession

class KerberosAuthentication(principal: String,
                             keytab: String,
                             conf: Configuration) extends Authentication {

  def getUgi(): UserGroupInformation = {
    UserGroupInformation.setConfiguration(conf)
    UserGroupInformation.loginUserFromKeytabAndReturnUGI(
      principal,
      SparkFiles.getRootDirectory() + "/" + keytab)
  }

}
