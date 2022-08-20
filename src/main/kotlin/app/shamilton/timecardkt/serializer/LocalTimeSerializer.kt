package app.shamilton.timecardkt.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.DateTimeException
import java.time.LocalTime

object LocalTimeSerializer : KSerializer<LocalTime> {

	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

	override fun serialize(encoder: Encoder, value: LocalTime) {
		val hour: String = value.hour.toString().padStart(2, '0')
		val minute: String = value.minute.toString().padStart(2, '0')
		encoder.encodeString("$hour:$minute")
	}

	override fun deserialize(decoder: Decoder): LocalTime {
		val localTimeData = decoder.decodeString()
		return decodeData(localTimeData)
	}

	private fun invalidData(data: String): Nothing = throw IllegalStateException("Invalid LocalTime data: $data")

	fun decodeData(localTimeData: String): LocalTime {
		val splitData: List<String> = localTimeData.split(':')
		if(splitData.size != 2) invalidData(localTimeData)

		try {
			// Have to account for 12 hour input
			val am = splitData[1].trim().endsWith("AM")
			val pm = splitData[1].trim().endsWith("PM")
			val is12Hour = am || pm

			val hour: Int = if(is12Hour) {
				var hourInput = splitData[0].toInt()
				if (am && hourInput == 12) {
					hourInput = 0
				} else if (pm && hourInput < 12) {
					hourInput += 12
				}
				hourInput
			} else {
				splitData[0].toInt()
			}
			val minute: Int = if (is12Hour) {
				val splitEnd = splitData[1].split(' ')
				if(splitEnd.size != 2) invalidData(localTimeData)
				splitEnd[0].toInt()
			} else {
				splitData[1].toInt()
			}

			try {
				return LocalTime.of(hour, minute)
			} catch(e: DateTimeException){
				invalidData(localTimeData)
			}
		} catch(e: NumberFormatException){
			invalidData(localTimeData)
		}
	}

}