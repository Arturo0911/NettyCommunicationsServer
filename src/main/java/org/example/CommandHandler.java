package org.example;

import io.netty.channel.ChannelHandlerContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {


    public String decode(String msg) {
        Matcher matcher = null;
        matcher = Pattern.compile("imei:(\\d+),").matcher(msg);
        if (matcher.find()){
            return matcher.group(1);
        }

        matcher = Pattern.compile("(.*);").matcher(msg);
        if (matcher.find()){
            System.out.println("matcher0 " +matcher.group(0));
            System.out.println("matcher1 " +matcher.group(1));
            return matcher.group(1);
        }
        return "nothing";
    }


    public void checkStatusCoban(ChannelHandlerContext ctx, String message){
        Object uniqueId = decode(message);
        System.out.println("imei => "+uniqueId);
        if(message.contains("imei:") && message.length() <= 30){
            ctx.channel().writeAndFlush("LOAD");

            System.out.println("LOGIN");
        }

        if(!message.isEmpty() && Character.isDigit(message.charAt(0))){
            ctx.channel().writeAndFlush("ON");
            System.out.println(message.charAt(0));

            System.out.println("Checking live connection from the coban");
        }
    }

    public String decodeMessage(String message){
        switch (message){
            case "help me":
                return Position.ALARM_SOS;
            case "low battery":
                return Position.ALARM_LOW_BATTERY;
            case "stockade":
                return Position.ALARM_GEOFENCE;
            case "move":
                return Position.ALARM_MOVEMENT;
            case "speed":
                return Position.ALARM_OVERSPEED;
            case "door alarm":
                return Position.ALARM_DOOR;
            case "ac alarm":
                return Position.ALARM_POWER_CUT;
            case "accident alarm":
                return Position.ALARM_ACCIDENT;
            case "sensor alarm":
                return Position.ALARM_VIBRATION;
            case "bonnet alarm":
                return Position.ALARM_BONNET;
            case "footbrake alarm":
                return Position.ALARM_FOOT_BRAKE;
            case "DTC":
                return Position.ALARM_FAULT;
            case "tracker":
            default:
                return null;
        }
    }

    public String formatCommand(String keyData, String uniqueId){
        return "**,imei:"+uniqueId+","+keyData;
    }

    public String  encodeCommand(String commandType, String uniqueId){
        if (commandType.equals(Command.TYPE_ALARM_DISARM)) {
            return formatCommand("M", uniqueId);
        }
        return null;
    }


}
