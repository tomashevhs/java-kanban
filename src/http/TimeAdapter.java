package http;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

public class TimeAdapter extends TypeAdapter<LocalDate> {
    public static final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
        if (isNull(localDate)) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDate.format(dtf));
        }
    }

    @Override
    public LocalDate read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return LocalDate.parse(jsonReader.nextString(), dtf);
        }
    }
}
