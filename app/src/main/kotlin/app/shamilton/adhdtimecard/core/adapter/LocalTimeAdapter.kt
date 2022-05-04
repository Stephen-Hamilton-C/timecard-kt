package app.shamilton.adhdtimecard.core.adapter;

import java.time.LocalTime
import com.squareup.moshi.ToJson;
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException

class LocalTimeAdapter {

	@ToJson
	fun toJson(localTime: LocalTime): String {
		return "${localTime.hour}:${localTime.minute}";
	}

	@FromJson
	fun fromJson(localTimeData: String): LocalTime {
		val splitData: List<String> = localTimeData.split(':');
		if(splitData.size != 2) throw JsonDataException("Invalid LocalTime data: $localTimeData");

		val hour: Int = splitData[0].toInt();
		val minute: Int = splitData[1].toInt();
		return LocalTime.of(hour, minute);
	}

}
