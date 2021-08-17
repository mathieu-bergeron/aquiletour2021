package ca.aquiletour.server.vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class TextMessageCodec implements MessageCodec<TextMessage, TextMessage> {

	@Override
	public void encodeToWire(Buffer buffer, TextMessage s) {
		buffer.appendString(s.getText());
	}

	@Override
	public TextMessage decodeFromWire(int pos, Buffer buffer) {
		return new TextMessage(buffer.getString(0, buffer.length()));
	}

	@Override
	public TextMessage transform(TextMessage s) {
		return s;
	}

	@Override
	public String name() {
		return codecName();
	}
	
	public static String codecName() {
		return TextMessage.class.getSimpleName();
	}

	@Override
	public byte systemCodecID() {
		return -1;
	}

}
