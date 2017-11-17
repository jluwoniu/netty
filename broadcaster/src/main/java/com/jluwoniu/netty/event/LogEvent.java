package com.jluwoniu.netty.event;

import java.net.InetSocketAddress;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEvent {

    private static final byte SEPARATOR = (byte)':';
    private final InetSocketAddress source;
    private final String file;
    private final String message;
    private final long received;

    public LogEvent(String file, String message) {
        this(null,file,message,-1);
    }

    public LogEvent(InetSocketAddress source, String file, String message, long received) {
        this.source = source;
        this.file = file;
        this.message = message;
        this.received = received;
    }

    public static byte getSEPARATOR() {
        return SEPARATOR;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getFile() {
        return file;
    }

    public String getMessage() {
        return message;
    }

    public long getReceived() {
        return received;
    }
}
