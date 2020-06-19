package com.brain.yarnsparkclient

import org.apache.hadoop.security.UserGroupInformation

trait Authentication {

  def getUgi: UserGroupInformation

}
