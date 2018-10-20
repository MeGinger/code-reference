package amazon.ood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class JsonValue {
	// array, object, string, number, literals
}

class JsonIiteral extends JsonValue {
	private final String value;
	private final boolean isNull;
	private final boolean isTrue;
	private final boolean isFalse;

	public JsonIiteral(String value) {
		this.value = value;
		isNull = "null".equals(value);
		isTrue = "true".equals(value);
		isFalse = "false".equals(value);
	}
}

class JsonString extends JsonValue {
	private final String string;

	JsonString(String string) {
		if (string == null) {
			throw new NullPointerException("string is null");
		}
		this.string = string;
	}
}

class JsonNumber extends JsonValue {
	private final String string;

	JsonNumber(String string) {
		if (string == null) {
			throw new NullPointerException("string is null");
		}
		this.string = string;
	}
}

class JsonArray extends JsonValue {
	private final List<JsonValue> values;

	public JsonArray() {
		values = new ArrayList<JsonValue>();
	}
}

class JsonObject extends JsonValue {
	private final Map<String, JsonValue> properties;

	public JsonObject() {
		properties = new HashMap<String, JsonValue>();
	}
}

public class JsonParser {
	private JsonValue token;
	
	public JsonObject parse(String string) {
		// this.at_ = '\0';
//		while (isWhiteBlack(this.at_ = read()))
//			;
//		switch ((int) this.at_) {
//		case '{':
//			return this.token_ = new Token(JsonToken.BEGIN_OBJ, new Value("{"));
//		case '}':
//			return this.token_ = new Token(JsonToken.END_OBJ, new Value("}"));
//		case '[':
//			return this.token_ = new Token(JsonToken.BEGIN_ARR, new Value("["));
//		case ']':
//			return this.token_ = new Token(JsonToken.END_ARR, new Value("]"));
//		case ':':
//			return this.token_ = new Token(JsonToken.COLON, new Value(":"));
//		case ',':
//			return this.token_ = new Token(JsonToken.COMMA, new Value(","));
//		case '1':
//		case '2':
//		case '3':
//		case '4':
//		case '5':
//		case '6':
//		case '7':
//		case '8':
//		case '9':
//		case '0':
//		case '-':
//		case '+':
//		case '.':
//			return this.token_ = readNumber(this.at_);
//		case '\"':
//			return this.token_ = readString();
//		case 'n':
//			return this.token_ = readNull();
//		case 't':
//			return this.token_ = readTrue();
//		case 'f':
//			return this.token_ = readFalse();
//		case -1:
//			return this.token_ = new Token(JsonToken.EOF, new Value("eof"));
//		default:
//			this.token_ = null;
//			error("scan->default", this.at_);
//			return null;
//		}

		return null;
	}

	public String prettyPrint(JsonObject jsonObject) {
		return null;
	}
	
	public String prettyPrint(String string) {
		return null;
	}

}
