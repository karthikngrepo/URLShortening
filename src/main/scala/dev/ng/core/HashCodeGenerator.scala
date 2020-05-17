package dev.ng.core

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64

import org.apache.commons.codec.binary.Hex

object HashCodeGenerator {

//  private val Alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

//  private val Base = Alphabet.length

  private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  def getCode(message: String): String = {
    //val sha256hex = DigestUtils.sha256Hex(originalString)
    val encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8))
    Hex.encodeHexString(encodedhash)
  }
}
