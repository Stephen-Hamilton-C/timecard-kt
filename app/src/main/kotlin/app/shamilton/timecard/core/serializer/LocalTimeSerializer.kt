package app.shamilton.timecard.core.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime

@Serializable
@SerialName("LocalTime")
private class LocalTimeSurrogate(val hour: Int, val minute: Int) {
	init {
		require(hour in 0..23 && minute in 0..59)
	}
}

object LocalTimeSerializer : KSerializer<LocalTime> {

	override val descriptor: SerialDescriptor = LocalTimeSurrogate.serializer().descriptor

	override fun serialize(encoder: Encoder, value: LocalTime) {
		val hour: String = value.hour.toString().padStart(2, '0')
		val minute: String = value.minute.toString().padEnd(2, '0')
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