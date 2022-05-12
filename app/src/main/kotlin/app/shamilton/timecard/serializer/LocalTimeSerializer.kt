package app.shamilton.timecard.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
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
		val splitData: List<String> = localTimeData.split(':')
		if(splitData.size != 2) throw IllegalStateException("Invalid LocalTime data: $localTimeData")

		val hour: Int = splitData[0].toInt()
		val minute: Int = splitData[1].toInt()
		return LocalTime.of(hour, minute)
	}

}