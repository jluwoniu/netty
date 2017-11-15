package com.jluwoniu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import java.nio.charset.Charset;

/**
 * Created by jluwo on 2017/11/15.
 */
public class Main {

    public static void main(String[] args)
    {
        //slice();
        op();
        //find();
    }

    private static void slice()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        //ByteBuf slice = buf.slice(0,15);
        ByteBuf copy = buf.copy(0,15);
        //System.out.println(slice.toString(utf8));
        System.out.println(copy.toString(utf8));
        //slice.setByte(0,(byte)'J');
        copy.setByte(0,(byte)'J');
        //System.out.println(slice.toString(utf8));
        System.out.println(buf.toString(utf8));
    }

    private static void op()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf byteBuf = Unpooled.buffer(1024);
        byteBuf.writeBoolean(true);
        byteBuf.writeByte(74);
        byteBuf.writeMedium(8);
        byteBuf.writeInt(9);
        byteBuf.writeLong(10);
        byteBuf.writeShort(9);
        byteBuf.writeBytes("Netty in Action".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        System.out.println("boolean: " + byteBuf.readBoolean());
        System.out.println("byte: " + byteBuf.readByte());
        System.out.println("medium: " + byteBuf.readMedium());
        System.out.println("int: " + byteBuf.readInt());
        System.out.println("Long: " + byteBuf.readLong());
        System.out.println("Short: " + byteBuf.readShort());
        byte[] bytes = new byte["Netty in Action".getBytes().length];
        byteBuf.readBytes(bytes);
        System.out.println("bytes: " + new String(bytes));
        //System.out.println(byteBuf.toString(utf8));
    }

    private static void find()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action; rocks!",utf8);
        System.out.println(buf.indexOf(0,15,(byte)116));
        System.out.println(buf.forEachByte(ByteProcessor.FIND_SEMI_COLON));

    }

    private static void allocated()
    {

    }
}
