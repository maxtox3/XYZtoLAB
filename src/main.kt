import java.io.File
import kotlin.math.pow

fun readFile(fileName: String): List<String> =
  File(fileName).useLines { it.toList() }

fun main() {
  val cieCmfXyz = readFile("cie-cmf.txt")
  val palette = readFile("cucumber_1.txt").map { it.toDouble() }
  val light = readFile("light.txt").map { it.toDouble() }

  val palettePlusLight = palette.mapIndexed { index, s -> s * light[index] }

  var spec = 380.0
  var sumX = .0
  var sumY = .0
  var sumZ = .0

  cieCmfXyz.forEachIndexed { i, s ->
    val xyz = s.split(" ")
    sumX += spec * palettePlusLight[i] * xyz[0].toDouble() * spec
    sumY += spec * palettePlusLight[i] * xyz[1].toDouble() * spec
    sumZ += spec * palettePlusLight[i] * xyz[2].toDouble() * spec

    spec += 10
  }

  val k = 100 / (sumY * 10)

  val x = k * sumX * 10 / 100.0
  val y = k * sumY * 10 / 100.0
  val z = k * sumZ * 10 / 100.0

  println("x:$x y:$y z:$z")

  // XYZ to RGB
  val r = x * 3.2404542 + y * -1.5371385 + z * -0.4985314
  val g = x * -0.9692660 + y * 1.8760108 + z * 0.0415560
  val b = x * 0.0556434 + y * -0.2040259 + z * 1.0572252

  val powCoef = 1 / 2.4
  val resultR = (if (r > 0.0031308) (1.055 * r.pow(powCoef) - 0.055) else (12.92 * r)) * 255.0
  val resultG = (if (g > 0.0031308) (1.055 * g.pow(powCoef) - 0.055) else (12.92 * g)) * 255.0
  val resultB = (if (b > 0.0031308) (1.055 * b.pow(powCoef) - 0.055) else (12.92 * b)) * 255.0

  println("r:$resultR g:$resultG b:$resultB")

//  // XYZ to LAB
//
//  var spec1 = 380.0
//  var x1 = .0
//  var y1 = .0
//  var z1 = .0
//
//  cieCmfXyz.forEachIndexed { i, s ->
//    val xyz = s.split(" ")
//    x1 += spec1 * palette[i] * xyz[0].toDouble() * spec1
//    y1 += spec1 * palette[i] * xyz[1].toDouble() * spec1
//    z1 += spec1 * palette[i] * xyz[2].toDouble() * spec1
//
//    spec1 += 10
//  }
//
//  val k1 = 100 / (y1 * 10)
//
//  val xP = k1 * x1 * 10 / 100.0
//  val yP = k1 * y1 * 10 / 100.0
//  val zP = k1 * z1 * 10 / 100.0
//
//  val xyZtoLAB = ColorConverter.XYZtoLAB(xP.toFloat(), yP.toFloat(), zP.toFloat())
//
//
//  println("xp:$xP yp:$yP zp:$zP")
}