package dev.ng.core

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex

object HashCodeGenerator {

  private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  def getCode(message: String): String = {
    val encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8))
    Hex.encodeHexString(encodedhash)
  }
}
