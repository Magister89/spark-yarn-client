package com.brain.yarnsparkclient

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.SparkFiles
import org.apache.spark.sql.SparkSession

class YarnAuthentication(principal: String, keytab: String, spark: SparkSession, conf: Configuration) {

  def getUgi(): UserGroupInformation = {
    UserGroupInformation.setConfiguration(conf)
    UserGroupInformation.loginUserFromKeytabAndReturnUGI(
      principal,
      SparkFiles.getRootDirectory() + "/" + keytab)
  }

}
