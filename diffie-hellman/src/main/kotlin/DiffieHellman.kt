import java.math.BigInteger

object DiffieHellman {

    fun privateKey(prime: BigInteger): BigInteger = (1 until prime.toInt()).random().toBigInteger()

    fun publicKey(p: BigInteger, g: BigInteger, privKey: BigInteger): BigInteger = g.modPow(privKey, p)

    fun secret(prime: BigInteger, publicKey: BigInteger, privateKey: BigInteger): BigInteger =
        publicKey.modPow(privateKey, prime)
}
