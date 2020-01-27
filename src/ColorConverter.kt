object ColorConverter {
  // XYZ (Tristimulus) Reference values of a perfect reflecting diffuser
//2o Observer (CIE 1931)
// X2, Y2, Z2
  var CIE2_A = floatArrayOf(109.850f, 100f, 35.585f) //Incandescent
  var CIE2_C = floatArrayOf(98.074f, 100f, 118.232f)
  var CIE2_D50 = floatArrayOf(96.422f, 100f, 82.521f)
  var CIE2_D55 = floatArrayOf(95.682f, 100f, 92.149f)
  var CIE2_D65 = floatArrayOf(95.047f, 100f, 108.883f) //Daylight
  var CIE2_D75 = floatArrayOf(94.972f, 100f, 122.638f)
  var CIE2_F2 = floatArrayOf(99.187f, 100f, 67.395f) //Fluorescent
  var CIE2_F7 = floatArrayOf(95.044f, 100f, 108.755f)
  var CIE2_F11 = floatArrayOf(100.966f, 100f, 64.370f)
  //10o Observer (CIE 1964)
// X2, Y2, Z2
  var CIE10_A = floatArrayOf(111.144f, 100f, 35.200f) //Incandescent
  var CIE10_C = floatArrayOf(97.285f, 100f, 116.145f)
  var CIE10_D50 = floatArrayOf(96.720f, 100f, 81.427f)
  var CIE10_D55 = floatArrayOf(95.799f, 100f, 90.926f)
  var CIE10_D65 = floatArrayOf(94.811f, 100f, 107.304f) //Daylight
  var CIE10_D75 = floatArrayOf(94.416f, 100f, 120.641f)
  var CIE10_F2 = floatArrayOf(103.280f, 100f, 69.026f) //Fluorescent
  var CIE10_F7 = floatArrayOf(95.792f, 100f, 107.687f)
  var CIE10_F11 = floatArrayOf(103.866f, 100f, 65.627f)
  /**
   * RFB -> CMYK
   * @param red Values in the range [0..255].
   * @param green Values in the range [0..255].
   * @param blue Values in the range [0..255].
   * @return CMYK color space. Normalized.
   */
  fun RGBtoCMYK(red: Int, green: Int, blue: Int): FloatArray {
    val cmyk = FloatArray(4)
    val r = red / 255f
    val g = green / 255f
    val b = blue / 255f
    val k = 1.0f - Math.max(r, Math.max(g, b))
    val c = (1f - r - k) / (1f - k)
    val m = (1f - g - k) / (1f - k)
    val y = (1f - b - k) / (1f - k)
    cmyk[0] = c
    cmyk[1] = m
    cmyk[2] = y
    cmyk[3] = k
    return cmyk
  }

  /**
   * CMYK -> RGB
   * @param c Cyan.
   * @param m Magenta.
   * @param y Yellow.
   * @param k Black.
   * @return RGB color space.
   */
  fun CMYKtoRGB(
    c: Float,
    m: Float,
    y: Float,
    k: Float
  ): IntArray {
    val rgb = IntArray(3)
    rgb[0] = (255 * (1 - c) * (1 - k)).toInt()
    rgb[1] = (255 * (1 - m) * (1 - k)).toInt()
    rgb[2] = (255 * (1 - y) * (1 - k)).toInt()
    return rgb
  }

  /**
   * RGB -> YUV.
   * Y in the range [0..1].
   * U in the range [-0.5..0.5].
   * V in the range [-0.5..0.5].
   * @param red Values in the range [0..255].
   * @param green Values in the range [0..255].
   * @param blue Values in the range [0..255].
   * @return YUV color space.
   */
  fun RGBtoYUV(red: Int, green: Int, blue: Int): FloatArray {
    val r = red.toFloat() / 255
    val g = green.toFloat() / 255
    val b = blue.toFloat() / 255
    val yuv = FloatArray(3)
    val y: Float
    val u: Float
    val v: Float
    y = (0.299 * r + 0.587 * g + 0.114 * b).toFloat()
    u = (-0.14713 * r - 0.28886 * g + 0.436 * b).toFloat()
    v = (0.615 * r - 0.51499 * g - 0.10001 * b).toFloat()
    yuv[0] = y
    yuv[1] = u
    yuv[2] = v
    return yuv
  }

  /**
   * YUV -> RGB.
   * @param y Luma. In the range [0..1].
   * @param u Chrominance. In the range [-0.5..0.5].
   * @param v Chrominance. In the range [-0.5..0.5].
   * @return RGB color space.
   */
  fun YUVtoRGB(y: Float, u: Float, v: Float): IntArray {
    val rgb = IntArray(3)
    val r: Float
    val g: Float
    val b: Float
    r = ((y + 0.000 * u + 1.140 * v) * 255).toFloat()
    g = ((y - 0.396 * u - 0.581 * v) * 255).toFloat()
    b = ((y + 2.029 * u + 0.000 * v) * 255).toFloat()
    rgb[0] = r.toInt()
    rgb[1] = g.toInt()
    rgb[2] = b.toInt()
    return rgb
  }

  /**
   * RGB -> YIQ.
   * @param red Values in the range [0..255].
   * @param green Values in the range [0..255].
   * @param blue Values in the range [0..255].
   * @return YIQ color space.
   */
  fun RGBtoYIQ(red: Int, green: Int, blue: Int): FloatArray {
    val yiq = FloatArray(3)
    val y: Float
    val i: Float
    val q: Float
    val r = red.toFloat() / 255
    val g = green.toFloat() / 255
    val b = blue.toFloat() / 255
    y = (0.299 * r + 0.587 * g + 0.114 * b).toFloat()
    i = (0.596 * r - 0.275 * g - 0.322 * b).toFloat()
    q = (0.212 * r - 0.523 * g + 0.311 * b).toFloat()
    yiq[0] = y
    yiq[1] = i
    yiq[2] = q
    return yiq
  }

  /**
   * YIQ -> RGB.
   * @param y Luma. Values in the range [0..1].
   * @param i In-phase. Values in the range [-0.5..0.5].
   * @param q Quadrature. Values in the range [-0.5..0.5].
   * @return RGB color space.
   */
  fun YIQtoRGB(y: Double, i: Double, q: Double): IntArray {
    val rgb = IntArray(3)
    var r: Int
    var g: Int
    var b: Int
    r = ((y + 0.956 * i + 0.621 * q) * 255).toInt()
    g = ((y - 0.272 * i - 0.647 * q) * 255).toInt()
    b = ((y - 1.105 * i + 1.702 * q) * 255).toInt()
    r = Math.max(0, Math.min(255, r))
    g = Math.max(0, Math.min(255, g))
    b = Math.max(0, Math.min(255, b))
    rgb[0] = r
    rgb[1] = g
    rgb[2] = b
    return rgb
  }

  fun RGBtoYCbCr(red: Int, green: Int, blue: Int, colorSpace: YCbCrColorSpace): FloatArray {
    val r = red.toFloat() / 255
    val g = green.toFloat() / 255
    val b = blue.toFloat() / 255
    val YCbCr = FloatArray(3)
    val y: Float
    val cb: Float
    val cr: Float
    if (colorSpace == YCbCrColorSpace.ITU_BT_601) {
      y = (0.299 * r + 0.587 * g + 0.114 * b).toFloat()
      cb = (-0.169 * r - 0.331 * g + 0.500 * b).toFloat()
      cr = (0.500 * r - 0.419 * g - 0.081 * b).toFloat()
    } else {
      y = (0.2215 * r + 0.7154 * g + 0.0721 * b).toFloat()
      cb = (-0.1145 * r - 0.3855 * g + 0.5000 * b).toFloat()
      cr = (0.5016 * r - 0.4556 * g - 0.0459 * b).toFloat()
    }
    YCbCr[0] = y
    YCbCr[1] = cb
    YCbCr[2] = cr
    return YCbCr
  }

  fun YCbCrtoRGB(
    y: Float,
    cb: Float,
    cr: Float,
    colorSpace: YCbCrColorSpace
  ): IntArray {
    val rgb = IntArray(3)
    val r: Float
    val g: Float
    val b: Float
    if (colorSpace == YCbCrColorSpace.ITU_BT_601) {
      r = (y + 0.000 * cb + 1.403 * cr).toFloat() * 255
      g = (y - 0.344 * cb - 0.714 * cr).toFloat() * 255
      b = (y + 1.773 * cb + 0.000 * cr).toFloat() * 255
    } else {
      r = (y + 0.000 * cb + 1.5701 * cr).toFloat() * 255
      g = (y - 0.1870 * cb - 0.4664 * cr).toFloat() * 255
      b = (y + 1.8556 * cb + 0.000 * cr).toFloat() * 255
    }
    rgb[0] = r.toInt()
    rgb[1] = g.toInt()
    rgb[2] = b.toInt()
    return rgb
  }

  /**
   * Rg-Chromaticity space is already known to remove ambiguities due to illumination or surface pose.
   * @see Neural Information Processing - Chi Sing Leung. p. 668
   *
   * @param red Red coefficient.
   * @param green Green coefficient.
   * @param blue Blue coefficient.
   * @return Normalized RGChromaticity. Range[0..1].
   */
  fun RGChromaticity(red: Int, green: Int, blue: Int): DoubleArray {
    val color = DoubleArray(5)
    val sum = red + green + blue.toDouble()
    //red
    color[0] = red / sum
    //green
    color[1] = green / sum
    //blue
    color[2] = 1 - color[0] - color[1]
    val rS = color[0] - 0.333
    val gS = color[1] - 0.333
    //saturation
    color[3] = Math.sqrt(rS * rS + gS * gS)
    //hue
    color[4] = Math.atan(rS / gS)
    return color
  }

  /**
   * RGB -> HSV.
   * Adds (hue + 360) % 360 for represent hue in the range [0..359].
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return HSV color space.
   */
  fun RGBtoHSV(red: Int, green: Int, blue: Int): FloatArray {
    val hsv = FloatArray(3)
    val r = red / 255f
    val g = green / 255f
    val b = blue / 255f
    val max = Math.max(r, Math.max(g, b))
    val min = Math.min(r, Math.min(g, b))
    val delta = max - min
    // Hue
    if (max == min) {
      hsv[0] = 0F
    } else if (max == r) {
      hsv[0] = (g - b) / delta * 60f
    } else if (max == g) {
      hsv[0] = ((b - r) / delta + 2f) * 60f
    } else if (max == b) {
      hsv[0] = ((r - g) / delta + 4f) * 60f
    }
    // Saturation
    if (delta == 0f) hsv[1] = 0F else hsv[1] = delta / max
    //Value
    hsv[2] = max
    return hsv
  }

  /**
   * HSV -> RGB.
   * @param hue Hue.
   * @param saturation Saturation. In the range[0..1].
   * @param value Value. In the range[0..1].
   * @return RGB color space. In the range[0..255].
   */
  fun HSVtoRGB(hue: Float, saturation: Float, value: Float): IntArray {
    val rgb = IntArray(3)
    val hi = Math.floor(hue / 60.0).toFloat() % 6
    val f = (hue / 60.0 - Math.floor(hue / 60.0)).toFloat()
    val p = (value * (1.0 - saturation)).toFloat()
    val q = (value * (1.0 - f * saturation)).toFloat()
    val t = (value * (1.0 - (1.0 - f) * saturation)).toFloat()
    if (hi == 0f) {
      rgb[0] = (value * 255).toInt()
      rgb[1] = (t * 255).toInt()
      rgb[2] = (p * 255).toInt()
    } else if (hi == 1f) {
      rgb[0] = (q * 255).toInt()
      rgb[1] = (value * 255).toInt()
      rgb[2] = (p * 255).toInt()
    } else if (hi == 2f) {
      rgb[0] = (p * 255).toInt()
      rgb[1] = (value * 255).toInt()
      rgb[2] = (t * 255).toInt()
    } else if (hi == 3f) {
      rgb[0] = (p * 255).toInt()
      rgb[1] = (value * 255).toInt()
      rgb[2] = (q * 255).toInt()
    } else if (hi == 4f) {
      rgb[0] = (t * 255).toInt()
      rgb[1] = (value * 255).toInt()
      rgb[2] = (p * 255).toInt()
    } else if (hi == 5f) {
      rgb[0] = (value * 255).toInt()
      rgb[1] = (p * 255).toInt()
      rgb[2] = (q * 255).toInt()
    }
    return rgb
  }

  /**
   * RGB -> YCC.
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return YCC color space. In the range [0..1].
   */
  fun RGBtoYCC(red: Int, green: Int, blue: Int): FloatArray {
    val ycc = FloatArray(3)
    val r = red / 255f
    val g = green / 255f
    val b = blue / 255f
    val y = 0.213f * r + 0.419f * g + 0.081f * b
    val c1 = -0.131f * r - 0.256f * g + 0.387f * b + 0.612f
    val c2 = 0.373f * r - 0.312f * r - 0.061f * b + 0.537f
    ycc[0] = y
    ycc[1] = c1
    ycc[2] = c2
    return ycc
  }

  /**
   * YCC -> RGB.
   * @param y Y coefficient.
   * @param c1 C coefficient.
   * @param c2 C coefficient.
   * @return RGB color space.
   */
  fun YCCtoRGB(y: Float, c1: Float, c2: Float): IntArray {
    val rgb = IntArray(3)
    val r = 0.981f * y + 1.315f * (c2 - 0.537f)
    val g = 0.981f * y - 0.311f * (c1 - 0.612f) - 0.669f * (c2 - 0.537f)
    val b = 0.981f * y + 1.601f * (c1 - 0.612f)
    rgb[0] = (r * 255f).toInt()
    rgb[1] = (g * 255f).toInt()
    rgb[2] = (b * 255f).toInt()
    return rgb
  }

  /**
   * RGB -> YCoCg.
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return YCoCg color space.
   */
  fun RGBtoYCoCg(red: Int, green: Int, blue: Int): FloatArray {
    val yCoCg = FloatArray(3)
    val r = red / 255f
    val g = green / 255f
    val b = blue / 255f
    val y = r / 4f + g / 2f + b / 4f
    val co = r / 2f - b / 2f
    val cg = -r / 4f + g / 2f - b / 4f
    yCoCg[0] = y
    yCoCg[1] = co
    yCoCg[2] = cg
    return yCoCg
  }

  /**
   * YCoCg -> RGB.
   * @param y Pseudo luminance, or intensity.
   * @param co Orange chrominance.
   * @param cg Green chrominance.
   * @return RGB color space.
   */
  fun YCoCgtoRGB(y: Float, co: Float, cg: Float): IntArray {
    val rgb = IntArray(3)
    val r = y + co - cg
    val g = y + cg
    val b = y - co - cg
    rgb[0] = (r * 255f).toInt()
    rgb[1] = (g * 255f).toInt()
    rgb[2] = (b * 255f).toInt()
    return rgb
  }

  /**
   * RGB -> XYZ
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return XYZ color space.
   */
  fun RGBtoXYZ(red: Int, green: Int, blue: Int): FloatArray {
    val xyz = FloatArray(3)
    var r = red / 255f
    var g = green / 255f
    var b = blue / 255f
    //R
    if (r > 0.04045) r = Math.pow(
      ((r + 0.055f) / 1.055f).toDouble(),
      2.4
    ).toFloat() else r /= 12.92f
    //G
    if (g > 0.04045) g = Math.pow(
      ((g + 0.055f) / 1.055f).toDouble(),
      2.4
    ).toFloat() else g /= 12.92f
    //B
    if (b > 0.04045) b = Math.pow(
      ((b + 0.055f) / 1.055f).toDouble(),
      2.4
    ).toFloat() else b /= 12.92f
    r *= 100f
    g *= 100f
    b *= 100f
    val x = 0.412453f * r + 0.35758f * g + 0.180423f * b
    val y = 0.212671f * r + 0.71516f * g + 0.072169f * b
    val z = 0.019334f * r + 0.119193f * g + 0.950227f * b
    xyz[0] = x
    xyz[1] = y
    xyz[2] = z
    return xyz
  }

  /**
   * XYZ -> RGB
   * @param x X coefficient.
   * @param y Y coefficient.
   * @param z Z coefficient.
   * @return RGB color space.
   */
  fun XYZtoRGB(x: Float, y: Float, z: Float): IntArray {
    var x = x
    var y = y
    var z = z
    val rgb = IntArray(3)
    x /= 100f
    y /= 100f
    z /= 100f
    var r = 3.240479f * x - 1.53715f * y - 0.498535f * z
    var g = -0.969256f * x + 1.875991f * y + 0.041556f * z
    var b = 0.055648f * x - 0.204043f * y + 1.057311f * z
    r = if (r > 0.0031308) 1.055f * Math.pow(
      r.toDouble(),
      0.4166
    ).toFloat() - 0.055f else 12.92f * r
    g = if (g > 0.0031308) 1.055f * Math.pow(
      g.toDouble(),
      0.4166
    ).toFloat() - 0.055f else 12.92f * g
    b = if (b > 0.0031308) 1.055f * Math.pow(
      b.toDouble(),
      0.4166
    ).toFloat() - 0.055f else 12.92f * b
    rgb[0] = (r * 255).toInt()
    rgb[1] = (g * 255).toInt()
    rgb[2] = (b * 255).toInt()
    return rgb
  }

  /**
   * XYZ -> HunterLAB
   * @param x X coefficient.
   * @param y Y coefficient.
   * @param z Z coefficient.
   * @return HunterLab coefficient.
   */
  fun XYZtoHunterLAB(x: Float, y: Float, z: Float): FloatArray {
    val hunter = FloatArray(3)
    val sqrt = Math.sqrt(y.toDouble()).toFloat()
    val l = 10 * sqrt
    val a = 17.5f * ((1.02f * x - y) / sqrt)
    val b = 7f * ((y - 0.847f * z) / sqrt)
    hunter[0] = l
    hunter[1] = a
    hunter[2] = b
    return hunter
  }

  /**
   * HunterLAB -> XYZ
   * @param l L coefficient.
   * @param a A coefficient.
   * @param b B coefficient.
   * @return XYZ color space.
   */
  fun HunterLABtoXYZ(l: Float, a: Float, b: Float): FloatArray {
    val xyz = FloatArray(3)
    val tempY = l / 10f
    val tempX = a / 17.5f * l / 10f
    val tempZ = b / 7f * l / 10f
    val y = tempY * tempY
    val x = (tempX + y) / 1.02f
    val z = -(tempZ - y) / 0.847f
    xyz[0] = x
    xyz[1] = y
    xyz[2] = z
    return xyz
  }

  /**
   * RGB -> HunterLAB.
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return HunterLAB color space.
   */
  fun RGBtoHunterLAB(red: Int, green: Int, blue: Int): FloatArray {
    val xyz = RGBtoXYZ(red, green, blue)
    return XYZtoHunterLAB(xyz[0], xyz[1], xyz[2])
  }

  /**
   * HunterLAB -> RGB.
   * @param l L coefficient.
   * @param a A coefficient.
   * @param b B coefficient.
   * @return RGB color space.
   */
  fun HunterLABtoRGB(l: Float, a: Float, b: Float): IntArray {
    val xyz = HunterLABtoXYZ(l, a, b)
    return XYZtoRGB(xyz[0], xyz[1], xyz[2])
  }

  /**
   * RGB -> HLS.
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @return HLS color space.
   */
  fun RGBtoHLS(red: Int, green: Int, blue: Int): FloatArray {
    val hsl = FloatArray(3)
    val r = red / 255f
    val g = green / 255f
    val b = blue / 255f
    val max = Math.max(r, Math.max(r, b))
    val min = Math.min(r, Math.min(r, b))
    val delta = max - min
    //HSK
    var h = 0f
    var s = 0f
    val l = (max + min) / 2
    if (delta == 0f) { // gray color
      h = 0f
      s = 0.0f
    } else { // get saturation value
      s = if (l <= 0.5) delta / (max + min) else delta / (2 - max - min)
      // get hue value
      var hue: Float
      hue = if (r == max) {
        (g - b) / 6 / delta
      } else if (g == max) {
        1.0f / 3 + (b - r) / 6 / delta
      } else {
        2.0f / 3 + (r - g) / 6 / delta
      }
      // correct hue if needed
      if (hue < 0) hue += 1f
      if (hue > 1) hue -= 1f
      h = (hue * 360)
    }
    hsl[0] = h
    hsl[1] = s
    hsl[2] = l
    return hsl
  }

  /**
   * HLS -> RGB.
   * @param hue Hue.
   * @param saturation Saturation.
   * @param luminance Luminance.
   * @return RGB color space.
   */
  fun HSLtoRGB(
    hue: Float,
    saturation: Float,
    luminance: Float
  ): IntArray {
    val rgb = IntArray(3)
    var r = 0f
    var g = 0f
    var b = 0f
    if (saturation == 0f) { // gray values
      b = (luminance * 255)
      g = b
      r = g
    } else {
      val v1: Float
      val v2: Float
      val h = hue / 360
      v2 = if (luminance < 0.5) luminance * (1 + saturation) else luminance + saturation - luminance * saturation
      v1 = 2 * luminance - v2
      r = (255 * Hue_2_RGB(v1, v2, h + 1.0f / 3))
      g = (255 * Hue_2_RGB(v1, v2, h))
      b = (255 * Hue_2_RGB(v1, v2, h - 1.0f / 3))
    }
    rgb[0] = r.toInt()
    rgb[1] = g.toInt()
    rgb[2] = b.toInt()
    return rgb
  }

  private fun Hue_2_RGB(v1: Float, v2: Float, vH: Float): Float {
    var vH = vH
    if (vH < 0) vH += 1f
    if (vH > 1) vH -= 1f
    if (6 * vH < 1) return v1 + (v2 - v1) * 6 * vH
    if (2 * vH < 1) return v2
    return if (3 * vH < 2) v1 + (v2 - v1) * (2.0f / 3 - vH) * 6 else v1
  }

  /**
   * RGB -> CIE-LAB.
   * @param red Red coefficient. Values in the range [0..255].
   * @param green Green coefficient. Values in the range [0..255].
   * @param blue Blue coefficient. Values in the range [0..255].
   * @param tristimulus XYZ Tristimulus.
   * @return CIE-LAB color space.
   */
  fun RGBtoLAB(red: Int, green: Int, blue: Int, tristimulus: FloatArray): FloatArray {
    val xyz = RGBtoXYZ(red, green, blue)
    return XYZtoLAB(
      xyz[0],
      xyz[1],
      xyz[2],
      tristimulus
    )
  }

  /**
   * CIE-LAB -> RGB.
   * @param l L coefficient.
   * @param a A coefficient.
   * @param b B coefficient.
   * @param tristimulus XYZ Tristimulus.
   * @return RGB color space.
   */
  fun LABtoRGB(
    l: Float,
    a: Float,
    b: Float,
    tristimulus: FloatArray
  ): IntArray {
    val xyz = LABtoXYZ(l, a, b, tristimulus)
    return XYZtoRGB(xyz[0], xyz[1], xyz[2])
  }

  /**
   * XYZ -> CIE-LAB.
   * @param x X coefficient.
   * @param y Y coefficient.
   * @param z Z coefficient.
   * @param tristimulus XYZ Tristimulus.
   * @return CIE-LAB color space.
   */
  fun XYZtoLAB(
    x: Float,
    y: Float,
    z: Float,
    tristimulus: FloatArray
  ): FloatArray {
    var x = x
    var y = y
    var z = z
    val lab = FloatArray(3)
    x /= tristimulus[0]
    y /= tristimulus[1]
    z /= tristimulus[2]
    x = if (x > 0.008856) Math.pow(
      x.toDouble(),
      0.33
    ).toFloat() else 7.787f * x + 0.1379310344827586f
    y = if (y > 0.008856) Math.pow(
      y.toDouble(),
      0.33
    ).toFloat() else 7.787f * y + 0.1379310344827586f
    z = if (z > 0.008856) Math.pow(
      z.toDouble(),
      0.33
    ).toFloat() else 7.787f * z + 0.1379310344827586f
    lab[0] = 116 * y - 16
    lab[1] = 500 * (x - y)
    lab[2] = 200 * (y - z)
    return lab
  }

  /**
   * CIE-LAB -> XYZ.
   * @param l L coefficient.
   * @param a A coefficient.
   * @param b B coefficient.
   * @param tristimulus XYZ Tristimulus.
   * @return XYZ color space.
   */
  fun LABtoXYZ(
    l: Float,
    a: Float,
    b: Float,
    tristimulus: FloatArray
  ): FloatArray {
    val xyz = FloatArray(3)
    var y = (l + 16f) / 116f
    var x = a / 500f + y
    var z = y - b / 200f
    //Y
    y = if (Math.pow(y.toDouble(), 3.0) > 0.008856) Math.pow(
      y.toDouble(),
      3.0
    ).toFloat() else ((y - 16 / 116) / 7.787).toFloat()
    //X
    x = if (Math.pow(x.toDouble(), 3.0) > 0.008856) Math.pow(
      x.toDouble(),
      3.0
    ).toFloat() else ((x - 16 / 116) / 7.787).toFloat()
    // Z
    z = if (Math.pow(z.toDouble(), 3.0) > 0.008856) Math.pow(
      z.toDouble(),
      3.0
    ).toFloat() else ((z - 16 / 116) / 7.787).toFloat()
    xyz[0] = x * tristimulus[0]
    xyz[1] = y * tristimulus[1]
    xyz[2] = z * tristimulus[2]
    return xyz
  }

  /**
   * RGB -> C1C2C3.
   * @param r Red coefficient. Values in the range [0..255].
   * @param g Green coefficient. Values in the range [0..255].
   * @param b Blue coefficient. Values in the range [0..255].
   * @return C1C2C3 color space.
   */
  fun RGBtoC1C2C3(r: Int, g: Int, b: Int): FloatArray {
    val c = FloatArray(3)
    c[0] = Math.atan(r / Math.max(g, b).toDouble()).toFloat()
    c[1] = Math.atan(g / Math.max(r, b).toDouble()).toFloat()
    c[2] = Math.atan(b / Math.max(r, g).toDouble()).toFloat()
    return c
  }

  /**
   * RGB -> O1O2.
   * @param r Red coefficient. Values in the range [0..255].
   * @param g Green coefficient. Values in the range [0..255].
   * @param b Blue coefficient. Values in the range [0..255].
   * @return O1O2 color space.
   */
  fun RGBtoO1O2(r: Int, g: Int, b: Int): FloatArray {
    val o = FloatArray(2)
    o[0] = (r - g) / 2f
    o[1] = (r + g) / 4f - b / 2f
    return o
  }

  /**
   * RGB -> Grayscale.
   * @param r Red coefficient. Values in the range [0..255].
   * @param g Green coefficient. Values in the range [0..255].
   * @param b Blue coefficient. Values in the range [0..255].
   * @return Grayscale color space.
   */
  fun RGBtoGrayscale(r: Int, g: Int, b: Int): Float {
    return r * 0.2125f + g * 0.7154f + b * 0.0721f
  }

  enum class YCbCrColorSpace {
    ITU_BT_601, ITU_BT_709_HDTV
  }
}