package steganography;

import java.time.LocalTime;

public class Message {

	private String from;
	private String to;
	private String message;
	private LocalTime time;

	public Message() {
		super();

	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public Message(String from, String to) {
		this.from = from;
		this.to = to;

	}

	public final String getFrom() {
		return from;
	}

	public final void setFrom(String from) {
		this.from = from;
	}

	public final String getTo() {
		return to;
	}

	public final void setTo(String to) {
		this.to = to;
	}

	public final LocalTime getTime() {
		return time;
	}

	public final void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", message=" + message + ", time=" + time + "]";
	}
	
	

}
