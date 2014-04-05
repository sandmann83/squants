/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.motion.{ MetersPerSecond, Newtons }
import squants.energy.Joules
import squants.time.Seconds
import squants.electro.{ Ohms, OhmMeters, Siemens, SiemensPerMeter }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LengthSpec extends FlatSpec with Matchers {

  behavior of "Length and its Units of Measure"

  it should "create values using UOM factories" in {

    Meters(1).toMeters should be(1)
    Microns(1).toMicrons should be(1)
    Millimeters(1).toMillimeters should be(1)
    Centimeters(1).toCentimeters should be(1)
    Decimeters(1).toDecimeters should be(1)
    Decameters(1).toDecameters should be(1)
    Hectometers(1).toHectometers should be(1)
    Kilometers(1).toKilometers should be(1)

    Inches(1).toInches should be(1)
    Feet(1).toFeet should be(1)
    Yards(1).toYards should be(1)
    UsMiles(1).toUsMiles should be(1)
    InternationalMiles(1).toInternationalMiles should be(1)
    NauticalMiles(1).toNauticalMiles should be(1)
    AstronomicalUnits(1).toAstronomicalUnits should be(1)
    LightYears(1).toLightYears should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Meters(1)
    x.toMeters should be(1)
    x.toMicrons should be(1 / MetricSystem.Micro)
    x.toMillimeters should be(1 / MetricSystem.Milli)
    x.toCentimeters should be(1 / MetricSystem.Centi)
    x.toDecimeters should be(1 / MetricSystem.Deci)
    x.toDecameters should be(1 / MetricSystem.Deca)
    x.toHectometers should be(1 / MetricSystem.Hecto)
    x.toKilometers should be(1 / MetricSystem.Kilo)

    val metersPerFoot = 0.3048006096
    x.toInches should be(1 / (metersPerFoot / 12))
    x.toFeet should be(1 / metersPerFoot)
    x.toYards should be(1 / (metersPerFoot * 3))
    x.toUsMiles should be(1 / (metersPerFoot * 5280))
    x.toInternationalMiles should be(1 / 1609.344)
    x.toNauticalMiles should be(1 / 1852d)
    x.toAstronomicalUnits should be(1 / 149597870700d)
    x.toLightYears should be(1 / 9460730472580800d)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Meters(1).toString(Meters) should be("1.0 m")
    Microns(1).toString(Microns) should be("1.0 µm")
    Millimeters(1).toString(Millimeters) should be("1.0 mm")
    Centimeters(1).toString(Centimeters) should be("1.0 cm")
    Decimeters(1).toString(Decimeters) should be("1.0 dm")
    Decameters(1).toString(Decameters) should be("1.0 dam")
    Hectometers(1).toString(Hectometers) should be("1.0 hm")
    Kilometers(1).toString(Kilometers) should be("1.0 km")

    Inches(1).toString(Inches) should be("1.0 in")
    Feet(1).toString(Feet) should be("1.0 ft")
    Yards(1).toString(Yards) should be("1.0 yd")
    UsMiles(1).toString(UsMiles) should be("1.0 mi")
    InternationalMiles(1).toString(InternationalMiles) should be("1.0 mile")
    NauticalMiles(1).toString(NauticalMiles) should be("1.0 nmi")
    AstronomicalUnits(1).toString(AstronomicalUnits) should be("1.0 au")
    LightYears(1).toString(LightYears) should be("1.0 ly")
  }

  it should "return Area when multiplied by Length" in {
    Meters(1) * Meters(1) should be(SquareMeters(1))
  }

  it should "return Volume when multiplied by Area" in {
    Meters(1) * SquareMeters(1) should be(CubicMeters(1))
  }

  it should "return Energy when multiplied by Force" in {
    Meters(1) * Newtons(1) should be(Joules(1))
  }

  it should "return ElectricalConductance when multiplied by Conductivity" in {
    Meters(1) * SiemensPerMeter(1) should be(Siemens(1))
  }

  it should "return Resistivity when multiplied by ElectricalResistance" in {
    Meters(1) * Ohms(1) should be(OhmMeters(1))
  }

  it should "return Velocity when divided by Time" in {
    Meters(1) / Seconds(1) should be(MetersPerSecond(1))
  }

  it should "return Time when divided by Velocity" in {
    Meters(1) / MetersPerSecond(1) should be(Seconds(1))
  }

  it should "return an Area when squared" in {
    Meters(4).squared should be(SquareMeters(16))
  }

  it should "return a Volume when cubed" in {
    Meters(3).cubed should be(CubicMeters(27))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Meters(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Length](ser)
    x should be(des)
  }

  behavior of "LengthConversions"

  it should "provide aliases for single unit values" in {
    import LengthConversions._

    micron should be(Microns(1))
    millimeter should be(Millimeters(1))
    millimetre should be(Millimeters(1))
    centimeter should be(Centimeters(1))
    centimetre should be(Centimeters(1))
    decimeter should be(Decimeters(1))
    decimetre should be(Decimeters(1))
    meter should be(Meters(1))
    metre should be(Meters(1))
    decameter should be(Decameters(1))
    decametre should be(Decameters(1))
    hectometer should be(Hectometers(1))
    hectometre should be(Hectometers(1))
    kilometer should be(Kilometers(1))
    kilometre should be(Kilometers(1))
    inch should be(Inches(1))
    foot should be(Feet(1))
    yard should be(Yards(1))
    mile should be(UsMiles(1))
    nauticalMile should be(NauticalMiles(1))
    astronomicalUnit should be(AstronomicalUnits(1))
    lightYear should be(LightYears(1))
  }

  it should "provide implicit conversion from Double" in {
    import LengthConversions._

    val d = 10d
    d.µm should be(Microns(d))
    d.microns should be(Microns(d))
    d.mm should be(Millimeters(d))
    d.millimeters should be(Millimeters(d))
    d.millimetres should be(Millimeters(d))
    d.cm should be(Centimeters(d))
    d.centimeters should be(Centimeters(d))
    d.centimetres should be(Centimeters(d))
    d.dm should be(Decimeters(d))
    d.meters should be(Meters(d))
    d.metres should be(Meters(d))
    d.dam should be(Decameters(d))
    d.hm should be(Hectometers(d))
    d.km should be(Kilometers(d))
    d.kilometers should be(Kilometers(d))
    d.kilometres should be(Kilometers(d))
    d.inches should be(Inches(d))
    d.ft should be(Feet(d))
    d.feet should be(Feet(d))
    d.yd should be(Yards(d))
    d.yards should be(Yards(d))
    d.miles should be(UsMiles(d))
    d.nmi should be(NauticalMiles(d))
    d.au should be(AstronomicalUnits(d))
    d.ly should be(LightYears(d))
    d.lightYears should be(LightYears(d))
  }

  it should "provide implicit conversion from String" in {
    import LengthConversions._

    "10.33 mm".toLength.right.get should be(Millimeters(10.33))
    "10.33 cm".toLength.right.get should be(Centimeters(10.33))
    "10.33 m".toLength.right.get should be(Meters(10.33))
    "10.33 km".toLength.right.get should be(Kilometers(10.33))
    "10.33 in".toLength.right.get should be(Inches(10.33))
    "10.33 ft".toLength.right.get should be(Feet(10.33))
    "10.33 yd".toLength.right.get should be(Yards(10.33))
    "10.33 mi".toLength.right.get should be(UsMiles(10.33))
    "10.33 nmi".toLength.right.get should be(NauticalMiles(10.33))
    "10.33 au".toLength.right.get should be(AstronomicalUnits(10.33))
    "10.33 ly".toLength.right.get should be(LightYears(10.33))
    "10.33 zz".toLength.left.get should be("Unable to parse 10.33 zz as Length")
  }

  it should "provide Numeric support" in {
    import LengthConversions.LengthNumeric

    val ls = List(Meters(1000), Kilometers(1))
    ls.sum should be(Meters(2000))
  }
}
